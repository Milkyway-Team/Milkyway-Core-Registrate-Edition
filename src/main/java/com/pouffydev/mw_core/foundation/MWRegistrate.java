package com.pouffydev.mw_core.foundation;

import com.pouffydev.mw_core.MWCore;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class MWRegistrate extends CreateRegistrate {
    protected MWRegistrate(String modid) {
        super(modid);
    }
    public static MWRegistrate create(String modid) {
        return new MWRegistrate(modid);
    }
    public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> standardMWFluid(String name) {
        return fluid(name, MWCore.asResource("fluid/" + name + "/still"), MWCore.asResource("fluid/" + name + "/flowing"));
    }
    
    public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> standardMWFluid(String name, NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory) {
        return fluid(name, MWCore.asResource("fluid/" + name + "/still"), MWCore.asResource("fluid/" + name + "/flowing"), attributesFactory);
    }
}
