package com.pouffydev.mw_core.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pouffydev.mw_core.compat.jei.category.animations.AnimatedWelder;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;

public class DecimatingCategory extends BasinCategory {
    
    private final AnimatedWelder welder = new AnimatedWelder(false);
    private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();
    
    public static DecimatingCategory standard(Info<BasinRecipe> info) {
        return new DecimatingCategory(info);
    }
    
    
    protected DecimatingCategory(Info<BasinRecipe> info) {
        super(info, true);
    }
    
    @Override
    public void draw(BasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        super.draw(recipe, iRecipeSlotsView, matrixStack, mouseX, mouseY);
        
        HeatCondition requiredHeat = recipe.getRequiredHeat();
        if (requiredHeat != HeatCondition.NONE)
            heater.withHeat(requiredHeat.visualizeAsBlazeBurner())
                    .draw(matrixStack, getBackground().getWidth() / 2 + 3, 55);
        welder.draw(matrixStack, getBackground().getWidth() / 2 + 3, 34);
    }
    
}
