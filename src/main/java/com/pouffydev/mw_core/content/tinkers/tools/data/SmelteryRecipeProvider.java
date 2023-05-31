package com.pouffydev.mw_core.content.tinkers.tools.data;

import com.pouffydev.mw_core.index.AllFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;

import java.util.function.Consumer;

public class SmelteryRecipeProvider extends BaseRecipeProvider implements ISmelteryRecipeHelper, ICommonRecipeHelper {
    public SmelteryRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        this.addMeltingRecipes(consumer);
        this.addCastingRecipes(consumer);
    }

    @Override
    public String getName() {
        return "Create: Milkyway Smeltery Recipes";
    }
    private void addMeltingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";
        String dragonsteelFire = "metal/dragonsteel/fire/";
        String metalFolder = folder + "metal/";
        metalMelting(consumer, AllFluids.transium.get(), "transium", true, metalFolder, false);
    }
    private void addCastingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/casting/";
        String metalFolder = folder + "metal/";
        this.metalTagCasting(consumer, AllFluids.transium, "transium", metalFolder, true);
    }
}
