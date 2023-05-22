package com.pouffydev.mw_core.content.items.dohickies;

import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class RainbowColor implements ItemColor {

    @Override
    public int getColor(ItemStack stack, int layer) {
        Minecraft mc = Minecraft.getInstance();
        if (layer == 0)
            return Color.mixColors(0xffffff, 0xffffff, ((float) Mth.sin((float) (AnimationTickHolder.getTicks() / 10f)) + 1) / 2);
        if (layer == 1)
            return Color.rainbowColor(AnimationTickHolder.getTicks() * 30).getRGB();
        return 0;
    }
}
