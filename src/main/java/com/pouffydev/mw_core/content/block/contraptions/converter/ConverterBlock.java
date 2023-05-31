package com.pouffydev.mw_core.content.block.contraptions.converter;

import com.pouffydev.mw_core.index.AllBlockEntities;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class ConverterBlock extends HorizontalKineticBlock implements IBE<ConverterBlockEntity> {
    public ConverterBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)this.getStateDefinition().any());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }


    public Direction.Axis getRotationAxis(BlockState state) {
        return ((Direction)state.getValue(HORIZONTAL_FACING)).getAxis();
    }

    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == ((Direction)state.getValue(HORIZONTAL_FACING)).getAxis();
    }
    @Override
    public Class<ConverterBlockEntity> getBlockEntityClass() {
        return ConverterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ConverterBlockEntity> getBlockEntityType() {
        return (BlockEntityType) AllBlockEntities.MECHANICAL_CONVERTER.get();
    }
}
