package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.TagGen;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collections;

import static com.pouffydev.mw_core.index.MWTags.NameSpace.*;

public class MWTags {
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> optionalTag(IForgeRegistry<T> registry,
                                                                           ResourceLocation id) {
        return registry.tags()
                .createOptionalTagKey(id, Collections.emptySet());
    }
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> minecraftTag(IForgeRegistry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation("minecraft", path));
    }
    public static TagKey<Fluid> minecraftFluidTag(String water) {
        return minecraftTag(ForgeRegistries.FLUIDS, water);
    }
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> modTag(IForgeRegistry<T> registry, String modID, String path) {
        return optionalTag(registry, new ResourceLocation(modID, path));
    }
    public static TagKey<Item> modItemTag(String modID, String path) {
        return modTag(ForgeRegistries.ITEMS, modID, path);
    }
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> forgeTag(IForgeRegistry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation("forge", path));
    }

    public static TagKey<Block> forgeBlockTag(String path) {
        return forgeTag(ForgeRegistries.BLOCKS, path);
    }
    
    public static TagKey<Item> mechanismTag(String type) {
        return modItemTag("mw_core","mechanisms/" + type);
    }
    public static TagKey<Item> casingTag(String type) {
        return modItemTag("mw_core","casings/" + type);
    }
    public static TagKey<Item> forgeItemTag(String path) {
        return forgeTag(ForgeRegistries.ITEMS, path);
    }

    public static TagKey<Fluid> forgeFluidTag(String path) {
        return forgeTag(ForgeRegistries.FLUIDS, path);
    }

    @Deprecated(forRemoval = true)
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> axeOrPickaxe() {
        return TagGen.axeOrPickaxe();
    }

    @Deprecated(forRemoval = true)
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> axeOnly() {
        return TagGen.axeOnly();
    }

    @Deprecated(forRemoval = true)
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> pickaxeOnly() {
        return TagGen.pickaxeOnly();
    }

    @Deprecated(forRemoval = true)
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, ItemBuilder<BlockItem, BlockBuilder<T, P>>> tagBlockAndItem(
            String... path) {
        return TagGen.tagBlockAndItem(path);
    }
    public enum NameSpace {
        MOD(MWCore.ID, false, true),
        FORGE("forge"),
        TIC("tconstruct"),
        CREATE(Create.ID)

        ;

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;

        NameSpace(String id) {
            this(id, true, false);
        }

        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }
    public enum AllItemTags {
         RADIANT_QUARTZ(FORGE, "gems/radiant_quartz"),
        CRUSHED_ORE(CREATE, "crushed_ores"),
        STURDY_SHEETS(FORGE, "plates/sturdy"),
        AMMUNITION(MOD, "firearms/ammunition"),

        ;

        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        AllItemTags() {
            this(MOD);
        }

        AllItemTags(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllItemTags(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllItemTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        AllItemTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(ForgeRegistries.ITEMS, id);
            } else {
                tag = ItemTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Item item) {
            return item.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        }

        private static void init() {
        }
    }
    public enum AllBlockTags {
        COMBUSTION_HEATERS(MOD, "combustion_heaters"),
        EXCAVATOR_BLACKLIST(MOD, "excavator_blacklist"),
        DRAGONESS_BLOCK_UNPLACEABLE(MOD, "dragoness_block_unplaceable"),
        ;
        public final TagKey<Block> tag;
        public final boolean alwaysDatagen;
        AllBlockTags() {
            this(MOD);
        }
        AllBlockTags(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }
        AllBlockTags(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllBlockTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        AllBlockTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(ForgeRegistries.BLOCKS, id);
            } else {
                tag = BlockTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }
        @SuppressWarnings("deprecation")
        public boolean matches(Block block) {
            return block.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof BlockItem blockItem && matches(blockItem.getBlock());
        }

        public boolean matches(BlockState state) {
            return state.is(tag);
        }

        private static void init() {
        }
    }
    public enum AllFluidTags {
        TRANSIUM(MOD, "molten_transium"),

        ;

        public final TagKey<Fluid> tag;
        public final boolean alwaysDatagen;

        AllFluidTags() {
            this(MOD);
        }

        AllFluidTags(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllFluidTags(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllFluidTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        AllFluidTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(ForgeRegistries.FLUIDS, id);
            } else {
                tag = FluidTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Fluid fluid) {
            return fluid.is(tag);
        }

        public boolean matches(FluidState state) {
            return state.is(tag);
        }

        private static void init() {
        }
    }
    public static void init() {
        AllItemTags.init();
        AllBlockTags.init();
        AllFluidTags.init();
    }
}
