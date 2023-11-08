package com.pouffydev.mw_core.content.block.generators.reactor.chamber;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pouffydev.mw_core.content.block.generators.reactor.ReactorCogwheelBlockEntity;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class ReactorChamberRenderer extends SafeBlockEntityRenderer<ReactorChamberBlockEntity> {
    private boolean hasPipeJunctionAbove(ReactorChamberBlockEntity be) {
        return be.hasPipeJunctionAbove;
    }
    private boolean isAboveReactorCogwheel(ReactorChamberBlockEntity be) {
        return be.isAboveReactorCogwheel;
    }
    public ReactorChamberRenderer(BlockEntityRendererProvider.Context context){ super(); }
    @Override
    public boolean shouldRenderOffScreen(ReactorChamberBlockEntity be) {
        return true;
    }
    @Override
    protected void renderSafe(ReactorChamberBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        if (hasPipeJunctionAbove(be)) {
            this.renderJunctionExtension(be, partialTicks, ms, bufferSource, light, overlay);
        }
        if (isAboveReactorCogwheel(be)) {
            this.renderChamberRods(be, partialTicks, ms, bufferSource, light, overlay);
        }
    }
    
    private void renderJunctionExtension(ReactorChamberBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
        if (!Backend.canUseInstancing(be.getLevel())) {
        }
        
        BlockState blockState = be.getBlockState();
        
        SuperByteBuffer partial = CachedBufferer.partial(AllBlockPartials.JUNCTION_EXTENSION, blockState);
        partial.light(light)
                .renderInto(ms, vb);
    }
    private void renderChamberRods(ReactorChamberBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
        if (!Backend.canUseInstancing(be.getLevel())) {
        }
        
        BlockState blockState = be.getBlockState();
        
        SuperByteBuffer partial = CachedBufferer.partial(AllBlockPartials.CHAMBER_EXTENSION, blockState);
        partial.light(light)
                .renderInto(ms, vb);
    }
}
