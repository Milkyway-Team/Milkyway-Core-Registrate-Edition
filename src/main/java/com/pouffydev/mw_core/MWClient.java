package com.pouffydev.mw_core;

import com.pouffydev.mw_core.index.MWBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MWClient {
    
    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(MWClient::clientInit);
    }
    public static void clientInit(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(MWBlocks.COMBUSTION_ENGINE.get(), RenderType.cutout());
    }
    @SubscribeEvent
    static void registerModelLoaders(ModelRegistryEvent event) {
    }
    
}
