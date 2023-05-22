package com.pouffydev.mw_core.foundation.data.recipe;

import com.pouffydev.mw_core.index.AllItems;
import com.simibubi.create.AllRecipeTypes;
import net.minecraft.data.DataGenerator;

public class MWPressingRecipeGen extends MWProcessingRecipeGen {

	GeneratedRecipe

			RADIANT_QUARTZ = create("radiant_quartz_sheet", b -> b.require(M.radiantQuartz())
			.output(AllItems.RADIANT_QUARTZ_SHEET.get()))

	;

	public MWPressingRecipeGen(DataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.PRESSING;
	}

}
