package com.pouffydev.mw_core.content.block.generators.combustion;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class CombustionEngineRenderer extends KineticBlockEntityRenderer {
    public CombustionEngineRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    protected SuperByteBuffer getRotatedModel(KineticBlockEntity te, BlockState state) {
        return CachedBufferer.partialFacing(AllPartialModels.SHAFTLESS_COGWHEEL, state);
    }
}
