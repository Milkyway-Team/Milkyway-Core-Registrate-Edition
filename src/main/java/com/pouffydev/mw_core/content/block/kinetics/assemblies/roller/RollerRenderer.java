package com.pouffydev.mw_core.content.block.kinetics.assemblies.roller;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class RollerRenderer extends KineticBlockEntityRenderer<RollerBlockEntity> {
    public RollerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    
    @Override
    protected SuperByteBuffer getRotatedModel(RollerBlockEntity be, BlockState state) {
        return CachedBufferer.partial(AllBlockPartials.ROLLER_WHEEL, state);
    }
    @Override
    protected void renderSafe(RollerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        renderItems(be, partialTicks, ms, bufferSource, light, overlay);
        VertexConsumer vb = bufferSource.getBuffer(RenderType.cutoutMipped());
        BlockState blockState = be.getBlockState();
        BlockPos pos = be.getBlockPos();
        int packedLightmapCoords = LevelRenderer.getLightColor(be.getLevel(), pos);
        SuperByteBuffer wheel =  CachedBufferer.partial(AllBlockPartials.ROLLER_WHEEL, blockState);
        SuperByteBuffer wheelR =  CachedBufferer.partial(AllBlockPartials.ROLLER_WHEEL_ROTATED, blockState);
        Direction.Axis axis = getRotationAxisOf(be);
        
        if (be.getBlockState().getValue(RollerBlock.HORIZONTAL_FACING).getAxis() == Direction.WEST.getAxis() || be.getBlockState().getValue(RollerBlock.HORIZONTAL_FACING).getAxis() == Direction.EAST.getAxis()) {
            wheelR
                    .rotateCentered(Direction.UP, 180*(float)Math.PI/180f)
                    .translate(0, 9f/16f, 0)
                    .rotateCentered(Direction.WEST, -getAngleForTe(be, pos, axis))
                    .light(packedLightmapCoords)
                    .renderInto(ms, vb);
        } else {
            wheel
                    .rotateCentered(Direction.UP, 180*(float)Math.PI/180f)
                    .translate(0, 9f/16f, 0)
                    .rotateCentered(Direction.NORTH, -getAngleForTe(be, pos, axis))
                    .light(packedLightmapCoords)
                    .renderInto(ms, vb);
        }

    }
    protected void renderItems(RollerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                               int light, int overlay) {
        if (!be.inventory.isEmpty()) {
            boolean alongZ = !be.getBlockState()
                    .getValue(RollerBlock.AXIS_ALONG_FIRST_COORDINATE);
            ms.pushPose();
            
            boolean moving = be.inventory.recipeDuration != 0;
            float offset = moving ? (float) (be.inventory.remainingTime) / be.inventory.recipeDuration : 0;
            float processingSpeed = Mth.clamp(Math.abs(be.getSpeed()) / 32, 1, 128);
            if (moving) {
                offset = Mth
                        .clamp(offset + ((-partialTicks + .5f) * processingSpeed) / be.inventory.recipeDuration, 0.125f, 1f);
                if (!be.inventory.appliedRecipe)
                    offset += 1;
                offset /= 2;
            }
            
            if (be.getSpeed() == 0)
                offset = .5f;
            if (be.getSpeed() < 0 ^ alongZ)
                offset = 1 - offset;
            
            for (int i = 0; i < be.inventory.getSlots(); i++) {
                ItemStack stack = be.inventory.getStackInSlot(i);
                if (stack.isEmpty())
                    continue;
                
                ItemRenderer itemRenderer = Minecraft.getInstance()
                        .getItemRenderer();
                BakedModel modelWithOverrides = itemRenderer.getModel(stack, be.getLevel(), null, 0);
                boolean blockItem = modelWithOverrides.isGui3d();
                
                ms.translate(alongZ ? offset : .5, blockItem ? .925f : 13f / 16f, alongZ ? .5 : offset);
                
                ms.scale(.5f, .5f, .5f);
                if (alongZ)
                    ms.mulPose(Vector3f.YP.rotationDegrees(90));
                ms.mulPose(Vector3f.XP.rotationDegrees(90));
                itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, ms, buffer, 0);
                break;
            }
            
            ms.popPose();
        }
    }
    @Override
    protected BlockState getRenderedBlockState(RollerBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }
}
