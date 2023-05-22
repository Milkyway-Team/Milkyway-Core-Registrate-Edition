package com.pouffydev.mw_core.content.items.dohickies;

import com.pouffydev.mw_core.MWCore;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class RadiantQuartzItem extends NoGravInventoryDohicky {
    public RadiantQuartzItem(Properties properties) {
        super(properties);
        properties.tab(MWCore.itemGroup).fireResistant();
    }

    public boolean isFoil(ItemStack stack) {
        return true;
    }

    protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
        super.onCreated(entity, persistentData);
        entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 50.0, 0.0));
    }
}
