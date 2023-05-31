package com.pouffydev.mw_core.content.block.contraptions.converter;

import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

public class ConverterBlockEntity extends MechanicalMixerBlockEntity {
    private static final Object converterRecipesKey = new Object();

    public ConverterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void renderParticles() {
        Random random = new Random();
        Optional<BasinBlockEntity> basin = this.getBasin();
        if (basin.isPresent() && this.basinChecker != null) {
            Iterator var3 = ((BasinBlockEntity)basin.get()).getInvs().iterator();

            while(var3.hasNext()) {
                SmartInventory inv = (SmartInventory)var3.next();

                for(int slot = 0; slot < inv.getSlots(); ++slot) {
                    if (!((double)random.nextFloat() > 0.2)) {
                        ItemStack stackInSlot = inv.getStackInSlot(slot);
                        if (!stackInSlot.isEmpty()) {
                            ItemParticleOption data = new ItemParticleOption(ParticleTypes.ITEM, stackInSlot);
                            this.spillParticle(data);
                        }
                    }
                }
            }

        }
    }

    public float getRenderedHeadOffset(float partialTicks) {
        float value = 0.0F;
        if (this.running) {
            value = (float)Math.max(Math.min(this.runningTicks < 20 ? 1.0 - Math.cos((double)(((float)this.runningTicks + partialTicks) / 12.0F)) : Math.cos((double)(((float)this.runningTicks + partialTicks - 20.0F) / 12.0F)), 1.0), 0.0);
        }

        return value * 1.4375F;
    }

    protected <C extends Container> boolean matchStaticFilters(Recipe<C> recipe) {
        return recipe.getId() == AllRecipeTypes.CONVERSION.getType();
    }

    protected Object getRecipeCacheKey() {
        return converterRecipesKey;
    }
}
