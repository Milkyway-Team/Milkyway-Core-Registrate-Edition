package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.pouffydev.mw_core.index.AllBlockEntities;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class WelderBlock extends KineticBlock implements IBE<WelderBlockEntity>, ICogWheel {
    public WelderBlock(Properties properties) {
        super(properties);
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return !AllBlocks.DEPOT.has(worldIn.getBlockState(pos.below()));
    }
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class getBlockEntityClass() {
        return WelderBlockEntity.class;
    }

    @Override
    public BlockEntityType getBlockEntityType() {
        return AllBlockEntities.WELDER.get();
    }
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
}
