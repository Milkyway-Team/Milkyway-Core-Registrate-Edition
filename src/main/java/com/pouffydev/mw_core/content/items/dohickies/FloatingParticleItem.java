package com.pouffydev.mw_core.content.items.dohickies;

import com.pouffydev.mw_core.MWCore;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;

public class FloatingParticleItem extends NoGravInventoryCreativeDohicky {
    public FloatingParticleItem(Properties properties, ParticleOptions particle1, ParticleOptions particle2, ParticleOptions particle3, boolean isImmune) {
        super(properties, particle1, particle2, particle3, isImmune);
        properties.tab(MWCore.itemGroup);
    }

    //public boolean isFoil(ItemStack stack) {
    //    return true;
    //}

    protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
        super.onCreated(entity, persistentData);
        entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 50.0, 0.0));
    }
}
