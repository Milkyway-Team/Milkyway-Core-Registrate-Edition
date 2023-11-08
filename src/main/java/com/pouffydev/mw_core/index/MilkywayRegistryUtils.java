package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;

import static com.pouffydev.mw_core.MWCore.registrate;
@SuppressWarnings("unused")
public class MilkywayRegistryUtils {
    /**
     * Milkyway's Fluid Registry Helpers :3
     * @see FluidEntry
     */
    public static FluidEntry<ForgeFlowingFluid.Flowing> registerFluid(String name, int temperature, int viscosity, int density, int luminosity, int slopeFindDistance, int levelDecreasePerBlock, int tickRate, float explosionResistance, boolean copyWater) {
        if (copyWater){
            return registrate.standardMWFluid(name, NoColorFluidAttributes::new)
                    .lang(name)
                    .tag(MWTags.forgeFluidTag(name))
                    .tag(MWTags.minecraftFluidTag("water"))
                    .attributes(b -> b
                            .viscosity(viscosity)
                            .density(density)
                            .luminosity(luminosity)
                            .temperature(temperature))
                    .properties(p -> p
                            .levelDecreasePerBlock(levelDecreasePerBlock)
                            .tickRate(tickRate)
                            .slopeFindDistance(slopeFindDistance)
                            .explosionResistance(explosionResistance))
                    .register();
        }
        else {
            return registrate.standardMWFluid(name, NoColorFluidAttributes::new)
                    .lang(name)
                    .tag(MWTags.forgeFluidTag(name))
                    .attributes(b -> b
                            .viscosity(viscosity)
                            .density(density)
                            .luminosity(luminosity)
                            .temperature(temperature))
                    .properties(p -> p
                            .levelDecreasePerBlock(levelDecreasePerBlock)
                            .tickRate(tickRate)
                            .slopeFindDistance(slopeFindDistance)
                            .explosionResistance(explosionResistance))
                    .register();
        }
    }
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MWCore.ID);
    public static FluidObject<ForgeFlowingFluid> registerTinkersFluid(String name, int temp) {
        String still = String.format("mw_core:fluid/%s/still", name);
        String flow = String.format("mw_core:fluid/%s/flowing", name);
        return FLUIDS.register(name, FluidAttributes.builder(new ResourceLocation(still), new ResourceLocation(flow)).density(2000).viscosity(10000).temperature(temp).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA), Material.LAVA, 15);
    }
    public static class NoColorFluidAttributes extends FluidAttributes {
        protected NoColorFluidAttributes(Builder builder, Fluid fluid) {super(builder, fluid);}@Override public int getColor(BlockAndTintGetter world, BlockPos pos) {return 0x00ffffff;}}

    /**
     * Pre-Built ItemEntry's for Milkyway's Items ranging from Swords to Sheets
     * @method itemRegistrate
     * @see ItemEntry
     */
    private static final CreateRegistrate itemRegistrate = MWCore.registrate().creativeModeTab(() -> MWCore.itemGroup);
    public static ItemEntry<SwordItem> sword(String material, Tier tier, int pAttackDamageModifier, float pAttackSpeedModifier) {return itemRegistrate.item(material + "_sword", p -> new SwordItem(tier, pAttackDamageModifier, pAttackSpeedModifier, p)).properties(p->p.tab(MWCore.itemGroup)).model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_sword")).register();}
    public static ItemEntry<PickaxeItem> pickaxe(String material, Tier tier, int pAttackDamageModifier, float pAttackSpeedModifier) {return itemRegistrate.item(material + "_pickaxe", p -> new PickaxeItem(tier, pAttackDamageModifier, pAttackSpeedModifier, p)).properties(p->p.tab(MWCore.itemGroup)).model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_pickaxe")).register();}
    public static ItemEntry<AxeItem> axe(String material, Tier tier, float pAttackDamageModifier, float pAttackSpeedModifier) {return itemRegistrate.item(material + "_axe", p -> new AxeItem(tier, pAttackDamageModifier, pAttackSpeedModifier, p)).properties(p->p.tab(MWCore.itemGroup)).model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_axe")).register();}
    public static ItemEntry<ShovelItem> shovel(String material, Tier tier, float pAttackDamageModifier, float pAttackSpeedModifier) {return itemRegistrate.item(material + "_shovel", p -> new ShovelItem(tier, pAttackDamageModifier, pAttackSpeedModifier, p)).properties(p->p.tab(MWCore.itemGroup)).model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_shovel")).register();}
    public static ItemEntry<HoeItem> hoe(String material, Tier tier, int attackDamageBaseline, float attackSpeed) {return itemRegistrate.item(material + "_hoe", p -> new HoeItem(tier, attackDamageBaseline, attackSpeed, p)).properties(p->p.tab(MWCore.itemGroup)).model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_hoe")).register();}
    public static ItemEntry<Item> sheet(String material) {return itemRegistrate.item(material + "_sheet", Item::new).properties(p->p.tab(MWCore.itemGroup)).tag(plates(material)).tag(plates()).register();}
    public static ItemEntry<Item> ingot(String material) {return itemRegistrate.item(material + "_ingot", Item::new).properties(p->p.tab(MWCore.itemGroup)).tag(ingots(material)).tag(ingots()).register();}
    public static ItemEntry<Item> nugget(String material) {return itemRegistrate.item(material + "_nugget", Item::new).properties(p->p.tab(MWCore.itemGroup)).tag(nuggets(material)).tag(nuggets()).register();}
    public static ItemEntry<Item> rawOre(String material) {return itemRegistrate.item("raw_" + material, Item::new).properties(p->p.tab(MWCore.itemGroup)).tag(rawMaterials(material)).tag(rawMaterials()).register();}
    public static ItemEntry<Item> sturdySheet(String material) {return itemRegistrate.item("sturdy_" + material + "_sheet", Item::new).properties(p->p.tab(MWCore.itemGroup)).tag(MWTags.forgeItemTag("plates/sturdy/" + material)).tag(MWTags.forgeItemTag("plates/sturdy")).register();}
    public static ItemEntry<Item> reinforcedSheet(String material) {return itemRegistrate.item("reinforced_" + material + "_sheet", Item::new).properties(p->p.tab(MWCore.itemGroup)).tag(MWTags.forgeItemTag("plates/reinforced/" + material)).tag(MWTags.forgeItemTag("plates/reinforced")).register();}
    public static ItemEntry<Item> unprocessedSheet(String material) {return itemRegistrate.item("unprocessed_" + material + "_sheet", Item::new).properties(p->p.tab(MWCore.itemGroup)).register();}
    public static ItemEntry<Item> reprocessedSheet(String material) {return itemRegistrate.item("reprocessed_" + material + "_sheet", Item::new).properties(p->p.tab(MWCore.itemGroup)).register();}
    public static ItemEntry<Item> mechanism(String type) {return itemRegistrate.item(type + "_mechanism", Item::new).properties(p->p.tab(MWCore.itemGroup)).register();}
    public static ItemEntry<Item> mechanism(String type, Rarity rarity) {return itemRegistrate.item(type + "_mechanism", Item::new).properties(p->p.tab(MWCore.itemGroup).rarity(rarity)).register();}
    public static ItemEntry<SequencedAssemblyItem> incompleteMechanism(String type) {return itemRegistrate.item("incomplete_" + type + "_mechanism", SequencedAssemblyItem::new).properties(p->p).register();}
    public static ItemEntry<SequencedAssemblyItem> incompleteMechanism(String type, Rarity rarity) {return itemRegistrate.item("incomplete_" + type + "_mechanism", SequencedAssemblyItem::new).properties(p->p.rarity(rarity)).register();}
    
    /**
     * Pre-Built TagKey's for Milkyway's Items for use in TagGen
     * @see TagKey
     */
    public static TagKey<Item> gems(String material) {
        return MWTags.forgeItemTag("gems/" + material);
    }
    public static TagKey<Item> rawMaterials(String material) {
        return MWTags.forgeItemTag("raw_materials/" + material);
    }
    public static TagKey<Item> ingots(String material) {
        return MWTags.forgeItemTag("ingots/" + material);
    }
    public static TagKey<Item> nuggets(String material) {
        return MWTags.forgeItemTag("nuggets/" + material);
    }
    public static TagKey<Item> plates(String material) {
        return MWTags.forgeItemTag("plates/" + material);
    }
    public static TagKey<Item> plates() {
        return MWTags.forgeItemTag("plates");
    }
    public static TagKey<Item> rawMaterials() {
        return MWTags.forgeItemTag("raw_materials");
    }
    public static TagKey<Item> ingots() {
        return MWTags.forgeItemTag("ingots");
    }
    public static TagKey<Item> randomiumBlacklist() {
        return MWTags.modItemTag("randomium", "blacklist");
    }
    public static TagKey<Item> nuggets() {return MWTags.forgeItemTag("nuggets");}
    public static TagKey<Item> gems() {
        return MWTags.forgeItemTag("gems");
    }
    
    /**
     * Pre-Built Rarity's for Milkyway's Items for use in Item.Properties
     * @see Rarity
     */
    public static Rarity tarnished = Rarity.create("tarnished", ChatFormatting.DARK_RED);
    
    
}
