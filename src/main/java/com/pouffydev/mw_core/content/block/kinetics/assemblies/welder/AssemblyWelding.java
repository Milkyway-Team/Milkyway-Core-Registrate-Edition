package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pouffydev.mw_core.compat.jei.category.animations.AnimatedWelder;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;

public class AssemblyWelding extends SequencedAssemblySubCategory {
    
    AnimatedWelder welder;
    
    public AssemblyWelding() {
        super(25);
        welder = new AnimatedWelder(true);
    }
    
    @Override
    public void draw(SequencedRecipe<?> recipe, PoseStack ms, double mouseX, double mouseY, int index) {
        welder.offset = index;
        ms.pushPose();
        ms.translate(-7, 50, 0);
        ms.scale(.75f, .75f, .75f);
        welder.draw(ms, getWidth() / 2, 0);
        ms.popPose();
    }
}
