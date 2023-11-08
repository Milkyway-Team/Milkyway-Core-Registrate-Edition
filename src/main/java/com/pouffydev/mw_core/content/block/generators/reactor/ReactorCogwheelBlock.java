package com.pouffydev.mw_core.content.block.generators.reactor;

import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlockEntity;
import com.pouffydev.mw_core.index.AllBlockEntities;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.content.processing.basin.BasinBlock;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class ReactorCogwheelBlock extends KineticBlock implements ICogWheel, IBE<ReactorCogwheelBlockEntity> {
    public ReactorCogwheelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any());
    }
    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos.above());
        if (blockEntity instanceof ReactorChamberBlockEntity)
            return false;
        return true;
    }
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }
    
    @Override
    public Class<ReactorCogwheelBlockEntity> getBlockEntityClass() {
        return ReactorCogwheelBlockEntity.class;
    }
    
    @Override
    public BlockEntityType<? extends ReactorCogwheelBlockEntity> getBlockEntityType() {
        return AllBlockEntities.REACTOR_COGWHEEL.get();
    }
}
