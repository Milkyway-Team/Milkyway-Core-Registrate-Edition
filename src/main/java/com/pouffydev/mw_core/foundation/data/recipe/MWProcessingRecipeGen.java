package com.pouffydev.mw_core.foundation.data.recipe;

import com.pouffydev.mw_core.MWCore;
import com.simibubi.create.Create;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.MixingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class MWProcessingRecipeGen extends MilkywayRecipeProvider {

    protected static final List<MWProcessingRecipeGen> GENERATORS = new ArrayList<>();
    public static void registerAll(DataGenerator gen) {
        //GENERATORS.add(new CrushingRecipeGen(gen));
        //GENERATORS.add(new MillingRecipeGen(gen));
        //GENERATORS.add(new CuttingRecipeGen(gen));
        //GENERATORS.add(new WashingRecipeGen(gen));
        //GENERATORS.add(new PolishingRecipeGen(gen));
        GENERATORS.add(new DecimatingRecipeGen(gen));
        GENERATORS.add(new MWMixingRecipeGen(gen));
        GENERATORS.add(new RollingRecipeGen(gen));
        GENERATORS.add(new MWPressingRecipeGen(gen));
        GENERATORS.add(new WeldingRecipeGen(gen));
        //GENERATORS.add(new EmptyingRecipeGen(gen));
        //GENERATORS.add(new HauntingRecipeGen(gen));
        //GENERATORS.add(new ItemApplicationRecipeGen(gen));

        gen.addProvider(new DataProvider() {

            @Override
            public String getName() {
                return "Create: Milkyway's Processing Recipes";
            }

            @Override
            public void run(HashCache dc) throws IOException {
                GENERATORS.forEach(g -> {
                    try {
                        g.run(dc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public MWProcessingRecipeGen(DataGenerator generator) {
        super(generator);
    }

    protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(String namespace,
                                                                     Supplier<ItemLike> singleIngredient, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        GeneratedRecipe generatedRecipe = c -> {
            ItemLike iItemProvider = singleIngredient.get();
            transform
                    .apply(new ProcessingRecipeBuilder<>(serializer.getFactory(),
                            new ResourceLocation(namespace, RegisteredObjects.getKeyOrThrow(iItemProvider.asItem())
                                    .getPath())).withItemIngredients(Ingredient.of(iItemProvider)))
                    .build(c);
        };
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    /**
     * Create a processing recipe with a single itemstack ingredient, using its id
     * as the name of the recipe
     */
    <T extends ProcessingRecipe<?>> GeneratedRecipe create(Supplier<ItemLike> singleIngredient, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return create(MWCore.ID, singleIngredient, transform);
    }

    protected <T extends ProcessingRecipe<?>> GeneratedRecipe createWithDeferredId(Supplier<ResourceLocation> name,
                                                                                                        UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        GeneratedRecipe generatedRecipe =
                c -> transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name.get()))
                        .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    /**
     * Create a new processing recipe, with recipe definitions provided by the
     * function
     */
    protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(ResourceLocation name,
                                                                                          UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return createWithDeferredId(() -> name, transform);
    }

    /**
     * Create a new processing recipe, with recipe definitions provided by the
     * function
     */
    <T extends ProcessingRecipe<?>> GeneratedRecipe create(String name,
                                                                                UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return create(MWCore.asResource(name), transform);
    }

    protected abstract IRecipeTypeInfo getRecipeType();

    protected <T extends ProcessingRecipe<?>> ProcessingRecipeSerializer<T> getSerializer() {
        return getRecipeType().getSerializer();
    }

    protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
        return () -> {
            ResourceLocation registryName = RegisteredObjects.getKeyOrThrow(item.get()
                    .asItem());
            return MWCore.asResource(registryName.getPath() + suffix);
        };
    }

    @Override
    public String getName() {
        return "Milkyway's Processing Recipes: " + getRecipeType().getId()
                .getPath();
    }
}
