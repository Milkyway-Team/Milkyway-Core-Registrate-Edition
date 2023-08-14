package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.pouffydev.mw_core.content.block.kinetics.MWKineticBlockEntity;
import com.pouffydev.mw_core.foundation.data.MWAdvancementBehaviour;
import com.pouffydev.mw_core.foundation.data.MWAdvancements;
import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.pouffydev.mw_core.index.AllTags;
import com.simibubi.create.AllParticleTypes;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmAngleTarget;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class WelderBlockEntity extends MWKineticBlockEntity implements WeldingBehaviour.WeldingBehaviourSpecifics {
    ArmAngleTarget previousTarget;
    LerpedFloat lowerArmAngle;
    LerpedFloat upperArmAngle;
    LerpedFloat baseAngle;
    LerpedFloat headAngle;
    public WeldingBehaviour weldingBehaviour;
    private int sheetsCreated;
    public int runningTicks;
    public int processingTicks;
    public boolean running;
    public WelderBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        //baseAngle = LerpedFloat.angular();
        //baseAngle.startWithValue(previousTarget.baseAngle);
        //lowerArmAngle = LerpedFloat.angular();
        //lowerArmAngle.startWithValue(previousTarget.lowerArmAngle);
        //upperArmAngle = LerpedFloat.angular();
        //upperArmAngle.startWithValue(previousTarget.upperArmAngle);
        //headAngle = LerpedFloat.angular();
        //headAngle.startWithValue(previousTarget.headAngle);
    }
    @SuppressWarnings("unused")
    private void spawnSparks(Level pLevel, BlockPos pPos) {
        Random random = pLevel.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pPos.relative(direction);
            if (!pLevel.getBlockState(blockpos).isSolidRender(pLevel, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.25625D * (double)direction.getStepX() : (double)random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.25625D * (double)direction.getStepY() : (double)random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.25625D * (double)direction.getStepZ() : (double)random.nextFloat();
                pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, d1, d2, d3, 0, 0, 0);
                pLevel.addParticle(ParticleTypes.FLAME, d1, d2, d3, 0, 0, 0);
            }
        }

    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        super.tickAudio();

        // SoundEvents.BLOCK_STONE_BREAK
        boolean slow = Math.abs(getSpeed()) < 65;
        if (slow && AnimationTickHolder.getTicks() % 2 == 0)
            return;
        if (runningTicks == 20)
            AllSoundEvents.STEAM.playAt(level, worldPosition, .75f, 1, true);
    }
    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(3);
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        weldingBehaviour = new WeldingBehaviour(this);
        behaviours.add(weldingBehaviour);

        registerMWAwardables(behaviours, MWAdvancements.SHEET_WELDER);
    }
    public WeldingBehaviour getWeldingBehaviour() {
        return weldingBehaviour;
    }
    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putBoolean("Running", running);
        compound.putInt("Ticks", runningTicks);
        if (getBehaviour(MWAdvancementBehaviour.TYPE).isOwnerPresent())
            compound.putInt("SheetsCreated", sheetsCreated);
    }
    int getProcessingDuration(WeldingRecipe recipe) {
        int duration = recipe.getProcessingDuration();

        if (duration == 0) {
            return 20 * 256;
        }
        return duration;
    }
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        running = compound.getBoolean("Running");
        runningTicks = compound.getInt("Ticks");
        sheetsCreated = compound.getInt("SheetsCreated");
    }
    public void onItemPressed(ItemStack result) {
        MWAdvancementBehaviour behaviour = getBehaviour(MWAdvancementBehaviour.TYPE);
        if (AllTags.AllItemTags.STURDY_SHEETS.matches(result))
            sheetsCreated += result.getCount();
        if (sheetsCreated >= 1000) {
            behaviour.awardPlayer(MWAdvancements.SHEET_WELDER);
            sheetsCreated = 0;
        }
    }
    private static final RecipeWrapper weldingInv = new RecipeWrapper(new ItemStackHandler(1));
    public Optional<WeldingRecipe> getRecipe(ItemStack item) {
        Optional<WeldingRecipe> assemblyRecipe =
                SequencedAssemblyRecipe.getRecipe(level, item, AllRecipeTypes.WELDING.getType(), WeldingRecipe.class);
        if (assemblyRecipe.isPresent())
            return assemblyRecipe;

        weldingInv.setItem(0, item);
        return AllRecipeTypes.WELDING.find(weldingInv, level);
    }
    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        Optional<WeldingRecipe> recipe = getRecipe(input.stack);
        if (!recipe.isPresent())
            return false;
        if (simulate)
            return true;
        weldingBehaviour.particleItems.add(input.stack);
        List<ItemStack> outputs = RecipeApplier.applyRecipeOn(
                canProcessInBulk() ? input.stack : ItemHandlerHelper.copyStackWithSize(input.stack, 1), recipe.get());

        for (ItemStack created : outputs) {
            if (!created.isEmpty()) {
                onItemPressed(created);
                break;
            }
        }

        outputList.addAll(outputs);
        return true;
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        ItemStack item = itemEntity.getItem();
        Optional<WeldingRecipe> recipe = getRecipe(item);
        if (!recipe.isPresent())
            return false;
        if (simulate)
            return true;

        ItemStack itemCreated = ItemStack.EMPTY;
        weldingBehaviour.particleItems.add(item);
        if (canProcessInBulk() || item.getCount() == 1) {
            RecipeApplier.applyRecipeOn(itemEntity, recipe.get());
            itemCreated = itemEntity.getItem()
                    .copy();
        } else {
            for (ItemStack result : RecipeApplier.applyRecipeOn(ItemHandlerHelper.copyStackWithSize(item, 1),
                    recipe.get())) {
                if (itemCreated.isEmpty())
                    itemCreated = result.copy();
                ItemEntity created =
                        new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                created.setDefaultPickUpDelay();
                created.setDeltaMovement(VecHelper.offsetRandomly(Vec3.ZERO, Create.RANDOM, .05f));
                level.addFreshEntity(created);
            }
            item.shrink(1);
        }

        if (!itemCreated.isEmpty())
            onItemPressed(itemCreated);
        return true;
    }

    @Override
    public boolean canProcessInBulk() {
        return false;
    }

    @Override
    public int getParticleAmount() {
        return 15;
    }

    @Override
    public float getKineticSpeed() {
        return getSpeed();
    }
}
