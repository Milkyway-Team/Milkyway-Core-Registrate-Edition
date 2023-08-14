package com.pouffydev.mw_core.content.block.kinetics;

import com.pouffydev.mw_core.foundation.data.MWAdvancementBehaviour;
import com.pouffydev.mw_core.foundation.data.MilkywayAdvancement;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MWKineticBlockEntity extends KineticBlockEntity {
    public MWKineticBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
    public void registerMWAwardables(List<BlockEntityBehaviour> behaviours, MilkywayAdvancement... advancements) {
        for (BlockEntityBehaviour behaviour : behaviours) {
            if (behaviour instanceof MWAdvancementBehaviour ab) {
                ab.add(advancements);
                return;
            }
        }
        behaviours.add(new MWAdvancementBehaviour(this, advancements));
    }
}
