package com.pouffydev.mw_core.content.block.generators.reactor.chamber;

import com.pouffydev.mw_core.content.block.generators.reactor.ReactorCogwheelBlock;
import com.pouffydev.mw_core.content.block.generators.reactor.input.ReactorInputBlock;
import com.pouffydev.mw_core.index.AllFluids;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.List;

public class ReactorChamberBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    protected SmartFluidTankBehaviour tank;
    public boolean isRunning;
    public boolean overloaded;
    public boolean isOverreacting;
    public int overloadCapacity = 1000;
    public boolean hasPipeJunctionAbove;
    public boolean isAboveReactorCogwheel;
    public ReactorChamberBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        tank = SmartFluidTankBehaviour.single(this, 1000);
        behaviours.add(tank);
    }
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side != Direction.DOWN)
            return tank.getCapability()
                    .cast();
        return super.getCapability(cap, side);
    }
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking,
                getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY));
    }
    private static final FluidStack reactorFuel = new FluidStack(FluidHelper.convertToStill(AllFluids.chromatic_waste.get()), 1);
    @Override
    public void write(CompoundTag nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        nbt.putBoolean("Running", isRunning);
        nbt.putBoolean("Overloaded", overloaded);
        nbt.putBoolean("HasPipeJunctionAbove", hasPipeJunctionAbove);
        nbt.putInt("OverloadCapacity", overloadCapacity);
        nbt.putBoolean("IsAboveReactorCogwheel", isAboveReactorCogwheel);
        nbt.putBoolean("IsOverreacting", isOverreacting);
    }
    
    @Override
    public void read(CompoundTag nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        isRunning = nbt.getBoolean("Running");
        overloaded = nbt.getBoolean("Overloaded");
        hasPipeJunctionAbove = nbt.getBoolean("HasPipeJunctionAbove");
        overloadCapacity = nbt.getInt("OverloadCapacity");
        isAboveReactorCogwheel = nbt.getBoolean("IsAboveReactorCogwheel");
        isOverreacting = nbt.getBoolean("IsOverreacting");
    }
    @Override
    public void lazyTick() {
        super.lazyTick();
        SmartFluidTank fluidHandler = tank.getPrimaryHandler();
        // consume
        if(!fluidHandler.isEmpty() && fluidHandler.getFluid().containsFluid(reactorFuel) && fluidHandler.getFluidAmount() >= 100 && !overloaded && isAboveReactorCogwheel) {
            fluidHandler.drain(100, IFluidHandler.FluidAction.EXECUTE);
            isRunning = true;
            if (!hasPipeJunctionAbove &&level != null) {
                //20% chance to reduce overload capacity by 1
                if (level.random.nextInt(5) == 0) {
                    overloadCapacity -= 1;
                    if (overloadCapacity <= 0) {
                        overloaded = true;
                    }
                }
                if (overloadCapacity < 250) {
                    isOverreacting = true;
                }
                if (overloadCapacity <= 0) {
                    overloaded = true;
                }
            }
        }
        else {
            isRunning = false;
        }
        if (overloaded) {
            kaboom();
        }
        //Check for pipe junction above
        if (level != null) {
            BlockState checkState = level.getBlockState(worldPosition.above());
            BlockState reactorCogwheel = level.getBlockState(worldPosition.below(2));
            hasPipeJunctionAbove = checkState.getBlock() instanceof ReactorInputBlock;
            isAboveReactorCogwheel = reactorCogwheel.getBlock() instanceof ReactorCogwheelBlock;
        }
    }
    
    public void kaboom() {
        if (level == null)
            return;
        level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 5, true, net.minecraft.world.level.Explosion.BlockInteraction.BREAK);
    }
}
