package com.pouffydev.mw_core.content.items;

import com.pouffydev.mw_core.content.items.dohickies.NoGravInventoryCreativeDohicky;
import com.pouffydev.mw_core.content.items.dohickies.NoGravInventoryDohicky;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;

public class ShadowSteelItem extends NoGravInventoryCreativeDohicky {
    public ShadowSteelItem(Properties properties, ParticleOptions particle1, ParticleOptions particle2, ParticleOptions particle3) {
        super(properties, particle1, particle2, particle3);
    }
    @Override
    protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
        super.onCreated(entity, persistentData);
        float yMotion = (entity.fallDistance + 3) / 50f;
        entity.setDeltaMovement(0, yMotion, 0);
    }

    @Override
    protected float getIdleParticleChance(ItemEntity entity) {
        return (float) (Mth.clamp(entity.getItem()
                .getCount() - 10, Mth.clamp(entity.getDeltaMovement().y * 20, 5, 20), 100) / 64f);
    }
}
