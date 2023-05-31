package com.pouffydev.mw_core.content.block.generators.combustion;

import com.pouffydev.mw_core.index.AllBlockEntities;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

import java.util.Random;

public class CombustionEngineBlock extends HorizontalKineticBlock implements IBE<CombustionEngineBlockEntity>, ICogWheel {


    public CombustionEngineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)this.getStateDefinition().any());
    }
    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }
    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, Random random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.scheduleTick(pos, this, 20);

    }
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return ((Direction)state.getValue(HORIZONTAL_FACING)).getAxis();
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
    public Class<CombustionEngineBlockEntity> getBlockEntityClass() {
        return CombustionEngineBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CombustionEngineBlockEntity> getBlockEntityType() {
        return (BlockEntityType) AllBlockEntities.COMBUSTION_ENGINE.get();
    }
}
