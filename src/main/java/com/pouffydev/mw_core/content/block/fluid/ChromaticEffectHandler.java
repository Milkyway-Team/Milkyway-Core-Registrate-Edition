package com.pouffydev.mw_core.content.block.fluid;

import com.pouffydev.mw_core.foundation.data.advancement.MWAdvancements;
import com.simibubi.create.content.fluids.OpenEndedPipe;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;

public class ChromaticEffectHandler implements OpenEndedPipe.IEffectHandler {
    
    @Override
    public boolean canApplyEffects(OpenEndedPipe pipe, FluidStack fluid) {
        return fluid.getFluid() instanceof ChromaticWasteFluid;
    }
    
    @Override
    public void applyEffects(OpenEndedPipe pipe, FluidStack fluidStack) {
        if (!(pipe.getWorld() instanceof ServerLevel level))
            return;
        var pos = pipe.getOutputPos();
        var pipePos = pipe.getPos();
        var speed = new Vec3(pos.getX() - pipePos.getX(),
                pos.getY() - pipePos.getY(),
                pos.getZ() - pipePos.getZ()).scale(0.2);
        var smokePos = VecHelper.getCenterOf(pos);
        ChromaticWasteFluid fluid = (ChromaticWasteFluid) fluidStack.getFluid();
        int amount = fluidStack.getAmount();
        fluid.smoke(level, smokePos, amount);
    }
}
