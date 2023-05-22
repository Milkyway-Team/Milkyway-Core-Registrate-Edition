package com.pouffydev.mw_core.foundation.data.recipe;

import com.pouffydev.mw_core.MWCore;
import com.simibubi.create.AllTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class MilkywayRecipeProvider extends RecipeProvider {

    protected final List<GeneratedRecipe> all = new ArrayList<>();

    public MilkywayRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        all.forEach(c -> c.register(consumer));
        MWCore.LOGGER.info(getName() + " registered " + all.size() + " recipe" + (all.size() == 1 ? "" : "s"));
    }

    protected GeneratedRecipe register(GeneratedRecipe recipe) {
        all.add(recipe);
        return recipe;
    }

    @FunctionalInterface
    public interface GeneratedRecipe {
        void register(Consumer<FinishedRecipe> consumer);
    }

    protected static class Marker {
    }
    protected static class M {
        static TagKey<Item> gold() {
            return AllTags.forgeItemTag("ingots/gold");
        }
        static TagKey<Item> goldSheet() {
            return AllTags.forgeItemTag("plates/gold");
        }
        static TagKey<Item> iron() {
            return AllTags.forgeItemTag("ingots/iron");
        }
        static TagKey<Item> ironSheet() {
            return AllTags.forgeItemTag("plates/iron");
        }
        static TagKey<Item> copper() {
            return AllTags.forgeItemTag("ingots/copper");
        }
        static TagKey<Item> copperSheet() {
            return AllTags.forgeItemTag("plates/copper");
        }
        static TagKey<Item> aluminum() {
            return AllTags.forgeItemTag("ingots/aluminum");
        }
        static TagKey<Item> aluminumSheet() {
            return AllTags.forgeItemTag("plates/aluminum");
        }
        static TagKey<Item> brass() {
            return AllTags.forgeItemTag("ingots/brass");
        }
        static TagKey<Item> brassSheet() {
            return AllTags.forgeItemTag("plates/brass");
        }
        static TagKey<Item> tin() {
            return AllTags.forgeItemTag("ingots/tin");
        }
        static TagKey<Item> tinSheet() {
            return AllTags.forgeItemTag("plates/tin");
        }
        static TagKey<Item> lead() {
            return AllTags.forgeItemTag("ingots/lead");
        }
        static TagKey<Item> leadSheet() {
            return AllTags.forgeItemTag("plates/lead");
        }
        static TagKey<Item> steel() {
            return AllTags.forgeItemTag("ingots/steel");
        }
        static TagKey<Item> steelSheet() {
            return AllTags.forgeItemTag("plates/steel");
        }
        static TagKey<Item> zinc() {
            return AllTags.forgeItemTag("ingots/zinc");
        }
        static TagKey<Item> zincSheet() {
            return AllTags.forgeItemTag("plates/zinc");
        }
        static TagKey<Item> radiantQuartz(){return AllTags.forgeItemTag("gems/radiant_quartz");}

        }
    }

