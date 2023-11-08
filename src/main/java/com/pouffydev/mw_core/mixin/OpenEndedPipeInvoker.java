package com.pouffydev.mw_core.mixin;

import com.simibubi.create.content.fluids.OpenEndedPipe;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(OpenEndedPipe.class)
public interface OpenEndedPipeInvoker {
    @Invoker(remap = false)
    void invokeApplyEffects(FluidStack fluid);
}
