package com.pouffydev.mw_core.foundation.data.recipe;

import com.pouffydev.mw_core.index.AllFluids;
import com.pouffydev.mw_core.index.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.data.DataGenerator;

public class MWMixingRecipeGen extends MWProcessingRecipeGen {
    GeneratedRecipe
            tarnished_nugget = create("tarnished_nugget", b -> b.require(AllFluids.magic.get(), 250)
            .require(AllFluids.creatite.get(), 250)
            .require(M.mithrilNugget())
            .require(M.mithrilNugget())
            .require(M.mithrilNugget())
            .require(M.mithrilNugget())
            .require(M.mithrilNugget())
            .require(M.mithrilNugget())
            .require(AllItems.polishedRadiantQuartz.get())
            .output(AllFluids.chromatic_waste.get(), 500)
            .output(AllItems.tarnishedNugget.get(), 3)
            .requiresHeat(HeatCondition.SUPERHEATED))
    
    ;
    public MWMixingRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
}
