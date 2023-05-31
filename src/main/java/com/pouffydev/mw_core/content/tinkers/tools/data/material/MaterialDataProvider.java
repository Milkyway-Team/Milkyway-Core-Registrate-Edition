package com.pouffydev.mw_core.content.tinkers.tools.data.material;

import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class MaterialDataProvider extends AbstractMaterialDataProvider {
    public MaterialDataProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return "Create: Milkyway Materials";
    }

    @Override
    protected void addMaterials() {
        addMaterial(MWMaterialIds.transium,   4, ORDER_COMPAT, false);

    }
}
