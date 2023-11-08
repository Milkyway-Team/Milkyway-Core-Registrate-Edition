package com.pouffydev.mw_core.foundation.data.recipe;

import com.mrh0.createaddition.index.CAItems;
import com.pouffydev.mw_core.index.AllRecipeTypes;
import net.minecraft.data.DataGenerator;

public class RollingRecipeGen extends MWProcessingRecipeGen {
    
    GeneratedRecipe
            copperWire = create("copper_sheet", b -> b.require(M.copperSheet())
            .output(CAItems.COPPER_WIRE.get(), 2))
    
    ;
    
    
    
    
    public RollingRecipeGen(DataGenerator dataGenerator) {
        super(dataGenerator);
    }
    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.ROLLING;
    }
}
