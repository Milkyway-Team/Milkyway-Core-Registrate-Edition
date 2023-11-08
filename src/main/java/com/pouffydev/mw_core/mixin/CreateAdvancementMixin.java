package com.pouffydev.mw_core.mixin;

import com.pouffydev.mw_core.foundation.data.advancement.MilkywayAdvancement;
import com.pouffydev.mw_core.foundation.data.advancement.ModdedCreateAdvancement;
import com.simibubi.create.foundation.advancement.CreateAdvancement;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(value = CreateAdvancement.class, remap = false)
@Implements(@Interface(iface = ModdedCreateAdvancement.class, prefix = "milkywayCore$", remap = Interface.Remap.NONE))
public class CreateAdvancementMixin {
    
    //@Nullable
    //private MilkywayAdvancement milkywayCore$advancement = null;
    //
    //public void milkywayCore$fromModAdvancement(MilkywayAdvancement advancement) {
    //    this.milkywayCore$advancement = advancement;
    //}
    //
    //@Inject(method = "isAlreadyAwardedTo", at = @At("HEAD"), cancellable = true)
    //private void milkywayCoreIsAlreadyAwardedTo(Player player, CallbackInfoReturnable<Boolean> cir) {
    //    if(milkywayCore$advancement != null) {
    //        cir.setReturnValue(milkywayCore$advancement.isAlreadyAwardedTo(player));
    //    }
    //}
    //
    //@Inject(method = "awardTo", at = @At("HEAD"), cancellable = true)
    //private void milkywayCoreAwardTo(Player player, CallbackInfo ci) {
    //    if(milkywayCore$advancement != null) {
    //        milkywayCore$advancement.awardTo(player);
    //        ci.cancel();
    //    }
    //}
    
}
