package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.content.items.ChromaticSteelItem;
import com.pouffydev.mw_core.content.items.RadiantSteelItem;
import com.pouffydev.mw_core.content.items.ShadowSteelItem;
import com.pouffydev.mw_core.content.items.dohickies.FloatingParticleItem;
import com.pouffydev.mw_core.content.items.dohickies.RadiantQuartzItem;
import com.pouffydev.mw_core.content.items.tools.blade.TarnishedBladeItem;
import com.pouffydev.mw_core.content.items.tools.deforester.TarnishedDeforesterItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.IEventBus;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;

@SuppressWarnings("unused")
public class AllItems {
    private static final CreateRegistrate REGISTRATE = MWCore.registrate()
            .creativeModeTab(() -> MWCore.itemGroup);

    private static ItemEntry<Item> sheet(String material) {
        return REGISTRATE
                .item(material + "_sheet", Item::new)
                .properties(p->p.tab(MWCore.itemGroup))
                .tag(AllTags.forgeItemTag("plates/" + material))
                .tag(AllTags.forgeItemTag("plates"))
                .register();
    }
    private static ItemEntry<Item> sturdySheet(String material) {
        return REGISTRATE
                .item("sturdy_" + material + "_sheet", Item::new)
                .properties(p->p.tab(MWCore.itemGroup))
                .tag(AllTags.forgeItemTag("plates/sturdy/" + material))
                .tag(AllTags.forgeItemTag("plates/sturdy"))
                .register();
    }
    private static ItemEntry<Item> reinforcedSheet(String material) {
        return REGISTRATE
                .item("reinforced_" + material + "_sheet", Item::new)
                .properties(p->p.tab(MWCore.itemGroup))
                .tag(AllTags.forgeItemTag("plates/reinforced/" + material))
                .tag(AllTags.forgeItemTag("plates/reinforced"))
                .register();
    }
    private static ItemEntry<Item> unprocessedSheet(String material) {
        return REGISTRATE
                .item("unprocessed_" + material + "_sheet", Item::new)
                .properties(p->p.tab(MWCore.itemGroup))
                .register();
    }
    private static ItemEntry<Item> reprocessedSheet(String material) {
        return REGISTRATE
                .item("reprocessed_" + material + "_sheet", Item::new)
                .properties(p->p.tab(MWCore.itemGroup))
                .register();
    }

    public static final ItemEntry<Item> SLIMESTEEL = sheet("slimesteel"), QUEENS_SLIME = sheet("queens_slime"), HEPATIZON = sheet("hepatizon"), CONSTANTAN = sheet("constantan"),
            ROSE_GOLD = sheet("rose_gold"), AMETHYST_BRONZE = sheet("amethyst_bronze"), COBALT = sheet("cobalt"), MANYULLYN = sheet("manyullyn"), DIAMOND = sheet("diamond"),
            ELECTRUM = sheet("electrum"), ENDERIUM = sheet("enderium"), INVAR = sheet("invar"), LEAD = sheet("lead"), LUMIUM = sheet("lumium"), NETHERITE = sheet("netherite"),
            NICKEL = sheet("nickel"), SIGNALUM = sheet("signalum"), SILVER = sheet("silver"), TIN = sheet("tin")
            ;
    public static final ItemEntry<Item> STURDY_BRASS = sturdySheet("brass"), STURDY_STEEL = sturdySheet("steel"), STURDY_ZINC = sturdySheet("zinc"),
            STURDY_GOLD = sturdySheet("gold"), STURDY_IRON = sturdySheet("iron"), STURDY_COPPER = sturdySheet("copper"), STURDY_TIN = sturdySheet("tin"),
            STURDY_LEAD = sturdySheet("lead")
            ;
    public static final ItemEntry<Item> REINFORCED_BRASS = reinforcedSheet("brass"), REINFORCED_STEEL = reinforcedSheet("steel"), REINFORCED_ZINC = reinforcedSheet("zinc"),
            REINFORCED_GOLD = reinforcedSheet("gold"), REINFORCED_IRON = reinforcedSheet("iron"), REINFORCED_COPPER = reinforcedSheet("copper"), REINFORCED_TIN = reinforcedSheet("tin"),
            REINFORCED_LEAD = reinforcedSheet("lead")
                    ;
    public static final ItemEntry<Item> UNPROCESSED_BRASS = unprocessedSheet("brass"), UNPROCESSED_STEEL = unprocessedSheet("steel"), UNPROCESSED_ZINC = unprocessedSheet("zinc"),
            UNPROCESSED_GOLD = unprocessedSheet("gold"), UNPROCESSED_IRON = unprocessedSheet("iron"), UNPROCESSED_COPPER = unprocessedSheet("copper"), UNPROCESSED_TIN = unprocessedSheet("tin"),
            UNPROCESSED_LEAD = unprocessedSheet("lead")
                    ;
    public static final ItemEntry<Item> REPROCESSED_BRASS = reprocessedSheet("brass"), REPROCESSED_STEEL = reprocessedSheet("steel"), REPROCESSED_ZINC = reprocessedSheet("zinc"),
            REPROCESSED_GOLD = reprocessedSheet("gold"), REPROCESSED_IRON = reprocessedSheet("iron"), REPROCESSED_COPPER = reprocessedSheet("copper"), REPROCESSED_TIN = reprocessedSheet("tin"),
            REPROCESSED_LEAD = reprocessedSheet("lead")
                    ;

    public static final ItemEntry<RadiantQuartzItem> RADIANT_QUARTZ_SHEET = REGISTRATE.item("radiant_quartz_sheet", RadiantQuartzItem::new)
            .properties(Item.Properties::fireResistant)
            .register();
    public static final ItemEntry<RadiantQuartzItem> RADIANT_QUARTZ = REGISTRATE.item("radiant_quartz", RadiantQuartzItem::new)
            .properties(Item.Properties::fireResistant)
            .tag(AllTags.forgeItemTag("gems/radiant_quartz"))
            .register();

    public static final ItemEntry<RadiantQuartzItem> POLISHED_RADIANT_QUARTZ = REGISTRATE.item("polished_radiant_quartz", RadiantQuartzItem::new)
            .properties(Item.Properties::fireResistant)
            .register();

    public static final ItemEntry<RadiantQuartzItem> RADIANT_QUARTZ_DUST = REGISTRATE.item("radiant_quartz_dust", RadiantQuartzItem::new)
            .properties(Item.Properties::fireResistant)
            .register();
    public static final ItemEntry<FloatingParticleItem> CREATIVE_INGOT = REGISTRATE.item("creative_ingot", (props) -> new FloatingParticleItem(props, ParticleTypes.REVERSE_PORTAL, ParticleTypes.PORTAL, ParticleTypes.DRIPPING_OBSIDIAN_TEAR, true))
            .properties(p->p.tab(MWCore.itemGroup).rarity(Rarity.EPIC).fireResistant())
            .tag(AllTags.forgeItemTag("ingots/creative"))
            .lang("Ingot of Creativity")
            .register();
    public static final ItemEntry<Item> CREATIVE_FIBER = REGISTRATE.item("creative_fiber", Item::new)
            .properties(p->p.tab(MWCore.itemGroup).fireResistant())
            .register();
    public static final ItemEntry<Item> CREATITE_INFUSED_CHROMATIC_STEEL = REGISTRATE.item("creatite_infused_chromatic_steel", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .tag(AllTags.forgeItemTag("ingots/creatite_infused_chromatic_steel"))
            .register();
    public static final ItemEntry<ChromaticSteelItem> CHROMATIC_STEEL = REGISTRATE.item("chromatic_steel", ChromaticSteelItem::new)
            .properties(p->p.tab(MWCore.itemGroup).rarity(Rarity.UNCOMMON))
            .tag(AllTags.forgeItemTag("ingots/chromatic_steel"))
            .register();
    public static final ItemEntry<RadiantSteelItem> RADIANT_STEEL = REGISTRATE.item("radiant_steel", (props) -> new RadiantSteelItem(props, ParticleTypes.END_ROD, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.GLOW))
                    .properties(p -> p.rarity(Rarity.UNCOMMON))
                    .register();
    public static final ItemEntry<ShadowSteelItem> SHADOW_STEEL = REGISTRATE.item("shadow_steel", (props) -> new ShadowSteelItem(props, ParticleTypes.ASH, ParticleTypes.ENTITY_EFFECT, ParticleTypes.SMOKE))
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .register();
    public static final ItemEntry<Item> RAW_ALUMINUM = REGISTRATE.item("raw_aluminum", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .tag(AllTags.forgeItemTag("raw_materials/aluminum"))
            .register();
    public static final ItemEntry<Item> ALUMINUM_INGOT = REGISTRATE.item("aluminum_ingot", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .recipe((ctx, prov) -> SimpleCookingRecipeBuilder.smelting(Ingredient.of(AllItems.RAW_ALUMINUM.get()), AllItems.ALUMINUM_INGOT.get(), 0.7f, 200).unlockedBy("has_item", has(ctx.get())))
            .tag(AllTags.forgeItemTag("ingots/aluminum"))
            .register();

    public static final ItemEntry<Item> DIAMOND_QUARTZ = REGISTRATE.item("diamond_quartz", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .register();
    public static final ItemEntry<Item> POLISHED_DIAMOND_QUARTZ = REGISTRATE.item("polished_diamond_quartz", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .register();
    public static final ItemEntry<Item> TRANSIUM_INGOT = REGISTRATE.item("transium_ingot", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .tag(AllTags.forgeItemTag("ingots/transium"))
            .register();
    public static final ItemEntry<FloatingParticleItem> TARNISHED_INGOT = REGISTRATE.item("tarnished_ingot", (props) -> new FloatingParticleItem(props, DustParticleOptions.REDSTONE, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.LAVA, true))
            .properties(p->p.tab(MWCore.itemGroup).fireResistant().rarity(Rarity.EPIC))
            .tag(AllTags.forgeItemTag("ingots/tarnished"))
            .lang("Ingot of Tarnished Creativity")
            .register();
    public static final ItemEntry<TarnishedDeforesterItem> DEFORESTER = REGISTRATE.item("tarnished_deforester", TarnishedDeforesterItem::new)
            .properties(p -> p.stacksTo(1)
                    .rarity(Rarity.EPIC))
            .model(AssetLookup.itemModelWithPartials())
            .register();

    public static final ItemEntry<TarnishedBladeItem> BLADE = REGISTRATE.item("tarnished_blade", TarnishedBladeItem::new)
            .properties(p -> p.stacksTo(1)
                    .rarity(Rarity.EPIC))
            .model(AssetLookup.itemModelWithPartials())
            .register();



    public static void register(IEventBus eventBus) {
    }
}
