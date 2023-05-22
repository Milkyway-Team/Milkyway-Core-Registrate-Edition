package com.pouffydev.mw_core.content.block.contraptions.converter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ConverterRenderer extends KineticTileEntityRenderer {
    public ConverterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.solid());
        BlockState blockState = te.getBlockState();
        CachedBufferer.partialFacing(AllBlockPartials.CONVERTER_HEAD, blockState, (Direction)blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)).translate(0.0, (double)(-((ConverterTileEntity)te).getRenderedHeadOffset(partialTicks)), 0.0).renderInto(ms, consumer);
    }
}
