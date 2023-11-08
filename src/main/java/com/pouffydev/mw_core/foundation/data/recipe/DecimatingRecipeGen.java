package com.pouffydev.mw_core.foundation.data.recipe;

import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.fluids.TinkerFluids;

public class DecimatingRecipeGen extends MWProcessingRecipeGen {
    
    GeneratedRecipe
            
            moltenCopper = create("heated/copper/sheet", b -> b.require(M.copperSheet())
            .output(TinkerFluids.moltenCopper.get().getSource(), 90)
            .requiresHeat(HeatCondition.HEATED)),
    
            moltenCopperSuperHeated = create("superheated/copper/sheet", b -> b.require(M.copperSheet())
            .output(TinkerFluids.moltenCopper.get().getSource(), 90)
            .output(TinkerFluids.moltenGold.get().getSource(), 10)
            .requiresHeat(HeatCondition.SUPERHEATED))
            ;
    
    
    
    
    public DecimatingRecipeGen(DataGenerator dataGenerator) {
        super(dataGenerator);
    }
    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.DECIMATING;
    }
}
