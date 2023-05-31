package com.pouffydev.mw_core.index;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.swing.*;
import java.util.function.Supplier;

public class AllToolTiers {
    public static final Tier TARNISHED = new Tier()
    {
        @Override
        public int getUses() {
            return 1024;
        }

        @Override
        public float getSpeed() {
            return 16.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 3.5F;
        }

        @Override
        public int getLevel() {
            return 4;
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(AllItems.TARNISHED_INGOT.get());
        }
    };
}
