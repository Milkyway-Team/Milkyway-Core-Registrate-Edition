package com.pouffydev.mw_core;

import com.pouffydev.mw_core.foundation.ponder.MWPonderIndex;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MWClient {
    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(MWClient::clientInit);
    }
    public static void clientInit(final FMLClientSetupEvent event) {
        MWPonderIndex.register();
    }
}
