package com.pouffydev.mw_core.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.roller.RollerBlock;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.pouffydev.mw_core.index.MWBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.minecraft.core.Direction;

public class AnimatedRoller extends AnimatedKinetics {
    @Override
    public void draw(PoseStack matrixStack, int xOffset, int yOffset) {
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 0);
        matrixStack.translate(0, 0, 200);
        matrixStack.translate(2, 22, 0);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(22.5f + 90));
        int scale = 25;
        
        blockElement(shaft(Direction.Axis.X))
                .rotateBlock(-getCurrentAngle(), 0, 0)
                .scale(scale)
                .render(matrixStack);
        
        blockElement(MWBlocks.ROLLER.getDefaultState()
                .setValue(RollerBlock.HORIZONTAL_FACING, Direction.EAST))
                .rotateBlock(0, 0, 0)
                .scale(scale)
                .render(matrixStack);
        
        blockElement(AllBlockPartials.ROLLER_WHEEL_ROTATED)
                .rotateBlock(getCurrentAngle(), 0, 0)
                .scale(scale)
                .render(matrixStack);
        
        matrixStack.popPose();
    }
}
