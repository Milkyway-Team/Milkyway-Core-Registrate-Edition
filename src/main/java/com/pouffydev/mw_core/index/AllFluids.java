package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;

import static com.simibubi.create.Create.REGISTRATE;

public class AllFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MWCore.ID);

    public static FluidObject<ForgeFlowingFluid> transium = register("molten_transium", 1200);
    public static FluidObject<ForgeFlowingFluid> creatite = register("molten_creatite", 1450);
    public static FluidObject<ForgeFlowingFluid> fluix = register("molten_fluix", 1200);
    public static FluidObject<ForgeFlowingFluid> certus = register("molten_certus", 1200);
    public static FluidObject<ForgeFlowingFluid> chromatic = register("chromatic_waste", 1200);
    public static FluidObject<ForgeFlowingFluid> magic = register("liquid_magic", 1200);
    public static FluidObject<ForgeFlowingFluid> pure_creatite = register("molten_pure_creatite", 1450);
    public static FluidObject<ForgeFlowingFluid> creativity = register("molten_creativity", 1450);
    public static FluidObject<ForgeFlowingFluid> refined_magic = register("refined_magic", 1200);

    private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
        String still = String.format("mw_core:fluid/%s/still", name);
        String flow = String.format("mw_core:fluid/%s/flowing", name);
        return FLUIDS.register(name, FluidAttributes.builder(new ResourceLocation(still), new ResourceLocation(flow)).density(2000).viscosity(10000).temperature(temp).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA), Material.LAVA, 15);
    }
}
