package com.pouffydev.mw_core.compat.tconstruct.tools.data.material;

import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

public class MaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {
    public MaterialRenderInfoProvider(DataGenerator gen, AbstractMaterialSpriteProvider spriteProvider) {
        super(gen, spriteProvider);
    }
    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(MWMaterialIds.transium).color(0xffe0e7).fallbacks("metal").luminosity(2);
        buildRenderInfo(MWMaterialIds.ancient_bismuth).color(0xb476b2).fallbacks("metal", "bone").luminosity(4);



    }
    @Override
    public String getName() {
        return "Create: Milkyway Material Render Info Provider";
    }

}
