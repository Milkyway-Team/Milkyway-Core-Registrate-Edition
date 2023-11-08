package com.pouffydev.mw_core.content.block.generators.reactor.chamber;

import com.pouffydev.mw_core.index.AllBlockEntities;
import com.pouffydev.mw_core.index.MWBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ReactorChamberBlock extends Block implements IWrenchable, IBE<ReactorChamberBlockEntity> {
    public ReactorChamberBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public Class<ReactorChamberBlockEntity> getBlockEntityClass() {
        return ReactorChamberBlockEntity.class;
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return !MWBlocks.reactorCogwheel.has(worldIn.getBlockState(pos.below()));
    }
    @Override
    public BlockEntityType<? extends ReactorChamberBlockEntity> getBlockEntityType() {
        return AllBlockEntities.REACTOR_CHAMBER.get();
    }
}
