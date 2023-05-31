package com.pouffydev.mw_core.content.tinkers.tools.data.material;

import com.pouffydev.mw_core.content.tinkers.tools.data.BaseRecipeProvider;
import com.pouffydev.mw_core.index.AllFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;

import java.util.function.Consumer;

public class MaterialRecipeProvider extends BaseRecipeProvider implements IMaterialRecipeHelper {
    public MaterialRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Create: Milkyway Material Recipe";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        addMaterialItems(consumer);
        addMaterialSmeltery(consumer);
    }

    private void addMaterialItems(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
    }
    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        materialMeltingCasting(consumer, MWMaterialIds.transium, AllFluids.transium, false, folder);
        //materialMeltingCasting(consumer, ForgedMaterialIds.iceDragonsteel, ForgedFluids.moltenIceDragonsteel, false, folder);
        //materialMeltingCasting(consumer, ForgedMaterialIds.lightningDragonsteel, ForgedFluids.moltenLightningDragonsteel, false, folder);
    }
}
