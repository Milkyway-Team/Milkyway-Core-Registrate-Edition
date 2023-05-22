package com.pouffydev.mw_core.index;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import static com.simibubi.create.Create.REGISTRATE;

public class AllFluids {
    public static FluidEntry<ForgeFlowingFluid.Flowing> molten(String material)
    {
            return REGISTRATE.standardFluid("molten_" + material.toLowerCase(), NoColorFluidAttributes::new)
                    .lang("Molten " + material)
                    .tag(AllTags.forgeFluidTag("molten_" + material.toLowerCase()))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();
    }
    public static FluidEntry<ForgeFlowingFluid.Flowing> twoWordFluid(String word1, String word2)
    {
        String path = word1.toLowerCase() + "_" + word2.toLowerCase();
        return REGISTRATE.standardFluid(path, NoColorFluidAttributes::new)
                .lang(word1 + " " + word2)
                .tag(AllTags.forgeFluidTag(path))
                .attributes(b -> b.viscosity(1500)
                        .density(1400))
                .properties(p -> p.levelDecreasePerBlock(2)
                        .tickRate(25)
                        .slopeFindDistance(3)
                        .explosionResistance(100f))
                .register();
    }
    public static final FluidEntry<ForgeFlowingFluid.Flowing> transium = molten("Transium"), creatite = molten("Creatite"),
    fluix = molten("Fluix"), certus = molten("Certus");

    public static final FluidEntry<ForgeFlowingFluid.Flowing> chromaticWaste = twoWordFluid("Chromatic", "Waste"), liquidMagic = twoWordFluid("Liquid", "Magic");

    public static void register() {}





    private static class NoColorFluidAttributes extends FluidAttributes {

        protected NoColorFluidAttributes(Builder builder, Fluid fluid) {
            super(builder, fluid);
        }

        @Override
        public int getColor(BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

    }
}
