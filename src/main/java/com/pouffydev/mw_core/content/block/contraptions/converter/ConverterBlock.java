package com.pouffydev.mw_core.content.block.contraptions.converter;

import com.pouffydev.mw_core.index.AllTileEntities;
import com.simibubi.create.content.contraptions.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class ConverterBlock extends HorizontalKineticBlock implements ITE<ConverterTileEntity> {
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

    public Class<ConverterTileEntity> getTileEntityClass() {
        return ConverterTileEntity.class;
    }

    public BlockEntityType<? extends ConverterTileEntity> getTileEntityType() {
        return (BlockEntityType) AllTileEntities.MECHANICAL_CONVERTER.get();
    }
}
