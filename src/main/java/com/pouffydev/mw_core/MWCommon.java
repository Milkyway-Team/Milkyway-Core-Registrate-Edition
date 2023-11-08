package com.pouffydev.mw_core;

import com.simibubi.create.foundation.ModFilePackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;

public class MWCommon {
    public static void init(final FMLCommonSetupEvent event) {
    
    }
    
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        
        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                IModFileInfo modFileInfo = ModList.get().getModFileById(MWCore.ID);
                if (modFileInfo == null) {
                    MWCore.LOGGER.error("Could not find Milkyway Core mod file info; built-in resource packs will be missing!");
                    return;
                }
                IModFile modFile = modFileInfo.getFile();
                event.addRepositorySource((consumer, constructor) -> {
                    consumer.accept(Pack.create(MWCore.asResource("mw_core_metals").toString(), false, () -> new ModFilePackResources("Milkyway Core Metal Resprites", modFile, "resourcepacks/mw_core_metals"), constructor, Pack.Position.TOP, PackSource.DEFAULT));
                });
            }
        }
        
    }
}
