package com.pouffydev.mw_core.content.block.generators.combustion;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class CombustionEngineRenderer extends KineticBlockEntityRenderer<CombustionEngineBlockEntity> {

    public CombustionEngineRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    public boolean shouldRenderOffScreen(CombustionEngineBlockEntity be) {
        return true;
    }
    @Override
    protected void renderSafe(CombustionEngineBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        BlockState blockState = be.getBlockState();

        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
        SuperByteBuffer superBuffer = CachedBufferer.partial(AllBlockPartials.COMBUSTION_COG, blockState);
        standardKineticRotationTransform(superBuffer, be, light).renderInto(ms, vb);
    }
}
