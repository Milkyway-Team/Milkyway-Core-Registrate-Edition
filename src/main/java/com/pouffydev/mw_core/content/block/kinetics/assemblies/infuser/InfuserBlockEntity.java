package com.pouffydev.mw_core.content.block.kinetics.assemblies.infuser;

import com.mrh0.createaddition.config.Config;
import com.mrh0.createaddition.energy.BaseElectricBlockEntity;
import com.mrh0.createaddition.index.CAEffects;
import com.mrh0.createaddition.recipe.charging.ChargingRecipe;
import com.pouffydev.mw_core.index.MWBlocks;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.FluidFX;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.HOLD;
import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.PASS;

public class InfuserBlockEntity extends BaseElectricBlockEntity implements IHaveGoggleInformation {
    public static final int FILLING_TIME = 20;
    protected BeltProcessingBehaviour beltProcessing;
    private Optional<ChargingRecipe> recipeCache = Optional.empty();
    private ItemStackHandler inputInv = new ItemStackHandler(1);
    protected int poweredTimer = 0;
    private String damageText = Lang.translateDirect("damage.mw_core.infuser", this.getCurrentFluidInTank().getDisplayName()).toString();
    private DamageSource DMG_SOURCE = new DamageSource(this.damageText);
    int dmgTick = 0;
    int soundTimeout = 0;
    public int processingTicks;
    public boolean sendSplash;
    public BlockInfusingBehaviour customProcess;
    SmartFluidTankBehaviour tank;
    public InfuserBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    @Override
    public int getCapacity() {
        return 0;
    }
    
    @Override
    public int getMaxIn() {
        return 0;
    }
    
    @Override
    public int getMaxOut() {
        return 0;
    }
    private FluidStack getCurrentFluidInTank() {
        return tank.getPrimaryHandler()
                .getFluid();
    }
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        tank = SmartFluidTankBehaviour.single(this, 1000);
        behaviours.add(tank);
        beltProcessing = new BeltProcessingBehaviour(this).whenItemEnters(this::onItemReceived)
                .whileItemHeld(this::whenItemHeld);
        behaviours.add(beltProcessing);
    }
    protected BeltProcessingBehaviour.ProcessingResult onItemReceived(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler) {
        if (handler.blockEntity.isVirtual())
            return PASS;
        if (!FillingByInfuser.canItemBeFilled(level, transported.stack))
            return PASS;
        if (tank.isEmpty())
            return HOLD;
        if (FillingByInfuser.getRequiredAmountForItem(level, transported.stack, getCurrentFluidInTank()) == -1)
            return PASS;
        return HOLD;
    }
    protected BeltProcessingBehaviour.ProcessingResult whenItemHeld(TransportedItemStack transported,
                                                                    TransportedItemStackHandlerBehaviour handler) {
        if (processingTicks != -1 && processingTicks != 5)
            return HOLD;
        if (!FillingByInfuser.canItemBeFilled(level, transported.stack))
            return PASS;
        if (tank.isEmpty())
            return HOLD;
        FluidStack fluid = getCurrentFluidInTank();
        int requiredAmountForItem = FillingByInfuser.getRequiredAmountForItem(level, transported.stack, fluid.copy());
        if (requiredAmountForItem == -1)
            return PASS;
        if (requiredAmountForItem > fluid.getAmount())
            return HOLD;
        
        if (processingTicks == -1) {
            processingTicks = FILLING_TIME;
            notifyUpdate();
            return HOLD;
        }
        
        // Process finished
        ItemStack out = FillingByInfuser.fillItem(level, requiredAmountForItem, transported.stack, fluid);
        if (!out.isEmpty()) {
            List<TransportedItemStack> outList = new ArrayList<>();
            TransportedItemStack held = null;
            TransportedItemStack result = transported.copy();
            result.stack = out;
            if (!transported.stack.isEmpty())
                held = transported.copy();
            outList.add(result);
            handler.handleProcessingOnItem(transported, TransportedItemStackHandlerBehaviour.TransportedResult.convertToAndLeaveHeld(outList, held));
        }
        tank.getPrimaryHandler().setFluid(fluid);
        sendSplash = true;
        this.localEnergy.internalConsumeEnergy((Integer)Config.TESLA_COIL_CHARGE_RATE.get());
        notifyUpdate();
        return HOLD;
    }
    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        
        compound.putInt("ProcessingTicks", processingTicks);
        if (sendSplash && clientPacket) {
            compound.putBoolean("Splash", true);
            sendSplash = false;
        }
    }
    
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        processingTicks = compound.getInt("ProcessingTicks");
        if (!clientPacket)
            return;
        if (compound.contains("Splash"))
            spawnSplash(tank.getPrimaryTank()
                    .getRenderedFluid());
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side != Direction.DOWN)
            return tank.getCapability()
                    .cast();
        return super.getCapability(cap, side);
    }
    public void tick() {
        super.tick();
        if (this.level.isClientSide()) {
            if (this.isPoweredState() && this.soundTimeout++ > 20) {
                this.soundTimeout = 0;
            }
            
        } else {
            int signal = this.getLevel().getBestNeighborSignal(this.getBlockPos());
            if (signal > 0 && this.localEnergy.getEnergyStored() >= (Integer)Config.TESLA_COIL_HURT_ENERGY_REQUIRED.get()) {
                this.poweredTimer = 10;
            }
            
            ++this.dmgTick;
            if ((this.dmgTick %= (Integer)Config.TESLA_COIL_HURT_FIRE_COOLDOWN.get()) == 0 && this.localEnergy.getEnergyStored() >= (Integer)Config.TESLA_COIL_HURT_ENERGY_REQUIRED.get() && signal > 0) {
                this.doDmg();
            }
            
            if (this.poweredTimer > 0) {
                if (!this.isPoweredState()) {
                    ((InfuserBlock) MWBlocks.INFUSER.get()).setPowered(this.level, this.getBlockPos(), true);
                }
                
                --this.poweredTimer;
            } else if (this.isPoweredState()) {
                ((InfuserBlock)MWBlocks.INFUSER.get()).setPowered(this.level, this.getBlockPos(), false);
            }
            
        }
        FluidStack currentFluidInTank = getCurrentFluidInTank();
        if (processingTicks == -1 && (isVirtual() || !level.isClientSide()) && !currentFluidInTank.isEmpty()) {
            BlockInfusingBehaviour.forEach(behaviour -> {
                if (customProcess != null)
                    return;
                if (behaviour.fillBlock(level, worldPosition.below(2), this, currentFluidInTank, true) > 0) {
                    processingTicks = FILLING_TIME;
                    customProcess = behaviour;
                    notifyUpdate();
                }
            });
        }
        
        if (processingTicks >= 0) {
            processingTicks--;
            if (processingTicks == 5 && customProcess != null) {
                int fillBlock = customProcess.fillBlock(level, worldPosition.below(2), this, currentFluidInTank, false);
                customProcess = null;
                if (fillBlock > 0) {
                    tank.getPrimaryHandler()
                            .setFluid(FluidHelper.copyStackWithAmount(currentFluidInTank,
                                    currentFluidInTank.getAmount() - fillBlock));
                    sendSplash = true;
                    notifyUpdate();
                }
            }
        }
        
        if (processingTicks >= 8 && level.isClientSide)
            spawnProcessingParticles(tank.getPrimaryTank()
                    .getRenderedFluid());
    }
    
    protected void spawnProcessingParticles(FluidStack fluid) {
        if (isVirtual())
            return;
        Vec3 vec = VecHelper.getCenterOf(worldPosition);
        vec = vec.subtract(0, 8 / 16f, 0);
        ParticleOptions particle = FluidFX.getFluidParticle(fluid);
        level.addAlwaysVisibleParticle(particle, vec.x, vec.y, vec.z, 0, -.1f, 0);
    }
    
    protected static int SPLASH_PARTICLE_COUNT = 20;
    
    protected void spawnSplash(FluidStack fluid) {
        if (isVirtual())
            return;
        Vec3 vec = VecHelper.getCenterOf(worldPosition);
        vec = vec.subtract(0, 2 - 5 / 16f, 0);
        ParticleOptions particle = FluidFX.getFluidParticle(fluid);
        for (int i = 0; i < SPLASH_PARTICLE_COUNT; i++) {
            Vec3 m = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 0.125f);
            m = new Vec3(m.x, Math.abs(m.y), m.z);
            level.addAlwaysVisibleParticle(particle, vec.x, vec.y, vec.z, m.x, m.y, m.z);
        }
    }
    
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY));
    }
    public boolean isEnergyInput(Direction side) {
        return side != ((Direction)this.getBlockState().getValue(InfuserBlock.FACING)).getOpposite();
    }
    @Override
    public boolean isEnergyOutput(Direction direction) {
        return false;
    }
    public int getConsumption() {
        return (Integer) Config.TESLA_COIL_CHARGE_RATE.get();
    }
    private void doDmg() {
        this.localEnergy.internalConsumeEnergy((Integer)Config.TESLA_COIL_HURT_ENERGY_REQUIRED.get());
        BlockPos origin = this.getBlockPos().relative(((Direction)this.getBlockState().getValue(InfuserBlock.FACING)).getOpposite());
        List<LivingEntity> ents = this.getLevel().getEntitiesOfClass(LivingEntity.class, (new AABB(origin)).inflate((double)(Integer)Config.TESLA_COIL_HURT_RANGE.get()));
        Iterator var3 = ents.iterator();
        
        while(var3.hasNext()) {
            LivingEntity e = (LivingEntity)var3.next();
            if (e == null) {
                return;
            }
            
            int dmg = (Integer)Config.TESLA_COIL_HURT_DMG_MOB.get();
            int time = (Integer)Config.TESLA_COIL_HURT_EFFECT_TIME_MOB.get();
            if (e instanceof Player) {
                dmg = (Integer)Config.TESLA_COIL_HURT_DMG_PLAYER.get();
                time = (Integer)Config.TESLA_COIL_HURT_EFFECT_TIME_PLAYER.get();
            }
            
            if (dmg > 0) {
                e.hurt(DMG_SOURCE, (float)dmg);
            }
            
            if (time > 0) {
                e.addEffect(new MobEffectInstance(CAEffects.SHOCKING, time));
            }
        }
    }
    
    public boolean isPoweredState() {
        return (Boolean)this.getBlockState().getValue(InfuserBlock.POWERED);
    }
    
}
