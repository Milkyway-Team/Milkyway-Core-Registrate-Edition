package com.pouffydev.mw_core.mixin;

import com.pouffydev.mw_core.MWCore;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinDemo {
    @Inject(method = "init", at = @At("TAIL"))
    private void exampleMixin(CallbackInfo ci) {
        MWCore.LOGGER.info("Hello World From {}", MWCore.MODID);
    }
}
