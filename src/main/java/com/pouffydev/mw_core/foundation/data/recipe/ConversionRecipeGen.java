package com.pouffydev.mw_core.foundation.data.recipe;

import com.pouffydev.mw_core.index.AllFluids;
import com.pouffydev.mw_core.index.AllItems;
import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.pouffydev.mw_core.index.AllTags;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import net.minecraft.data.DataGenerator;

public class ConversionRecipeGen extends MWProcessingRecipeGen {

    GeneratedRecipe
            TEMP_LAVA = create("tarnished_purification", b -> b
            .require(AllFluids.liquidMagic.get(), 750)
            .require(AllTags.forgeItemTag("ingots/tarnished"))
            .output(AllFluids.chromaticWaste.get(), 750)
            .output(AllItems.CREATIVE_INGOT.get(), 1)
            .requiresHeat(HeatCondition.SUPERHEATED))


    ;




    public ConversionRecipeGen(DataGenerator dataGenerator) {
        super(dataGenerator);
    }
    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.CONVERSION;
    }
}
