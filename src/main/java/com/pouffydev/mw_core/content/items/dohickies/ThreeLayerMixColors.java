package com.pouffydev.mw_core.content.items.dohickies;

import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class ThreeLayerMixColors implements ItemColor {

    private final int lay1c1;
    private final int lay1c2;
    private final int lay2c1;
    private final int lay2c2;
    private final int lay3c1;
    private final int lay3c2;

    public ThreeLayerMixColors(int lay1c1, int lay1c2, int lay2c1, int lay2c2, int lay3c1, int lay3c2) {
        this.lay1c1 = lay1c1;
        this.lay1c2 = lay1c2;
        this.lay2c1 = lay2c1;
        this.lay2c2 = lay2c2;
        this.lay3c1 = lay3c1;
        this.lay3c2 = lay3c2;
    }

    @Override
    public int getColor(ItemStack stack, int layer) {
        Minecraft mc = Minecraft.getInstance();
        float pt = AnimationTickHolder.getPartialTicks();
        float progress = (float) ((mc.player.getViewYRot(pt)) / 180 * Math.PI) + (AnimationTickHolder.getRenderTime() / 10f);
        if (layer == 0)
            return Color.mixColors(lay1c1, lay1c2, ((float) Mth.sin(progress) + 1) / 2);
        if (layer == 1)
            return Color.mixColors(lay2c1, lay2c2,
                    ((float) Mth.sin((float) (progress + Math.PI)) + 1) / 2);
        if (layer == 2)
            return Color.mixColors(lay3c1, lay3c2,
                    ((float) Mth.sin((float) (progress * 1.5f + Math.PI)) + 1) / 2);
        return 0;
    }
}
