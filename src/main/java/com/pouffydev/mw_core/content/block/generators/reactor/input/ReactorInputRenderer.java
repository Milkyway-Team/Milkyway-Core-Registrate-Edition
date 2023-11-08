package com.pouffydev.mw_core.content.block.generators.reactor.input;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class ReactorInputRenderer extends SafeBlockEntityRenderer<ReactorInputBlockEntity> {
    
    private boolean isOnReactor(ReactorInputBlockEntity be) {
        return be.isOnReactor;
    }
    public ReactorInputRenderer(BlockEntityRendererProvider.Context context){ super(); }
    @Override
    protected void renderSafe(ReactorInputBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        if (!isOnReactor(be)) {
            this.renderComponents(be, partialTicks, ms, bufferSource, light, overlay);
            FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, bufferSource, light, overlay);
        }
    }
    
    private void renderComponents(ReactorInputBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
        if (!Backend.canUseInstancing(be.getLevel())) {
        }
        
        BlockState blockState = be.getBlockState();
        
        SuperByteBuffer partial = CachedBufferer.partial(AllBlockPartials.PIPE_JUNCTION, blockState);
        partial.light(light)
                .renderInto(ms, vb);
    }
}
