package com.pouffydev.mw_core.content.block.kinetics.assemblies.roller;

import com.pouffydev.mw_core.index.AllBlockEntities;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RollerBlock extends HorizontalKineticBlock implements IBE<RollerBlockEntity> {
    public RollerBlock(Properties properties) {
        super(properties);
    }
    public static final BooleanProperty AXIS_ALONG_FIRST_COORDINATE = BooleanProperty.create("axis_along_first");
    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");

    @Override
    public Class<RollerBlockEntity> getBlockEntityClass() {
        return RollerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends RollerBlockEntity> getBlockEntityType() {
        return AllBlockEntities.ROLLER.get();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FLIPPED));
        builder.add(AXIS_ALONG_FIRST_COORDINATE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction prefferedSide = getPreferredHorizontalFacing(context);
        if (prefferedSide != null)
            return defaultBlockState().setValue(HORIZONTAL_FACING, prefferedSide);
        return super.getStateForPlacement(context);
    }

    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        BlockState newState = super.getRotatedBlockState(originalState, targetedFace);
        if (newState.getValue(HORIZONTAL_FACING)
                .getAxis() != Direction.Axis.Y)
            return newState;
        if (targetedFace.getAxis() != Direction.Axis.Y)
            return newState;
        if (!originalState.getValue(AXIS_ALONG_FIRST_COORDINATE))
            newState = newState.cycle(FLIPPED);
        return newState;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        BlockState newState = super.rotate(state, rot);
        if (state.getValue(HORIZONTAL_FACING)
                .getAxis() != Direction.Axis.Y)
            return newState;

        if (rot.ordinal() % 2 == 1 && (rot == Rotation.CLOCKWISE_90) != state.getValue(AXIS_ALONG_FIRST_COORDINATE))
            newState = newState.cycle(FLIPPED);
        if (rot == Rotation.CLOCKWISE_180)
            newState = newState.cycle(FLIPPED);

        return newState;
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        BlockState newState = super.mirror(state, mirrorIn);
        if (state.getValue(HORIZONTAL_FACING)
                .getAxis() != Direction.Axis.Y)
            return newState;

        boolean alongX = state.getValue(AXIS_ALONG_FIRST_COORDINATE);
        if (alongX && mirrorIn == Mirror.FRONT_BACK)
            newState = newState.cycle(FLIPPED);
        if (!alongX && mirrorIn == Mirror.LEFT_RIGHT)
            newState = newState.cycle(FLIPPED);

        return newState;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return AllShapes.CASING_12PX.get(Direction.Axis.Y);
    }
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult hit) {
        if (player.isSpectator() || !player.getItemInHand(handIn)
                .isEmpty())
            return InteractionResult.PASS;
        if (state.getOptionalValue(HORIZONTAL_FACING)
                .orElse(Direction.WEST) != Direction.UP)
            return InteractionResult.PASS;

        return onBlockEntityUse(worldIn, pos, be -> {
            for (int i = 0; i < be.inventory.getSlots(); i++) {
                ItemStack heldItemStack = be.inventory.getStackInSlot(i);
                if (!worldIn.isClientSide && !heldItemStack.isEmpty())
                    player.getInventory()
                            .placeItemBackInInventory(heldItemStack);
            }
            be.inventory.clear();
            be.notifyUpdate();
            return InteractionResult.SUCCESS;
        });
    }
    @Override
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        super.updateEntityAfterFallOn(worldIn, entityIn);
        if (!(entityIn instanceof ItemEntity))
            return;
        if (entityIn.level.isClientSide)
            return;

        BlockPos pos = entityIn.blockPosition();
        withBlockEntityDo(entityIn.level, pos, be -> {
            if (be.getSpeed() == 0)
                return;
            be.insertItem((ItemEntity) entityIn);
        });
    }
    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.NORMAL;
    }
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(HORIZONTAL_FACING)
                .getAxis();
    }
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
}
