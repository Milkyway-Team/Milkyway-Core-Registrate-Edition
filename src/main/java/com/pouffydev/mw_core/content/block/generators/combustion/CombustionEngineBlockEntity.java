package com.pouffydev.mw_core.content.block.generators.combustion;

import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlockEntity;
import com.pouffydev.mw_core.index.MWBlocks;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CombustionEngineBlockEntity extends GeneratingKineticBlockEntity {
    public CombustionEngineBlockEntity(BlockEntityType<CombustionEngineBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        isGenerator = false;
        updateGenerator = false;
    }
    protected boolean isGenerator;
    protected boolean updateGenerator;
    protected BlazeBurnerBlock.HeatLevel heatLevel;
    @Override
    public void lazyTick() {
        super.lazyTick();
        if (updateGenerator) {
            updateGenerator = false;
            updateGenerator();
        }
        if (level == null)
            return;
        getHeatLevel();
        BlockState checkState = level.getBlockState(worldPosition.below());
        if (checkState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL)) {
            isGenerator = true;
            updateGenerator = true;
        }
    }
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if (!wasMoved)
            isGenerator = compound.getBoolean("Generating");
    }
    public void getHeatLevel() {
        if (level == null)
            heatLevel = BlazeBurnerBlock.HeatLevel.NONE;
        BlockState checkState = level.getBlockState(worldPosition.below());
        if (checkState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL))
            heatLevel = checkState.getValue(BlazeBurnerBlock.HEAT_LEVEL);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.putBoolean("Generating", isGenerator);
        super.write(compound, clientPacket);
    }
    public void queueGeneratorUpdate() {
        updateGenerator = true;
    }
    @Override
    public float calculateAddedStressCapacity() {
        return lastCapacityProvided = (isGenerator ? super.calculateAddedStressCapacity() : 0);
    }

    @Override
    public float calculateStressApplied() {
        return isGenerator ? 0 : super.calculateStressApplied();
    }
    public void updateGenerator() {
        BlockState blockState = getBlockState();
        boolean shouldGenerate = true;

        if (shouldGenerate)
            shouldGenerate = level != null && level.hasNeighborSignal(worldPosition) && level.isLoaded(worldPosition.below()) && generatorSpeed() > 0;

        if (shouldGenerate == isGenerator)
            return;
        isGenerator = shouldGenerate;
        updateGeneratedRotation();
    }
    int generatorSpeed() {
        if (heatLevel == null)
            return 0;
        
        return switch (heatLevel) {
            case SMOULDERING, FADING -> 16;
            case KINDLED -> 32;
            case SEETHING -> 64;
            default -> 0;
        };
    }
    public void initialize() {
        super.initialize();
        if (!this.hasSource() || this.getGeneratedSpeed() > this.getTheoreticalSpeed()) {
            this.updateGeneratedRotation();
        }
        
    }
    public int speed() {
        return isGenerator ? generatorSpeed() : 0;
    }
    public int produceSpeed() {
        return speed();
    }
    @Override
    public float getGeneratedSpeed() {
        return produceSpeed();
    }
}
