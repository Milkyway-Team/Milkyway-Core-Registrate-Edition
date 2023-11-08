package com.pouffydev.mw_core.content.block.kinetics.assemblies.roller;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pouffydev.mw_core.compat.jei.category.animations.AnimatedRoller;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;

public class AssemblyRolling extends SequencedAssemblySubCategory {
    AnimatedRoller roller;
    public AssemblyRolling() {
        super(25);
        roller = new AnimatedRoller();
    }
    
    @Override
    public void draw(SequencedRecipe<?> recipe, PoseStack ms, double mouseX, double mouseY, int index) {
        ms.pushPose();
        ms.translate(0, 51.5f, 0);
        ms.scale(.6f, .6f, .6f);
        roller.draw(ms, getWidth() / 2, 30);
        ms.popPose();
    }
}
