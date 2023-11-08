package com.pouffydev.mw_core.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pouffydev.mw_core.compat.jei.category.animations.AnimatedWelder;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.welder.WeldingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

import java.util.List;

public class WeldingCategory extends CreateRecipeCategory<WeldingRecipe> {
    private final AnimatedWelder welder = new AnimatedWelder(true);
    
    public WeldingCategory(Info<WeldingRecipe> info) {
        super(info);
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WeldingRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, 27, 51)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));
        
        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        for (int i = 0; i < results.size(); i++) {
            ProcessingOutput output = results.get(i);
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2) * -19;
            builder.addSlot(RecipeIngredientRole.OUTPUT, single ? 132 : 132 + xOffset, 51 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack())
                    .addTooltipCallback(addStochasticTooltip(output));
        }
    }
    
    @Override
    public void draw(WeldingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(matrixStack, 62, 57);
        AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 126, 29 + (recipe.getRollableResults().size() > 2 ? -19 : 0));
        welder.draw(matrixStack, getBackground().getWidth() / 2 - 13, 22);
    }
}
