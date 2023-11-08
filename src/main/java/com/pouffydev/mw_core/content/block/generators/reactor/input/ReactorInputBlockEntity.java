package com.pouffydev.mw_core.content.block.generators.reactor.input;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlock;
import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlockEntity;
import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.pipes.StraightPipeBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class ReactorInputBlockEntity extends SmartBlockEntity {
    private FilteringBehaviour filter;
    public boolean isOnReactor;
    public boolean isReactorActive;
    public int wasteAmount;
    public ReactorInputBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    private void onFilterChanged(ItemStack newFilter) {
        if (!level.isClientSide)
            FluidPropagator.propagateChangedPipe(level, worldPosition, getBlockState());
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        registerAwardables(behaviours, FluidPropagator.getSharedTriggers());
            behaviours.add(new PipeJunctionBehaviour(this));
            behaviours.add(filter = new FilteringBehaviour(this, new PipeJunctionFilterSlot()).forFluids()
                    .withCallback(this::onFilterChanged));
    }
    class PipeJunctionBehaviour extends StraightPipeBlockEntity.StraightPipeFluidTransportBehaviour {
        
        public PipeJunctionBehaviour(SmartBlockEntity be) {
            super(be);
        }
        
        @Override
        public boolean canPullFluidFrom(FluidStack fluid, BlockState state, Direction direction) {
            if (!isOnReactor) {
                if (fluid.isEmpty() || filter != null && filter.test(fluid))
                    return super.canPullFluidFrom(fluid, state, direction);
            }
            return false;
        }
        
        @Override
        public boolean canHaveFlowToward(BlockState state, Direction direction) {
            if (!isOnReactor) {
                if (state.getBlock() instanceof ReactorInputBlock) {
                    ReactorInputBlock.hasPipeTowards(state);
                }
            }
            return false;
        }
    }
    static class PipeJunctionFilterSlot extends ValueBoxTransform {
        
        @Override
        public void rotate(BlockState state, PoseStack ms) {
            TransformStack.cast(ms)
                    .rotateX(90);
        }
        
        @Override
        public Vec3 getLocalOffset(BlockState state) {
            return new Vec3(0.5, 15.5f, 0.5);
        }
        
        public float getScale() {
            return super.getScale();
        };
        
    }
    @Override
    public void lazyTick() {
        super.lazyTick();
        if (level != null) {
            BlockState checkState = level.getBlockState(worldPosition.below());
            BlockEntity chamberEntity = level.getBlockEntity(worldPosition.below());
            isOnReactor = checkState.getBlock() instanceof ReactorChamberBlock;
            if (chamberEntity instanceof ReactorChamberBlockEntity) {
                isReactorActive = ((ReactorChamberBlockEntity) chamberEntity).isRunning;
                wasteAmount = ((ReactorChamberBlockEntity) chamberEntity).overloadCapacity;
            }
        }
        if (isOnReactor) {
            BlockState thisState = level.getBlockState(worldPosition);
            level.setBlockAndUpdate(worldPosition, thisState.setValue(ReactorInputBlock.isOnReactor, true));
            if (isReactorActive) {
                puffColourfulSmoke();
            }
            if (wasteAmount < 1000) {
                clearCountdown(1000 - wasteAmount);
            }
        }
    }
    public void clearCountdown(int wasteAmount) {
        //reset the Chamber's overloadCapacity to 1000 and create a huge puff of colourful smoke based on the amount of waste in the chamber. it should create a massive cloud of particles that slowly dissipates
        BlockEntity chamberEntity = level.getBlockEntity(worldPosition.below());
        if (chamberEntity instanceof ReactorChamberBlockEntity) {
            ((ReactorChamberBlockEntity) chamberEntity).overloadCapacity = 1000;
        }
        if (level.isClientSide) {
            Vec3 pos = VecHelper.getCenterOf(worldPosition);
            Vec3 motion = Vec3.ZERO;
            Vec3 noMotion = Vec3.ZERO;
            for (int i = 0; i < wasteAmount; i++) {
                Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1);
                Vec3 target = pos.add(offset);
                Vec3 direction = target.subtract(pos).normalize();
                motion = motion.add(direction.scale(0.05f));
                level.addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, noMotion.x, motion.y, noMotion.z);
            }
            for (int i = 0; i < wasteAmount; i++) {
                Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1);
                Vec3 target = pos.add(offset);
                Vec3 direction = target.subtract(pos).normalize();
                motion = motion.add(direction.scale(0.05f));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, noMotion.x, motion.y, noMotion.z);
            }
            for (int i = 0; i < wasteAmount; i++) {
                Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1);
                Vec3 target = pos.add(offset);
                Vec3 direction = target.subtract(pos).normalize();
                motion = motion.add(direction.scale(0.05f));
                level.addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, noMotion.x, motion.y, noMotion.z);
            }
            for (int i = 0; i < wasteAmount; i++) {
                Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1);
                Vec3 target = pos.add(offset);
                Vec3 direction = target.subtract(pos).normalize();
                motion = motion.add(direction.scale(0.05f));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, noMotion.x, motion.y, noMotion.z);
            }
        }
    }
    public void puffColourfulSmoke() {
        //Puff a bunch of colourful smoke particles out of the top of the block that spread upwards into a large cloud that slowly dissipates
        if (level == null)
            return;
        if (level.isClientSide) {
            Vec3 pos = VecHelper.getCenterOf(worldPosition);
            Vec3 motion = Vec3.ZERO;
            Vec3 noMotion = Vec3.ZERO;
            for (int i = 0; i < 10; i++) {
                Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1);
                Vec3 target = pos.add(offset);
                Vec3 direction = target.subtract(pos).normalize();
                motion = motion.add(direction.scale(0.05f));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, noMotion.x, motion.y, noMotion.z);
            }
            for (int i = 0; i < 10; i++) {
                Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1);
                Vec3 target = pos.add(offset);
                Vec3 direction = target.subtract(pos).normalize();
                motion = motion.add(direction.scale(0.05f));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, noMotion.x, motion.y, noMotion.z);
            }
            for (int i = 0; i < 10; i++) {
                Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1);
                Vec3 target = pos.add(offset);
                Vec3 direction = target.subtract(pos).normalize();
                motion = motion.add(direction.scale(0.05f));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, noMotion.x, motion.y, noMotion.z);
            }
            for (int i = 0; i < 10; i++) {
                Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1);
                Vec3 target = pos.add(offset);
                Vec3 direction = target.subtract(pos).normalize();
                motion = motion.add(direction.scale(0.05f));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, noMotion.x, motion.y, noMotion.z);
            }
        }
    }
}
