package com.pouffydev.mw_core.foundation.data.recipe;

import com.pouffydev.mw_core.index.AllItems;
import com.pouffydev.mw_core.index.AllRecipeTypes;
import net.minecraft.data.DataGenerator;

public class WeldingRecipeGen extends MWProcessingRecipeGen {

    GeneratedRecipe

            STURDY_COPPER_SHEET = create("sturdy_copper_sheet", b -> b.require(M.copperSheet())
            .output(AllItems.sturdyCopper.get()))

            ;




    public WeldingRecipeGen(DataGenerator dataGenerator) {
        super(dataGenerator);
    }
    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.WELDING;
    }
}
