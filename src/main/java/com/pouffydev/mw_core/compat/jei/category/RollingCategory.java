package com.pouffydev.mw_core.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pouffydev.mw_core.compat.jei.category.animations.AnimatedRoller;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.roller.RollingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedSaw;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

import java.util.List;

public class RollingCategory extends CreateRecipeCategory<RollingRecipe> {
    
    private final AnimatedRoller saw = new AnimatedRoller();
    
    public RollingCategory(Info<RollingRecipe> info) {
        super(info);
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RollingRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, 44, 5)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));
        
        List<ProcessingOutput> results = recipe.getRollableResults();
        int i = 0;
        for (ProcessingOutput output : results) {
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2) * -19;
            builder
                    .addSlot(RecipeIngredientRole.OUTPUT, 118 + xOffset, 48 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack())
                    .addTooltipCallback(addStochasticTooltip(output));
            i++;
        }
    }
    
    @Override
    public void draw(RollingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 70, 6);
        AllGuiTextures.JEI_SHADOW.render(matrixStack, 72 - 17, 42 + 13);
        
        saw.draw(matrixStack, 72, 42);
    }
}
