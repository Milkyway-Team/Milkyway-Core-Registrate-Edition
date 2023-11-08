package com.pouffydev.mw_core.content.block.kinetics.assemblies.infuser;

import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FillingByInfuser {
    private static final RecipeWrapper WRAPPER = new RecipeWrapper(new ItemStackHandler(1));
    
    public static boolean canItemBeFilled(Level world, ItemStack stack) {
        WRAPPER.setItem(0, stack);
        
        Optional<Infusing> assemblyRecipe =
                SequencedAssemblyRecipe.getRecipe(world, WRAPPER, AllRecipeTypes.INFUSING.getType(), Infusing.class);
        if (assemblyRecipe.isPresent())
            return true;
        
        if (AllRecipeTypes.INFUSING.find(WRAPPER, world)
                .isPresent())
            return true;
        return GenericItemFilling.canItemBeFilled(world, stack);
    }
    
    public static int getRequiredAmountForItem(Level world, ItemStack stack, FluidStack availableFluid) {
        WRAPPER.setItem(0, stack);
        
        Optional<Infusing> assemblyRecipe = SequencedAssemblyRecipe.getRecipe(world, WRAPPER,
                AllRecipeTypes.INFUSING.getType(), Infusing.class, matchItemAndFluid(world, availableFluid));
        if (assemblyRecipe.isPresent()) {
            FluidIngredient requiredFluid = assemblyRecipe.get()
                    .getRequiredFluid();
            if (requiredFluid.test(availableFluid))
                return requiredFluid.getRequiredAmount();
        }
        
        for (Recipe<RecipeWrapper> recipe : world.getRecipeManager()
                .getRecipesFor(AllRecipeTypes.INFUSING.getType(), WRAPPER, world)) {
            Infusing fillingRecipe = (Infusing) recipe;
            FluidIngredient requiredFluid = fillingRecipe.getRequiredFluid();
            if (requiredFluid.test(availableFluid))
                return requiredFluid.getRequiredAmount();
        }
        return GenericItemFilling.getRequiredAmountForItem(world, stack, availableFluid);
    }
    
    public static ItemStack fillItem(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid) {
        FluidStack toFill = availableFluid.copy();
        toFill.setAmount(requiredAmount);
        
        WRAPPER.setItem(0, stack);
        
        Infusing fillingRecipe = SequencedAssemblyRecipe
                .getRecipe(world, WRAPPER, AllRecipeTypes.INFUSING.getType(), Infusing.class,
                        matchItemAndFluid(world, availableFluid))
                .filter(fr -> fr.getRequiredFluid()
                        .test(toFill))
                .orElseGet(() -> {
                    for (Recipe<RecipeWrapper> recipe : world.getRecipeManager()
                            .getRecipesFor(AllRecipeTypes.INFUSING.getType(), WRAPPER, world)) {
                        Infusing fr = (Infusing) recipe;
                        FluidIngredient requiredFluid = fr.getRequiredFluid();
                        if (requiredFluid.test(toFill))
                            return fr;
                    }
                    return null;
                });
        
        if (fillingRecipe != null) {
            List<ItemStack> results = fillingRecipe.rollResults();
            availableFluid.shrink(requiredAmount);
            stack.shrink(1);
            return results.isEmpty() ? ItemStack.EMPTY : results.get(0);
        }
        
        return GenericItemFilling.fillItem(world, requiredAmount, stack, availableFluid);
    }
    
    private static Predicate<Infusing> matchItemAndFluid(Level world, FluidStack availableFluid) {
        return r -> r.matches(WRAPPER, world) && r.getRequiredFluid()
                .test(availableFluid);
    }
}
