package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.foundation.MWRegistrate;
import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class AllEntities {
    private static final MWRegistrate REGISTRATE = (MWRegistrate) MWCore.registrate();
    
    private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityType.EntityFactory<T> factory, NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer, MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire, NonNullConsumer<EntityType.Builder<T>> propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder<T, ?>) MWCore.registrate()
                .entity(id, factory, group)
                .properties(b -> b.setTrackingRange(range)
                        .setUpdateInterval(updateFrequency)
                        .setShouldReceiveVelocityUpdates(sendVelocity))
                .properties(propertyBuilder)
                .properties(b -> {
                    if (immuneToFire)
                        b.fireImmune();
                })
                .renderer(renderer);
    }
    public static void register() {}
}
