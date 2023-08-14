package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WelderRenderer extends KineticBlockEntityRenderer<WelderBlockEntity> {

    public WelderRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull WelderBlockEntity be) {
        return true;
    }

    @Override
    protected void renderSafe(WelderBlockEntity be, float pt, PoseStack ms, MultiBufferSource buffer, int light,
                              int overlay) {
        super.renderSafe(be, pt, ms, buffer, light, overlay);

        if (Backend.canUseInstancing(be.getLevel()))
            return;
        PoseStack msLocal = new PoseStack();
        TransformStack msr = TransformStack.cast(msLocal);
        BlockState blockState = be.getBlockState();

        VertexConsumer builder = buffer.getBuffer(RenderType.cutout());
        float baseAngle;
        float lowerArmAngle;
        float upperArmAngle;
        float headAngle;
        int color;

            baseAngle = be.baseAngle.getValue(pt);
            lowerArmAngle = be.lowerArmAngle.getValue(pt) - 135;
            upperArmAngle = be.upperArmAngle.getValue(pt) - 90;
            headAngle = be.headAngle.getValue(pt);
            color = 0xFFFFFF;

        renderArm(builder, ms, msLocal, msr, blockState, color, baseAngle, lowerArmAngle, upperArmAngle, headAngle, light);
    }
    private void renderArm(VertexConsumer builder, PoseStack ms, PoseStack msLocal, TransformStack msr,
                       BlockState blockState, int color, float baseAngle, float lowerArmAngle, float upperArmAngle, float headAngle, int light) {
        SuperByteBuffer base = CachedBufferer.partial(AllBlockPartials.WELDER_BASE, blockState)
                .light(light);
        SuperByteBuffer lowerBody = CachedBufferer.partial(AllBlockPartials.WELDER_LOWER_BODY, blockState)
                .light(light);
        SuperByteBuffer upperBody = CachedBufferer.partial(AllBlockPartials.WELDER_UPPER_BODY, blockState)
                .light(light);

        transformBase(msr, baseAngle);
        base.transform(msLocal)
                .renderInto(ms, builder);

        transformLowerArm(msr, lowerArmAngle);
        lowerBody.color(color)
                .transform(msLocal)
                .renderInto(ms, builder);

        transformUpperArm(msr, upperArmAngle);
        upperBody.color(color)
                .transform(msLocal)
                .renderInto(ms, builder);

        transformHead(msr, headAngle);
}
    public static void transformHead(TransformStack msr, float headAngle) {
        msr.translate(0, 0, -15 / 16d);
        msr.rotateX(headAngle - 45f);
    }

    public static void transformUpperArm(TransformStack msr, float upperArmAngle) {
        msr.translate(0, 0, -14 / 16d);
        msr.rotateX(upperArmAngle - 90);
    }

    public static void transformLowerArm(TransformStack msr, float lowerArmAngle) {
        msr.translate(0, 2 / 16d, 0);
        msr.rotateX(lowerArmAngle + 135);
    }

    public static void transformBase(TransformStack msr, float baseAngle) {
        msr.translate(0, 4 / 16d, 0);
        msr.rotateY(baseAngle);
    }
    @Override
    protected SuperByteBuffer getRotatedModel(WelderBlockEntity be, BlockState state) {
        return CachedBufferer.partial(AllBlockPartials.WELDER_COG, state);
    }

}
