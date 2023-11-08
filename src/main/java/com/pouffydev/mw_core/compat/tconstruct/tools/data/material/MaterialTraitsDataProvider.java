package com.pouffydev.mw_core.compat.tconstruct.tools.data.material;

import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class MaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {
    public MaterialTraitsDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
        super(gen, materials);
    }

    @Override
    public String getName() {
        return "Create: Milkyway Material Traits";
    }
    @Override
    protected void addMaterialTraits() {
        addDefaultTraits(MWMaterialIds.transium, TinkerModifiers.fiery);
        addDefaultTraits(MWMaterialIds.ancient_bismuth, TinkerModifiers.conducting);
    }
}
