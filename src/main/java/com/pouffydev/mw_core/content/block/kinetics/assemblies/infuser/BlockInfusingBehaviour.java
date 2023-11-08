package com.pouffydev.mw_core.content.block.kinetics.assemblies.infuser;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class BlockInfusingBehaviour {
    private static final Map<ResourceLocation, BlockInfusingBehaviour> BLOCK_SPOUTING_BEHAVIOURS = new HashMap<>();
    
    public static void addCustomSpoutInteraction(ResourceLocation resourceLocation,
                                                 BlockInfusingBehaviour movementBehaviour) {
        BLOCK_SPOUTING_BEHAVIOURS.put(resourceLocation, movementBehaviour);
    }
    
    public static void forEach(Consumer<? super BlockInfusingBehaviour> accept) {
        BLOCK_SPOUTING_BEHAVIOURS.values()
                .forEach(accept);
    }
    public abstract int fillBlock(Level world, BlockPos pos, InfuserBlockEntity infuser, FluidStack availableFluid,
                                  boolean simulate);
}
