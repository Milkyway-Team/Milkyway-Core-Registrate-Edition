package com.pouffydev.mw_core.content.block.kinetics.assemblies.infuser;

import com.pouffydev.mw_core.index.MWBlocks;
import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class Infusing extends ProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe {
    
    public Infusing(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(AllRecipeTypes.INFUSING, params);
    }
    
    @Override
    public boolean matches(RecipeWrapper inv, Level p_77569_2_) {
        return ingredients.get(0)
                .test(inv.getItem(0));
    }
    
    @Override
    protected int getMaxInputCount() {
        return 1;
    }
    
    @Override
    protected int getMaxOutputCount() {
        return 1;
    }
    
    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }
    public FluidIngredient getRequiredFluid() {
        if (fluidIngredients.isEmpty())
            throw new IllegalStateException("Infusing Recipe: " + id.toString() + " has no fluid ingredient!");
        return fluidIngredients.get(0);
    }
    @Override
    public void addAssemblyIngredients(List<Ingredient> list) {}
    
    @Override
    public void addAssemblyFluidIngredients(List<FluidIngredient> list) {
        list.add(getRequiredFluid());
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getDescriptionForAssembly() {
        List<FluidStack> matchingFluidStacks = fluidIngredients.get(0).getMatchingFluidStacks();
        if (matchingFluidStacks.size() == 0)
            return Components.literal("Invalid");
        return Lang.translateDirect("recipe.assembly.infusing",
                matchingFluidStacks.get(0).getDisplayName().getString());
    }
    
    @Override
    public void addRequiredMachines(Set<ItemLike> list) {
        list.add(MWBlocks.INFUSER.get());
    }
    
    @Override
    public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
        return () -> SequencedAssemblySubCategory.AssemblySpouting::new;
    }
    
}
