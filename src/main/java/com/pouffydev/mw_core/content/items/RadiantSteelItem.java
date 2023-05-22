package com.pouffydev.mw_core.content.items;

import com.pouffydev.mw_core.content.items.dohickies.NoGravInventoryCreativeDohicky;
import com.pouffydev.mw_core.content.items.dohickies.NoGravInventoryDohicky;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class RadiantSteelItem extends NoGravInventoryCreativeDohicky {
    public RadiantSteelItem(Properties properties, ParticleOptions particle1, ParticleOptions particle2, ParticleOptions particle3) {
        super(properties, particle1, particle2, particle3);
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
        super.onCreated(entity, persistentData);
        entity.setDeltaMovement(entity.getDeltaMovement()
                .add(0, .25f, 0));
    }
}
