package com.pouffydev.mw_core.content.tinkers.tools.data.material;

import com.pouffydev.mw_core.MWCore;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import static slimeknights.tconstruct.library.materials.definition.MaterialVariantId.create;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MWMaterialIds {

    public static final MaterialId transium     = id("transium");




    private static MaterialId id(String name) {
        return new MaterialId(MWCore.ID, name);
    }
}
