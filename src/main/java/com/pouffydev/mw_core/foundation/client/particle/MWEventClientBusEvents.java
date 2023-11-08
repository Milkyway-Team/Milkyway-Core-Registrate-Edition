package com.pouffydev.mw_core.foundation.client.particle;

import com.pouffydev.mw_core.MWCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MWCore.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MWEventClientBusEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
    
    }
    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(MWParticles.BISMUTH.get(), BismuthParticle.Provider::new);
    }
}
