package com.pouffydev.mw_core.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.pouffydev.mw_core.index.MWBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.util.Mth;

public class AnimatedWelder extends AnimatedKinetics {
	public boolean depot;
	
	public AnimatedWelder(boolean depot) {
		super();
		this.depot = depot;
	}
	@Override
	public void draw(PoseStack matrixStack, int xOffset, int yOffset) {
		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 200);
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(22.5f));
		int scale;
		float animation;

		

		if (depot) {
			scale = 20;
			animation = ((Mth.sin(AnimationTickHolder.getRenderTime() / 16f) + 1) / 10) + .3f;
			blockElement(AllBlocks.DEPOT.getDefaultState())
					.atLocal(0, 2, 0)
					.scale(scale)
					.render(matrixStack);
			
			blockElement(AllBlockPartials.WELDER_HEAD)
					.atLocal(0, animation, 0)
					.scale(scale)
					.render(matrixStack);
			
			blockElement(AllBlockPartials.WELDER_COG)
					.rotateBlock(0, getCurrentAngle() * 2, 0)
					.atLocal(0, 0, 0)
					.scale(scale)
					.render(matrixStack);
			
			blockElement(MWBlocks.WELDER.getDefaultState())
					.atLocal(0, 0, 0)
					.scale(scale)
					.render(matrixStack);
		}
		else {
			scale = 23;
			animation = ((Mth.sin(AnimationTickHolder.getRenderTime() / 16f) + 1) / 8) + .3f;
			blockElement(AllBlocks.BASIN.getDefaultState())
					.atLocal(0, 1.65, 0)
					.scale(scale)
					.render(matrixStack);
			
			blockElement(AllBlockPartials.WELDER_HEAD)
					.atLocal(0, animation, 0)
					.scale(scale)
					.render(matrixStack);
			
			blockElement(AllBlockPartials.WELDER_COG)
					.rotateBlock(0, getCurrentAngle() * 2, 0)
					.atLocal(0, 0, 0)
					.scale(scale)
					.render(matrixStack);
			
			blockElement(MWBlocks.WELDER.getDefaultState())
					.atLocal(0, 0, 0)
					.scale(scale)
					.render(matrixStack);
		}

		matrixStack.popPose();
	}

}
