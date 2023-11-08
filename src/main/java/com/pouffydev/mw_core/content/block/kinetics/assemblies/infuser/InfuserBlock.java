package com.pouffydev.mw_core.content.block.kinetics.assemblies.infuser;

import com.mrh0.createaddition.index.CABlockEntities;
import com.mrh0.createaddition.shapes.CAShapes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InfuserBlock extends Block implements IBE<InfuserBlockEntity>, IWrenchable {
    public static final VoxelShaper INFUSER_SHAPE = CAShapes.shape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0).add(1.0, 0.0, 1.0, 15.0, 12.0, 15.0).forDirectional();
    public static final DirectionProperty FACING;
    public static final BooleanProperty POWERED;
    public InfuserBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH)).setValue(POWERED, false));
    }
    
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return INFUSER_SHAPE.get(((Direction)state.getValue(FACING)).getOpposite());
    }
    
    public Class<InfuserBlockEntity> getBlockEntityClass() {
        return InfuserBlockEntity.class;
    }
    
    public BlockEntityType<? extends InfuserBlockEntity> getBlockEntityType() {
        return (BlockEntityType) CABlockEntities.TESLA_COIL.get();
    }
    
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CABlockEntities.TESLA_COIL.create(pos, state);
    }
    
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, POWERED});
    }
    
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return (BlockState)this.defaultBlockState().setValue(FACING, c.isSecondaryUseActive() ? c.getClickedFace() : c.getClickedFace().getOpposite());
    }
    
    public void setPowered(Level world, BlockPos pos, boolean powered) {
        world.setBlock(pos, (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, (Direction)world.getBlockState(pos).getValue(FACING))).setValue(POWERED, powered), 3);
    }
    
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return true;
    }
    
    static {
        FACING = BlockStateProperties.FACING;
        POWERED = BlockStateProperties.POWERED;
    }
}
