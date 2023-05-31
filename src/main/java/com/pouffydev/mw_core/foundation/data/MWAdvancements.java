package com.pouffydev.mw_core.foundation.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.pouffydev.mw_core.index.AllBlocks;
import com.pouffydev.mw_core.index.AllItems;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static com.pouffydev.mw_core.foundation.data.MilkywayAdvancement.TaskType.EXPERT;
import static com.pouffydev.mw_core.foundation.data.MilkywayAdvancement.TaskType.SILENT;

public class MWAdvancements implements DataProvider {
    public static final List<MilkywayAdvancement> ENTRIES = new ArrayList<>();
    public static final MilkywayAdvancement START = null,

    ROOT = create("root", b -> b.icon(com.simibubi.create.AllItems.BRASS_HAND)
            .title("Welcome to the Milkyway")
            .description("Advancement tree for Milkyway Core")
            .awardedForFree()
            .special(SILENT)),
    //CONVERTER = create("converter", b -> b.icon(AllBlocks.MECHANICAL_CONVERTER)
    //        .title("The Sturdiest Rocks")
    //        .description("Assemble a Sturdy Sheet by refining Powdered Obsidian")
    //        .after(ROOT)),
    //TARNISHED_CREATIVITY = create("tarnished_creativity", b -> b.icon(AllItems.TARNISHED_INGOT)
    //        .title("The Sturdiest Rocks")
    //        .description("Assemble a Sturdy Sheet by refining Powdered Obsidian")
    //        .whenIconCollected()
    //        .after(CONVERTER)),
    //CREATIVITY = create("creativity", b -> b.icon(AllItems.CREATIVE_INGOT)
    //        .title("The Sturdiest Rocks")
    //        .description("Assemble a Sturdy Sheet by refining Powdered Obsidian")
    //        .whenIconCollected()
    //        .after(TARNISHED_CREATIVITY)),
    //CREATIVITY_CRAFTING = create("creativity_factory", b -> b.icon(AllBlocks.MECHANICAL_CONVERTER)
    //        .title("Track Factory")
    //        .description("Produce more than 1000 Ingots of Creativity with the same Mechanical Converter")
    //        .after(CREATIVITY)
    //        .special(EXPERT)),






    END = null;

    private static MilkywayAdvancement create(String id, UnaryOperator<MilkywayAdvancement.Builder> b) {
        return new MilkywayAdvancement(id, b);
    }

    // Datagen

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .create();
    private final DataGenerator generator;

    public MWAdvancements(DataGenerator generatorIn) {
        this.generator = generatorIn;
    }

    @Override
    public void run(HashCache cache) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId()))
                throw new IllegalStateException("Duplicate advancement " + p_204017_3_.getId());

            Path path1 = getPath(path, p_204017_3_);

            try {
                DataProvider.save(GSON, cache, p_204017_3_.deconstruct()
                        .serializeToJson(), path1);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't save advancement {}", path1, ioexception);
            }
        };

        for (MilkywayAdvancement advancement : ENTRIES)
            advancement.save(consumer);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId()
                .getNamespace() + "/advancements/"
                + advancementIn.getId()
                .getPath()
                + ".json");
    }

    @Override
    public String getName() {
        return "Create's Advancements";
    }

    public static JsonObject provideLangEntries() {
        JsonObject object = new JsonObject();
        for (MilkywayAdvancement advancement : ENTRIES)
            advancement.appendToLang(object);
        return object;
    }

    public static void register() {}

}
