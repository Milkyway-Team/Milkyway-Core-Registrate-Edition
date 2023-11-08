package com.pouffydev.mw_core.content.items.tools.excavator;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.pouffydev.mw_core.MWCore;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class TarnishedExcavatorRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel GEAR = new PartialModel(MWCore.asResource("item/tarnished_excavator/gear"));
    protected static final PartialModel CORE = new PartialModel(MWCore.asResource("item/tarnished_excavator/core"));
    protected static final PartialModel CORE_GLOW = new PartialModel(MWCore.asResource("item/tarnished_excavator/core_glow/item"));
    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemTransforms.TransformType transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        int maxLight = 0xF000F0;
        float worldTime = AnimationTickHolder.getRenderTime();
        CompoundTag nbt = stack.getOrCreateTag();
        renderer.renderSolid(model.getOriginalModel(), light);
        if (nbt.getBoolean("active")) {
            renderer.renderSolidGlowing(CORE.get(), maxLight);
            renderer.renderGlowing(CORE_GLOW.get(), maxLight);
        }

        float angle = worldTime * -.5f % 360;
        ms.mulPose(Vector3f.YP.rotationDegrees(angle));
        renderer.renderSolid(GEAR.get(), light);
    }
}
