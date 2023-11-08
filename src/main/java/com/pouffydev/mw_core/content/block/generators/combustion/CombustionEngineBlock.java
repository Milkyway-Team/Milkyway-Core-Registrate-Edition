package com.pouffydev.mw_core.content.block.generators.combustion;

import com.pouffydev.mw_core.index.AllBlockEntities;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.worldWrappers.WrappedWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class CombustionEngineBlock extends KineticBlock implements IBE<CombustionEngineBlockEntity>, ICogWheel {


    public static int generatedSpeed;

    public CombustionEngineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)this.getStateDefinition().any());
    }
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
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
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        blockUpdate(state, worldIn, pos);
    }

    @Override
    public void updateIndirectNeighbourShapes(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, int flags, int count) {
        super.updateIndirectNeighbourShapes(stateIn, worldIn, pos, flags, count);
        blockUpdate(stateIn, worldIn, pos);
    }
    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        blockUpdate(state, worldIn, pos);
    }
    protected void blockUpdate(BlockState state, LevelAccessor worldIn, BlockPos pos) {
        if (worldIn instanceof WrappedWorld)
            return;
        if (worldIn.isClientSide())
            return;
        withBlockEntityDo(worldIn, pos, CombustionEngineBlockEntity::queueGeneratorUpdate);
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
