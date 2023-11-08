package com.pouffydev.mw_core.compat.jei;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.compat.jei.category.DecimatingCategory;
import com.pouffydev.mw_core.compat.jei.category.RollingCategory;
import com.pouffydev.mw_core.compat.jei.category.WeldingCategory;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.roller.RollingRecipe;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.welder.WeldingRecipe;
import com.pouffydev.mw_core.index.AllItems;
import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.pouffydev.mw_core.index.MWBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.SawingCategory;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
@JeiPlugin
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class MWJEI implements IModPlugin {
    private static final ResourceLocation ID = MWCore.asResource("jei_plugin");
    
    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();
    private IIngredientManager ingredientManager;
    
    private void loadCategories() {
        allCategories.clear();
        
        CreateRecipeCategory<?>
                welding = builder(WeldingRecipe.class)
                        .addTypedRecipes(AllRecipeTypes.WELDING)
                        .catalyst(MWBlocks.WELDER::get)
                        .doubleItemIcon(MWBlocks.WELDER.get(), AllItems.sturdyCopper.get())
                        .emptyBackground(177, 70)
                        .build("welding", WeldingCategory::new),
                
                deconstructing = builder(BasinRecipe.class)
                        .addTypedRecipes(AllRecipeTypes.DECIMATING)
                        .catalyst(MWBlocks.WELDER::get)
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleItemIcon(MWBlocks.WELDER.get(), AllBlocks.BASIN.get())
                        .emptyBackground(177, 103)
                        .build("decimating", DecimatingCategory::standard),
                
                sawing = builder(RollingRecipe.class)
                        .addTypedRecipes(AllRecipeTypes.ROLLING)
                        .catalyst(MWBlocks.ROLLER::get)
                        .doubleItemIcon(MWBlocks.ROLLER.get(), AllBlocks.BELT.get())
                        .emptyBackground(177, 70)
                        .build("rolling", RollingCategory::new)
                ;
    }
    private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }
    
    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        loadCategories();
        registration.addRecipeCategories(allCategories.toArray(IRecipeCategory[]::new));
    }
    
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        
        allCategories.forEach(c -> c.registerRecipes(registration));
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));
    }
    
    private class CategoryBuilder<T extends Recipe<?>> {
        private final Class<? extends T> recipeClass;
        private Predicate<CRecipes> predicate = cRecipes -> true;
        
        private IDrawable background;
        private IDrawable icon;
        
        private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
        private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();
        
        public CategoryBuilder(Class<? extends T> recipeClass) {
            this.recipeClass = recipeClass;
        }
        
        public CategoryBuilder<T> enableIf(Predicate<CRecipes> predicate) {
            this.predicate = predicate;
            return this;
        }
        
        public CategoryBuilder<T> enableWhen(Function<CRecipes, ConfigBase.ConfigBool> configValue) {
            predicate = c -> configValue.apply(c).get();
            return this;
        }
        
        public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
            recipeListConsumers.add(consumer);
            return this;
        }
        
        public CategoryBuilder<T> addRecipes(Supplier<Collection<? extends T>> collection) {
            return addRecipeListConsumer(recipes -> recipes.addAll(collection.get()));
        }
        
        public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred) {
            return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
                if (pred.test(recipe)) {
                    recipes.add((T) recipe);
                }
            }));
        }
        
        public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred, Function<Recipe<?>, T> converter) {
            return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
                if (pred.test(recipe)) {
                    recipes.add(converter.apply(recipe));
                }
            }));
        }
        
        public CategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
            return addTypedRecipes(recipeTypeEntry::getType);
        }
        
        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> MWJEI.<T>consumeTypedRecipes(recipes::add, recipeType.get()));
        }
        
        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType, Function<Recipe<?>, T> converter) {
            return addRecipeListConsumer(recipes -> MWJEI.<T>consumeTypedRecipes(recipe -> recipes.add(converter.apply(recipe)), recipeType.get()));
        }
        
        public CategoryBuilder<T> addTypedRecipesIf(Supplier<RecipeType<? extends T>> recipeType, Predicate<Recipe<?>> pred) {
            return addRecipeListConsumer(recipes -> MWJEI.<T>consumeTypedRecipes(recipe -> {
                if (pred.test(recipe)) {
                    recipes.add(recipe);
                }
            }, recipeType.get()));
        }
        
        public CategoryBuilder<T> addTypedRecipesExcluding(Supplier<RecipeType<? extends T>> recipeType,
                                                                     Supplier<RecipeType<? extends T>> excluded) {
            return addRecipeListConsumer(recipes -> {
                List<Recipe<?>> excludedRecipes = getTypedRecipes(excluded.get());
                MWJEI.<T>consumeTypedRecipes(recipe -> {
                    for (Recipe<?> excludedRecipe : excludedRecipes) {
                        if (doInputsMatch(recipe, excludedRecipe)) {
                            return;
                        }
                    }
                    recipes.add(recipe);
                }, recipeType.get());
            });
        }
        
        public CategoryBuilder<T> removeRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> {
                List<Recipe<?>> excludedRecipes = getTypedRecipes(recipeType.get());
                recipes.removeIf(recipe -> {
                    for (Recipe<?> excludedRecipe : excludedRecipes)
                        if (doInputsMatch(recipe, excludedRecipe) && doOutputsMatch(recipe, excludedRecipe))
                            return true;
                    return false;
                });
            });
        }
        
        public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
            catalysts.add(supplier);
            return this;
        }
        
        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return catalystStack(() -> new ItemStack(supplier.get()
                    .asItem()));
        }
        
        public CategoryBuilder<T> icon(IDrawable icon) {
            this.icon = icon;
            return this;
        }
        
        public CategoryBuilder<T> itemIcon(ItemLike item) {
            icon(new ItemIcon(() -> new ItemStack(item)));
            return this;
        }
        
        public CategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
            icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
            return this;
        }
        
        public CategoryBuilder<T> background(IDrawable background) {
            this.background = background;
            return this;
        }
        
        public CategoryBuilder<T> emptyBackground(int width, int height) {
            background(new EmptyBackground(width, height));
            return this;
        }
        
        public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
            Supplier<List<T>> recipesSupplier;
            if (predicate.test(AllConfigs.server().recipes)) {
                recipesSupplier = () -> {
                    List<T> recipes = new ArrayList<>();
                    for (Consumer<List<T>> consumer : recipeListConsumers)
                        consumer.accept(recipes);
                    return recipes;
                };
            } else {
                recipesSupplier = () -> Collections.emptyList();
            }
            
            CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
                    new mezz.jei.api.recipe.RecipeType<>(MWCore.asResource(name), recipeClass),
                    Lang.translateDirect("recipe." + name), background, icon, recipesSupplier, catalysts);
            CreateRecipeCategory<T> category = factory.create(info);
            allCategories.add(category);
            return category;
        }
    }
    
    public static void consumeAllRecipes(Consumer<Recipe<?>> consumer) {
        Minecraft.getInstance()
                .getConnection()
                .getRecipeManager()
                .getRecipes()
                .forEach(consumer);
    }
    
    public static <T extends Recipe<?>> void consumeTypedRecipes(Consumer<T> consumer, RecipeType<?> type) {
        Map<ResourceLocation, Recipe<?>> map = Minecraft.getInstance()
                .getConnection()
                .getRecipeManager().recipes.get(type);
        if (map != null) {
            map.values().forEach(recipe -> consumer.accept((T) recipe));
        }
    }
    
    public static List<Recipe<?>> getTypedRecipes(RecipeType<?> type) {
        List<Recipe<?>> recipes = new ArrayList<>();
        consumeTypedRecipes(recipes::add, type);
        return recipes;
    }
    
    public static List<Recipe<?>> getTypedRecipesExcluding(RecipeType<?> type, Predicate<Recipe<?>> exclusionPred) {
        List<Recipe<?>> recipes = getTypedRecipes(type);
        recipes.removeIf(exclusionPred);
        return recipes;
    }
    
    public static boolean doInputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        if (recipe1.getIngredients()
                .isEmpty()
                || recipe2.getIngredients()
                .isEmpty()) {
            return false;
        }
        ItemStack[] matchingStacks = recipe1.getIngredients()
                .get(0)
                .getItems();
        if (matchingStacks.length == 0) {
            return false;
        }
        return recipe2.getIngredients()
                .get(0)
                .test(matchingStacks[0]);
    }
    
    public static boolean doOutputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        return ItemStack.isSame(recipe1.getResultItem(), recipe2.getResultItem());
    }
    
}
