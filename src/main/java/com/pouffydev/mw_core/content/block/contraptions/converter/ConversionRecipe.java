package com.pouffydev.mw_core.content.block.contraptions.converter;

import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.BasinRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ConversionRecipe extends BasinRecipe {
    public ConversionRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(AllRecipeTypes.CONVERSION, params);
    }
}
