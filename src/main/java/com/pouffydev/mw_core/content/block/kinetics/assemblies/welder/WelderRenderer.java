package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

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
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (Backend.canUseInstancing(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();
        WeldingBehaviour weldingBehaviour = be.getWeldingBehaviour();
        float renderedHeadOffset =
                weldingBehaviour.getRenderedHeadOffset(partialTicks) * weldingBehaviour.mode.headOffset;

        SuperByteBuffer headRender = CachedBufferer.partialFacing(AllPartialModels.MECHANICAL_PRESS_HEAD, blockState,
                blockState.getValue(HORIZONTAL_FACING));
        headRender.translate(0, -renderedHeadOffset, 0)
                .light(light)
                .renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
    private void renderArm(VertexConsumer builder, PoseStack ms, PoseStack msLocal, TransformStack msr,
                       BlockState blockState, int color, float baseAngle, float lowerArmAngle, float upperArmAngle, float headAngle,
                       boolean goggles, boolean inverted, boolean hasItem, boolean isBlockItem, int light) {
        SuperByteBuffer base = CachedBufferer.partial(AllPartialModels.ARM_BASE, blockState)
                .light(light);
        SuperByteBuffer lowerBody = CachedBufferer.partial(AllPartialModels.ARM_LOWER_BODY, blockState)
                .light(light);
        SuperByteBuffer upperBody = CachedBufferer.partial(AllPartialModels.ARM_UPPER_BODY, blockState)
                .light(light);
}
    @Override
    protected BlockState getRenderedBlockState(WelderBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }
    @Override
    protected SuperByteBuffer getRotatedModel(ArmBlockEntity be, BlockState state) {
        return CachedBufferer.partial(AllPartialModels.ARM_COG, state);
    }

}
