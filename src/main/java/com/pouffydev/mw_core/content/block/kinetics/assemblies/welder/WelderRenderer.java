package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WelderRenderer extends KineticBlockEntityRenderer<WelderBlockEntity> {

    public WelderRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    
    @Override
    public boolean shouldRenderOffScreen(WelderBlockEntity be) {
        return true;
    }
    
    @Override
    protected void renderSafe(WelderBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        
        if (Backend.canUseInstancing(be.getLevel())) return;
        
        BlockState blockState = be.getBlockState();
        
        float renderedHeadOffset = be.getRenderedHeadOffset(partialTicks);
        VertexConsumer vbCutout = buffer.getBuffer(RenderType.cutoutMipped());
        
        SuperByteBuffer poleRender = CachedBufferer.partial(AllBlockPartials.WELDER_HEAD, blockState);
        poleRender.translate(0, -renderedHeadOffset, 0)
                .light(light)
                .renderInto(ms, vbCutout);
    }
    @Override
    protected SuperByteBuffer getRotatedModel(WelderBlockEntity be, BlockState state) {
        return CachedBufferer.partial(AllBlockPartials.WELDER_COG, state);
    }

}
