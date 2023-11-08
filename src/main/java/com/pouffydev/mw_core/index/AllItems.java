package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.content.items.ChromaticSteelItem;
import com.pouffydev.mw_core.content.items.RadiantSteelItem;
import com.pouffydev.mw_core.content.items.ShadowSteelItem;
import com.pouffydev.mw_core.content.items.dohickies.CreativeColor;
import com.pouffydev.mw_core.content.items.dohickies.FloatingParticleItem;
import com.pouffydev.mw_core.content.items.dohickies.RadiantQuartzItem;
import com.pouffydev.mw_core.content.items.tools.blade.TarnishedBladeItem;
import com.pouffydev.mw_core.content.items.tools.deforester.TarnishedDeforesterItem;
import com.pouffydev.mw_core.content.items.tools.excavator.TarnishedExcavatorItem;
import com.pouffydev.mw_core.foundation.MWRegistrate;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;

import static com.pouffydev.mw_core.index.MilkywayRegistryUtils.*;

@SuppressWarnings("unused")
public class AllItems {
    private static final MWRegistrate REGISTRATE = (MWRegistrate) MWCore.registrate().creativeModeTab(() -> MWCore.itemGroup);
    
    public static final ItemEntry<Item> slimesteel = sheet("slimesteel"), queensSlime = sheet("queens_slime"), hepatizon = sheet("hepatizon"), constantan = sheet("constantan"),
            roseGold = sheet("rose_gold"), amethystBronze = sheet("amethyst_bronze"), cobalt = sheet("cobalt"), manyullyn = sheet("manyullyn"), diamond = sheet("diamond"),
            electrum = sheet("electrum"), enderium = sheet("enderium"), invar = sheet("invar"), lead = sheet("lead"), lumium = sheet("lumium"), netherite = sheet("netherite"),
            nickel = sheet("nickel"), signalum = sheet("signalum"), silver = sheet("silver"), tin = sheet("tin");
    public static final ItemEntry<Item> sturdyBrass = sturdySheet("brass"), sturdySteel = sturdySheet("steel"), sturdyZinc = sturdySheet("zinc"),
            sturdyGold = sturdySheet("gold"), sturdyIron = sturdySheet("iron"), sturdyCopper = sturdySheet("copper"), sturdyTin = sturdySheet("tin"),
            sturdyLead = sturdySheet("lead"), sturdyAluminum = sturdySheet("aluminum"), sturdyBronze = sturdySheet("bronze")
                    ;
    public static final ItemEntry<Item> reinforcedBrass = reinforcedSheet("brass"), reinforcedSteel = reinforcedSheet("steel"), reinforcedZinc = reinforcedSheet("zinc"),
            reinforcedGold = reinforcedSheet("gold"), reinforcedIron = reinforcedSheet("iron"), reinforcedCopper = reinforcedSheet("copper"), reinforcedTin = reinforcedSheet("tin"),
            reinforcedLead = reinforcedSheet("lead"), reinforcedAluminum = reinforcedSheet("aluminum"), reinforcedBronze = reinforcedSheet("bronze")
                    ;
    public static final ItemEntry<Item> unprocessedBrass = unprocessedSheet("brass"), unprocessedSteel = unprocessedSheet("steel"), unprocessedZinc = unprocessedSheet("zinc"),
            unprocessedGold = unprocessedSheet("gold"), unprocessedIron = unprocessedSheet("iron"), unprocessedCopper = unprocessedSheet("copper"), unprocessedTin = unprocessedSheet("tin"),
            unprocessedLead = unprocessedSheet("lead"), unprocessedAluminum = unprocessedSheet("aluminum"), unprocessedBronze = unprocessedSheet("bronze")
                    ;
    public static final ItemEntry<Item> reprocessedBrass = reprocessedSheet("brass"), reprocessedSteel = reprocessedSheet("steel"), reprocessedZinc = reprocessedSheet("zinc"),
            reprocessedGold = reprocessedSheet("gold"), reprocessedIron = reprocessedSheet("iron"), reprocessedCopper = reprocessedSheet("copper"), reprocessedTin = reprocessedSheet("tin"),
            reprocessedLead = reprocessedSheet("lead"), reprocessedAluminum = reprocessedSheet("aluminum"), reprocessedBronze = reprocessedSheet("bronze")
                    ;
    public static final ItemEntry<RadiantQuartzItem> RADIANT_QUARTZ_SHEET = REGISTRATE.item("radiant_quartz_sheet", RadiantQuartzItem::new)
            .properties(Item.Properties::fireResistant)
            .register();
    public static final ItemEntry<RadiantQuartzItem> RADIANT_QUARTZ = REGISTRATE.item("radiant_quartz", RadiantQuartzItem::new)
            .properties(Item.Properties::fireResistant)
            .tag(gems("radiant_quartz")).tag(gems())
            .register();
    public static final ItemEntry<RadiantQuartzItem> polishedRadiantQuartz = REGISTRATE.item("polished_radiant_quartz", RadiantQuartzItem::new)
            .properties(Item.Properties::fireResistant)
            .register();

    public static final ItemEntry<RadiantQuartzItem> radiantQuartzDust = REGISTRATE.item("radiant_quartz_dust", RadiantQuartzItem::new)
            .properties(Item.Properties::fireResistant)
            .color(() -> CreativeColor::new)
            .register();
    public static final ItemEntry<FloatingParticleItem> creativeIngot = REGISTRATE.item("creative_ingot", (props) -> new FloatingParticleItem(props, ParticleTypes.REVERSE_PORTAL, ParticleTypes.PORTAL, ParticleTypes.DRIPPING_OBSIDIAN_TEAR, true))
            .properties(p->p.tab(MWCore.itemGroup).rarity(Rarity.EPIC).fireResistant())
            .tag(ingots("creative")).tag(ingots())
            .lang("Ingot of Creativity")
            .register();
    public static final ItemEntry<Item> creativeFiber = REGISTRATE.item("creative_fiber", Item::new)
            .properties(p->p.tab(MWCore.itemGroup).fireResistant())
            .register();
    public static final ItemEntry<Item> creatiteInfusedChromaticSteel = REGISTRATE.item("creatite_infused_chromatic_steel", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .tag(ingots("creatite_infused_chromatic_steel")).tag(ingots())
            .register();
    public static final ItemEntry<ChromaticSteelItem> chromaticSteel = REGISTRATE.item("chromatic_steel", ChromaticSteelItem::new)
            .properties(p->p.tab(MWCore.itemGroup).rarity(Rarity.UNCOMMON))
            .tag(ingots("chromatic_steel")).tag(ingots())
            .register();
    public static final ItemEntry<RadiantSteelItem> radiantSteel = REGISTRATE.item("radiant_steel", (props) -> new RadiantSteelItem(props, ParticleTypes.END_ROD, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.GLOW))
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .register();
    public static final ItemEntry<ShadowSteelItem> shadowSteel = REGISTRATE.item("shadow_steel", (props) -> new ShadowSteelItem(props, ParticleTypes.ASH, ParticleTypes.ENTITY_EFFECT, ParticleTypes.SMOKE))
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .register();
    static String al = "aluminum";
    public static final ItemEntry<Item> alIngot = ingot(al), alNugget = nugget(al), alSheet = sheet(al), alUps = unprocessedSheet(al), alSs = sturdySheet(al), alRps = reprocessedSheet(al), alRfs = reinforcedSheet(al), alRaw = rawOre(al);
    public static final ItemEntry<SwordItem> alSword = sword(al, AllToolTiers.ALUMINUM, 4, -2.4F);
    public static final ItemEntry<PickaxeItem> alPick = pickaxe(al, AllToolTiers.ALUMINUM, 2, -2.8F);
    public static final ItemEntry<AxeItem> alAxe = axe(al, AllToolTiers.ALUMINUM, 7.5F, -3.2F);
    public static final ItemEntry<ShovelItem> alShovel = shovel(al, AllToolTiers.ALUMINUM, 2.0F, -3.0F);
    public static final ItemEntry<HoeItem> alHoe = hoe(al, AllToolTiers.ALUMINUM, -1, -2.0F);
    public static final ItemEntry<Item> diamondQuartz = REGISTRATE.item("diamond_quartz", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .register();
    public static final ItemEntry<Item> polishedDiamondQuartz = REGISTRATE.item("polished_diamond_quartz", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .register();
    public static final ItemEntry<Item> transiumIngot = REGISTRATE.item("transium_ingot", Item::new)
            .properties(p->p.tab(MWCore.itemGroup))
            .tag(ingots("transium"))
            .tag(ingots())
            .register();
    public static final ItemEntry<FloatingParticleItem> tarnishedIngot = REGISTRATE.item("tarnished_ingot", (props) -> new FloatingParticleItem(props, DustParticleOptions.REDSTONE, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.LAVA, true))
            .properties(p->p.tab(MWCore.itemGroup).fireResistant().rarity(tarnished))
            .tag(ingots("tarnished"))
            .tag(ingots())
            .lang("Ingot of Tarnished Creativity")
            .register();
    public static final ItemEntry<FloatingParticleItem> tarnishedNugget = REGISTRATE.item("tarnished_nugget", (props) -> new FloatingParticleItem(props, DustParticleOptions.REDSTONE, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.LAVA, true))
            .properties(p->p.tab(MWCore.itemGroup).fireResistant().rarity(tarnished))
            .tag(nuggets("tarnished"))
            .tag(nuggets())
            .lang("Nugget of Tarnished Creativity")
            .register();
    public static final ItemEntry<TarnishedDeforesterItem> DEFORESTER = REGISTRATE.item("tarnished_deforester", TarnishedDeforesterItem::new)
            .properties(p -> p.stacksTo(1).rarity(tarnished))
            .model(AssetLookup.itemModelWithPartials())
            .register();
    public static final ItemEntry<TarnishedBladeItem> BLADE = REGISTRATE.item("tarnished_blade", TarnishedBladeItem::new)
            .properties(p -> p.stacksTo(1).rarity(tarnished))
            .model(AssetLookup.itemModelWithPartials())
            .register();
    public static final ItemEntry<TarnishedExcavatorItem> EXCAVATOR = REGISTRATE.item("tarnished_excavator", TarnishedExcavatorItem::new)
            .properties(p -> p.stacksTo(1).rarity(tarnished))
            .model(AssetLookup.itemModelWithPartials())
            .register();
    public static void register() {
    }
}
