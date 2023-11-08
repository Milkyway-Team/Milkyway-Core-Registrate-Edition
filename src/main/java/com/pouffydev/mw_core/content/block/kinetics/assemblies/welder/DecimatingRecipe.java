package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;

public class DecimatingRecipe extends BasinRecipe {
    
    public DecimatingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(AllRecipeTypes.DECIMATING, params);
    }
}
