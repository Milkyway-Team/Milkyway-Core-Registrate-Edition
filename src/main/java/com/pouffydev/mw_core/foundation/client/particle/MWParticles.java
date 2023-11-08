package com.pouffydev.mw_core.foundation.client.particle;

import com.pouffydev.mw_core.MWCore;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MWParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MWCore.ID);
    
    public static final RegistryObject<SimpleParticleType> BISMUTH =
            PARTICLE_TYPES.register("bismuth", () -> new SimpleParticleType(true));
    
    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
