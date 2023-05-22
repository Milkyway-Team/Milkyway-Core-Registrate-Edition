package com.pouffydev.mw_core.content.items.dohickies;

import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class TarnishedColor implements ItemColor {


    @Override
    public int getColor(ItemStack stack, int layer) {
        Minecraft mc = Minecraft.getInstance();
        float pt = AnimationTickHolder.getPartialTicks();
        float progress = (float) ((mc.player.getViewYRot(pt)) / 180 * Math.PI) + (AnimationTickHolder.getRenderTime() / 10f);
        if (layer == 0)
            return Color.mixColors(0xffffff, 0xffffff, ((float) Mth.sin(progress) + 1) / 2);
        if (layer == 1)
            return Color.mixColors(0xf6c5c5, 0xbc6666, ((float) Mth.sin(progress) + 1) / 2);
        //Color.rainbowColor(10);
        return 0;
    }
}
