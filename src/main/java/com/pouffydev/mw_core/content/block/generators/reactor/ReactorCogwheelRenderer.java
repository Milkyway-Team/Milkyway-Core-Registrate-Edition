package com.pouffydev.mw_core.content.block.generators.reactor;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pouffydev.mw_core.content.block.generators.combustion.CombustionEngineBlockEntity;
import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlockEntity;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class ReactorCogwheelRenderer extends KineticBlockEntityRenderer<ReactorCogwheelBlockEntity> {
    
    public ReactorCogwheelRenderer(BlockEntityRendererProvider.Context context){ super(context); }
    @Override
    public boolean shouldRenderOffScreen(ReactorCogwheelBlockEntity be) {
        return true;
    }
    private boolean hasChamberAbove(ReactorCogwheelBlockEntity be) {
        return be.hasChamberAbove();
    }
    @Override
    protected void renderSafe(ReactorCogwheelBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        BlockState blockState = be.getBlockState();
        
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        SuperByteBuffer superBuffer = CachedBufferer.partial(AllBlockPartials.COMBUSTION_COG, blockState);
        standardKineticRotationTransform(superBuffer, be, light).renderInto(ms, vb);
        if (hasChamberAbove(be)) {
            renderRodsDown(be, ms, buffer, light, 0, 0);
            renderRods(be, ms, buffer, light, 0, 4/16f);
            renderRods(be, ms, buffer, light, 0, 8/16f);
            renderRods(be, ms, buffer, light, 4/16f, 0);
            renderRods(be, ms, buffer, light, 8/16f, 0);
            renderRods(be, ms, buffer, light, 4/16f, 8/16f);
            renderRods(be, ms, buffer, light, 8/16f, 8/16f);
            renderRods(be, ms, buffer, light, 8/16f, 4/16f);
            //start every other rod's movement after a delay
            
        }
    }
    
    public void renderRods(ReactorCogwheelBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, double xOffset, double zOffset) {
        float angle;
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        angle = KineticBlockEntityRenderer.getAngleForTe(be, be.getBlockPos(), Direction.Axis.Z);
        BlockState blockState = be.getBlockState();
        float sine = Mth.sin(angle);
        float piston = ((1 - sine) / 4) * 8 / 16f;
        transformed(AllBlockPartials.REACTOR_ROD, blockState, false)
                .translate(0, piston, 0)
                .translate(xOffset, 14/16f, zOffset)
                .light(light)
                .renderInto(ms, vb);
    }
    public void renderRodsDown(ReactorCogwheelBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, double xOffset, double zOffset) {
        float angle;
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        angle = KineticBlockEntityRenderer.getAngleForTe(be, be.getBlockPos(), Direction.Axis.Z);
        BlockState blockState = be.getBlockState();
        float sine = Mth.sin(angle);
        float piston = (14/16f);
        float test = (14/16f)+(((1 - sine) / 4) * 7 / 16f);
        transformed(AllBlockPartials.REACTOR_ROD, blockState, false)
                .translate(0, piston, 0)
                .translate(xOffset, test, zOffset)
                .light(light)
                .translate(0, -piston, 0)
                .renderInto(ms , vb);
    }
    
    private SuperByteBuffer transformed(PartialModel model, BlockState blockState, boolean roll90) {
        return CachedBufferer.partial(model, blockState)
                .centre()
                .rotateY(roll90 ? -90 : 0)
                .unCentre();
    }
}
