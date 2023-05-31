package com.pouffydev.mw_core.content.tinkers.tools.data.sprite;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.content.tinkers.tools.data.material.MWMaterialIds;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.RepairKitStats;

public class ForgedMaterialSpriteProvider extends AbstractMaterialSpriteProvider {

    @Override
    public String getName() {
        return "Create: Milkyway Materials";
    }

    @Override
    protected void addAllMaterials() {
        ResourceLocation transiumBase = MWCore.asResource("generator/transium");
        ResourceLocation transiumHighlight = MWCore.asResource("generator/transium_highlight");
        ResourceLocation transiumBorder = MWCore.asResource("generator/transium_border");
        buildMaterial(MWMaterialIds.transium)
                .meleeHarvest().ranged().statType(RepairKitStats.ID, ExtraMaterialStats.ID)
                .fallbacks("metal")
                .transformer(GreyToSpriteTransformer.builderFromBlack()
                        .addTexture( 63, transiumBorder,    0xFF96ffff).addTexture(102, transiumBorder)
                        .addTexture(140, transiumBase,      0xFFffb2dc).addTexture(178, transiumBase)
                        .addTexture(216, transiumHighlight, 0xFFfaf3f0).addTexture(255, transiumHighlight)
                        .build());
            }
}
