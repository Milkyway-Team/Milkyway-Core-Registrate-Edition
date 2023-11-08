package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.content.block.fluid.ChromaticWasteFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.object.FluidObject;

import static com.pouffydev.mw_core.MWCore.registrate;
import static com.pouffydev.mw_core.index.MilkywayRegistryUtils.*;

public class AllFluids {

    public static FluidObject<ForgeFlowingFluid> transium = registerTinkersFluid("molten_transium", 1200);
    //public static FluidObject<ForgeFlowingFluid> certus = register("molten_certus", 1200);
    public static final ResourceLocation chromatic_waste_still = MWCore.asResource("fluid/chromatic_waste/still");
    public static final ResourceLocation chromatic_waste_flow = MWCore.asResource("fluid/chromatic_waste/flowing");
    public static final FluidEntry<ForgeFlowingFluid.Flowing> magic = registerFluid("liquid_magic", 1400, 10000, 2000, 15, 4, 1, 25, 100f, true);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> creatite = registerFluid("molten_creatite", 1450, 10000, 2000, 15, 4, 1, 25, 100f, false);
    public static FluidEntry<ForgeFlowingFluid.Flowing> pure_creatite = registerFluid("molten_pure_creatite", 1450, 10000, 2000, 15, 4, 1, 25, 100f, false);
    public static FluidEntry<ForgeFlowingFluid.Flowing> creativity = registerFluid("molten_creativity", 1450, 10000, 2000, 15, 4, 1, 25, 100f, false);
    public static FluidEntry<ForgeFlowingFluid.Flowing> tarnished_creativity = registerFluid("molten_tarnished_creativity", 1450, 10000, 2000, 15, 4, 1, 25, 100f, false);
    public static FluidEntry<ForgeFlowingFluid.Flowing> refined_magic = registerFluid("refined_magic", 1200, 10000, 2000, 15, 4, 1, 25, 100f, true);
    //public static FluidEntry<ForgeFlowingFluid.Flowing> transium = registerFluid("molten_transium", 1200, 10000, 2000, 15, 4, 1, 25, 100f, false);
    
    public static final FluidEntry<ChromaticWasteFluid> chromatic_waste = registrate.virtualFluid("chromatic_waste",
                    chromatic_waste_still, chromatic_waste_flow, null, ChromaticWasteFluid::new)
            .lang("Chromatic Waste")
            .attributes(builder -> builder.luminosity(15))
            .register();
    public static void register() {}
}
