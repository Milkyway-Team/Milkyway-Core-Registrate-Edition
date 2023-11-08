package com.pouffydev.mw_core.content.block.generators.reactor.input;

import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlockEntity;
import com.pouffydev.mw_core.index.AllBlockEntities;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ReactorInputBlock extends RotatedPillarKineticBlock implements IBE<ReactorInputBlockEntity> {
    public static final BooleanProperty isOnReactor = BooleanProperty.create("on_reactor");
    public ReactorInputBlock(Properties properties) {
        super(properties);
    }
    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
        if (world.isClientSide)
            return;
        BlockEntity blockEntity = world.getBlockEntity(pos.below());
        BlockState thisState = world.getBlockState(pos);
        if ((blockEntity instanceof ReactorChamberBlockEntity))
            thisState.setValue(isOnReactor, true);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(isOnReactor);
    }
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        super.getStateForPlacement(context);
        BlockState state = this.defaultBlockState();
        return state.setValue(isOnReactor, false);
    }
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return null;
    }
    public static void hasPipeTowards(BlockState state) {
        if (state.getValue(isOnReactor))
            return;
        state.getValue(AXIS);
    }
    
    
    @Override
    public Class<ReactorInputBlockEntity> getBlockEntityClass() {
        return ReactorInputBlockEntity.class;
    }
    
    @Override
    public BlockEntityType<? extends ReactorInputBlockEntity> getBlockEntityType() {
        return AllBlockEntities.PIPE_JUNCTION.get();
    }
}
