package com.pouffydev.mw_core;

import com.mojang.logging.LogUtils;
import com.pouffydev.mw_core.foundation.data.MWAdvancements;
import com.pouffydev.mw_core.foundation.data.recipe.MWProcessingRecipeGen;
import com.pouffydev.mw_core.index.*;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.LangMerger;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MWCore.MODID)
public class MWCore {
    public static final String MODID = "mw_core";
    private static final NonNullSupplier<CreateRegistrate> registrate = CreateRegistrate.lazy(MWCore.MODID);
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // TODO: Add new icon for your mod's item group
    public static final CreativeModeTab itemGroup = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(AllItems.RADIANT_QUARTZ.get());
        }
    };

    public MWCore()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        /*
         For adding simple kinetic blocks this is all you need but for fluids etc.
         see the Create GitHub repo -
         https://github.com/Creators-of-Create/Create/tree/mc1.18/dev/src/main/java/com/simibubi/create
         */

        AllBlocks.register();
        AllItems.register(eventBus);
        AllTileEntities.register();
        AllBlockPartials.register();
        AllRecipeTypes.register(eventBus);
        AllFluids.register();

        eventBus.addListener(EventPriority.LOWEST, MWCore::gatherData);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MWClient.onCtorClient(eventBus, forgeEventBus));
    }

    public static void gatherData(@NotNull GatherDataEvent event) {
        //TagGen.datagen();
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            gen.addProvider(new LangMerger(gen, MODID, "Create: Milkyway", AllLangPartials.values()));
            //gen.addProvider(AllSoundEvents.provider(gen));
        }
        if (event.includeServer()) {
            gen.addProvider(new MWAdvancements(gen));
        //    //gen.addProvider(new StandardRecipeGen(gen));
        //    //gen.addProvider(new MechanicalCraftingRecipeGen(gen));
        //    //gen.addProvider(new SequencedAssemblyRecipeGen(gen));
        MWProcessingRecipeGen.registerAll(gen);
//		//	AllOreFeatureConfigEntries.gatherData(event);
        }
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {}

    public static @NotNull CreateRegistrate registrate() {
        return registrate.get();
    }
}
