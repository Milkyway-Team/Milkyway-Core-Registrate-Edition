package com.pouffydev.mw_core.foundation.common;

import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MWCommonEvents {
    
    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(RecipeFinder.LISTENER);
    }
    
   //@SubscribeEvent
   //public static void onDatapackSync(OnDatapackSyncEvent event) {
   //    ServerPlayer player = event.getPlayer();
   //}
}
