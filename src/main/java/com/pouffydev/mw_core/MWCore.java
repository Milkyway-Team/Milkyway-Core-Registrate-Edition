package com.pouffydev.mw_core;

import com.mojang.logging.LogUtils;
import com.pouffydev.mw_core.compat.tconstruct.tools.data.material.*;
import com.pouffydev.mw_core.content.block.fluid.OpenEndedPipeEffects;
import com.pouffydev.mw_core.compat.tconstruct.tools.data.SmelteryRecipeProvider;
import com.pouffydev.mw_core.compat.tconstruct.tools.data.sprite.MilkywayMaterialSpriteProvider;
import com.pouffydev.mw_core.foundation.MWRegistrate;
import com.pouffydev.mw_core.foundation.client.particle.MWParticles;
import com.pouffydev.mw_core.foundation.config.MWConfigs;
import com.pouffydev.mw_core.foundation.data.advancement.MWTriggers;
import com.pouffydev.mw_core.foundation.data.recipe.MWProcessingRecipeGen;
import com.pouffydev.mw_core.foundation.data.recipe.SequencedAssemblyRecipeGen;
import com.pouffydev.mw_core.index.*;
import com.simibubi.create.foundation.data.LangMerger;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MWCore.ID)
public class MWCore {
    /**
     * Milkyway Core's Mod ID
     */
    public static final String ID = "mw_core";
    /**
     * Milkyway Core's Registrate
     */
    public static final MWRegistrate registrate = MWRegistrate.create(MWCore.ID);
    /**
     * Milkyway Core's Logger
     */
    public static final Logger LOGGER = LogUtils.getLogger();
    
    /**
     * Milkyway Core's Item Group
     */
    public static final CreativeModeTab itemGroup = new CreativeModeTab(ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(AllItems.tarnishedIngot.get());
        }
    };
    /**
     * Milkyway Core's Constructor
     */
    public MWCore()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        MWBlocks.register();
        AllItems.register();
        AllBlockEntities.register();
        AllBlockPartials.register();
        AllRecipeTypes.register(eventBus);
        AllFluids.register();
        MilkywayRegistryUtils.FLUIDS.register(eventBus);
        MWParticles.PARTICLE_TYPES.register(eventBus);
        registrate.registerEventListeners(eventBus);
        AllEntities.register();
        AllPackets.registerPackets();
        MWConfigs.register(modLoadingContext);
        MWTags.init();
        
        eventBus.addListener(EventPriority.LOWEST, MWCore::gatherData);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MWClient.onCtorClient(eventBus, forgeEventBus));
    }
    
    /**
     * Milkyway Core's Common Setup
     */
    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MWTriggers.register();
            OpenEndedPipeEffects.register();
            //MWAdvancements.register();
        });
    }
    /**
     * Milkyway Core's Data Generator
     */
    public static void gatherData(@NotNull GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        //gen.addProvider(new MWAdvancements(gen));
        if (event.includeClient()) {
            gen.addProvider(new LangMerger(gen, ID, "Create: Milkyway", AllLangPartials.values()));
            MilkywayMaterialSpriteProvider materialSprites = new MilkywayMaterialSpriteProvider();
            gen.addProvider(new MaterialRenderInfoProvider(gen, materialSprites));
        }
        if (event.includeServer()) {
            MaterialDataProvider materials = new MaterialDataProvider(gen);
            gen.addProvider(materials);
            gen.addProvider(new SmelteryRecipeProvider(gen));
            gen.addProvider(new MaterialRecipeProvider(gen));
            gen.addProvider(new MaterialStatsDataProvider(gen, materials));
            gen.addProvider(new MaterialTraitsDataProvider(gen, materials));
            MWProcessingRecipeGen.registerAll(gen);
            gen.addProvider(new SequencedAssemblyRecipeGen(gen));
        }
    }
    /**
     * Gets a uses Milkyway Core's ID for a resource location
     * @param path  Path
     * @return  Resource location path
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }
    /**
     * Gets a resource location for Milkyway Core
     * @param name  Name
     * @return  Resource location instance
     */
    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(ID, name);
    }
    private void setup(final FMLCommonSetupEvent event) {}

    public static @NotNull MWRegistrate registrate() {
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
