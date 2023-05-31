package com.pouffydev.mw_core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.pouffydev.mw_core.content.tinkers.tools.data.SmelteryRecipeProvider;
import com.pouffydev.mw_core.content.tinkers.tools.data.material.*;
import com.pouffydev.mw_core.content.tinkers.tools.data.sprite.ForgedMaterialSpriteProvider;
import com.pouffydev.mw_core.foundation.data.MWAdvancements;
import com.pouffydev.mw_core.foundation.data.recipe.MWProcessingRecipeGen;
import com.pouffydev.mw_core.index.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.LangMerger;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MWCore.ID)
public class MWCore {
    public static final String ID = "mw_core";
    public static final CreateRegistrate registrate = CreateRegistrate.create(MWCore.ID);
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // TODO: Add new icon for your mod's item group
    public static final CreativeModeTab itemGroup = new CreativeModeTab(ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.AIR);
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
        AllBlockEntities.register();
        AllBlockPartials.register();
        AllRecipeTypes.register(eventBus);
        AllFluids.FLUIDS.register(eventBus);
        registrate.registerEventListeners(eventBus);

        eventBus.addListener(EventPriority.LOWEST, MWCore::gatherData);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MWClient.onCtorClient(eventBus, forgeEventBus));
    }

    public static void gatherData(@NotNull GatherDataEvent event) {
        //TagGen.datagen();
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            gen.addProvider(new LangMerger(gen, ID, "Create: Milkyway", AllLangPartials.values()));
            ForgedMaterialSpriteProvider materialSprites = new ForgedMaterialSpriteProvider();
            gen.addProvider(new MaterialRenderInfoProvider(gen, materialSprites));
        }
        if (event.includeServer()) {
            gen.addProvider(new MWAdvancements(gen));
            MaterialDataProvider materials = new MaterialDataProvider(gen);
            gen.addProvider(materials);
            gen.addProvider(new SmelteryRecipeProvider(gen));
            gen.addProvider(new MaterialRecipeProvider(gen));
            gen.addProvider(new MaterialStatsDataProvider(gen, materials));
            gen.addProvider(new MaterialTraitsDataProvider(gen, materials));
            MWProcessingRecipeGen.registerAll(gen);
        }
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {}

    public static @NotNull CreateRegistrate registrate() {
        return registrate;
    }

    public static void sealMWClass(Object self, String base, String solution) {
        // note for future maintainers: this does not use Java 9's sealed classes as unless you use modules those are restricted to the same package.
        // Dumb restriction but not like we can change it.
        String name = self.getClass().getName();
        if (!name.startsWith("com.pouffydev.mw_core.")) {
            throw new IllegalStateException(base + " being extended from invalid package " + name + ". " + solution);
        }
    }
}
