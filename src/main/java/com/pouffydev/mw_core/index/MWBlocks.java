package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.MWSpriteShifts;
import com.pouffydev.mw_core.content.block.contraptions.converter.ConverterBlock;
import com.pouffydev.mw_core.content.block.generators.combustion.CombustionEngineBlock;
import com.pouffydev.mw_core.content.block.generators.motor.TarnishedMotorBlock;
import com.pouffydev.mw_core.content.block.generators.motor.TarnishedMotorGenerator;
import com.pouffydev.mw_core.content.block.generators.reactor.ReactorCogwheelBlock;
import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlock;
import com.pouffydev.mw_core.content.block.generators.reactor.input.ReactorInputBlock;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.infuser.InfuserBlock;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.roller.RollerBlock;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.welder.WelderBlock;
import com.pouffydev.mw_core.foundation.MWRegistrate;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.*;
import com.simibubi.create.foundation.utility.Couple;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraftforge.common.Tags;

import static com.pouffydev.mw_core.index.MilkywayRegistryUtils.tarnished;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;

public class MWBlocks {
    private static final MWRegistrate REGISTRATE = (MWRegistrate) MWCore.registrate().creativeModeTab(
            () -> MWCore.itemGroup
    );

    public static final BlockEntry<Block> DEEPSLATE_ALUMINUM_ORE = REGISTRATE.block("deepslate_aluminum_ore", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE_GOLD_ORE)
            .properties(p -> p.color(MaterialColor.STONE))
            .properties(p -> p.requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE))
            .transform(TagGen.pickaxeOnly())
            .loot((lt, b) -> lt.add(b,
                    RegistrateBlockLootTables.createSilkTouchDispatchTable(b,
                            RegistrateBlockLootTables.applyExplosionDecay(b, LootItem.lootTableItem(AllItems.alRaw.get())
                                    .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
            .tag(BlockTags.NEEDS_STONE_TOOL)
            .tag(Tags.Blocks.ORES)
            .transform(tagBlockAndItem("ores/aluminum", "ores_in_ground/deepslate"))
            .tag(Tags.Items.ORES)
            .build()
            .simpleItem()
            .lang("Deepslate Bauxite Ore")
            .register();
    public static final BlockEntry<Block> ALUMINUM_ORE = REGISTRATE.block("aluminum_ore", Block::new)
            .initialProperties(() -> Blocks.GOLD_ORE)
            .properties(p -> p.color(MaterialColor.METAL))
            .properties(p -> p.requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
            .transform(TagGen.pickaxeOnly())
            .loot((lt, b) -> lt.add(b,
                    RegistrateBlockLootTables.createSilkTouchDispatchTable(b,
                            RegistrateBlockLootTables.applyExplosionDecay(b, LootItem.lootTableItem(AllItems.alRaw.get())
                                    .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
            .tag(BlockTags.NEEDS_STONE_TOOL)
            .tag(Tags.Blocks.ORES)
            .transform(tagBlockAndItem("ores/aluminum", "ores_in_ground/stone"))
            .tag(Tags.Items.ORES)
            .build()
            .simpleItem()
            .lang("Bauxite Ore")
            .register();

    public static final BlockEntry<CasingBlock> RADIANT_CASING = REGISTRATE.block("radiant_casing", CasingBlock::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.lightLevel(s -> 15))
            .transform(BuilderTransformers.casing(() -> MWSpriteShifts.RADIANT_CASING))
            .simpleItem()
            .lang("Radiant Casing")
            .register();
    public static final BlockEntry<CasingBlock> TARNISHED_CASING = REGISTRATE.block("tarnished_casing", CasingBlock::new)
            .initialProperties(() -> Blocks.COPPER_BLOCK)
            .transform(BuilderTransformers.casing(() -> MWSpriteShifts.TARNISHED_CASING))
            .simpleItem()
            .item()
            .properties(p -> p.rarity(tarnished))
            .build()
            .lang("Tarnished Casing")
            .register();

    public static final BlockEntry<CasingBlock> ALUMINUM_CASING = REGISTRATE.block("aluminum_casing", CasingBlock::new)
            .initialProperties(() -> Blocks.COPPER_BLOCK)
            .transform(BuilderTransformers.casing(() -> MWSpriteShifts.ALUMINUM_CASING))
            .simpleItem()
            .lang("Aluminum Casing")
            .register();

    public static final BlockEntry<TarnishedMotorBlock> TARNISHED_MOTOR =
            REGISTRATE.block("tarnished_motor", TarnishedMotorBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.color(MaterialColor.COLOR_RED))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .transform(TagGen.pickaxeOnly())
                    .blockstate(new TarnishedMotorGenerator()::generate)
                    .transform(BlockStressDefaults.setCapacity(64.0))
                    .transform(BlockStressDefaults.setGeneratorSpeed(() -> Couple.create(0, 128)))
                    .item()
                    .properties(p -> p.rarity(tarnished))
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<Block> ALUMINUM_BLOCK = REGISTRATE.block("aluminum_block", Block::new)
            .initialProperties(() -> Blocks.COPPER_BLOCK)
            .properties(p -> p.color(MaterialColor.GLOW_LICHEN))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .tag(BlockTags.BEACON_BASE_BLOCKS)
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.getEntry(), 1)
                    .define('T', MWTags.forgeItemTag("ingots/aluminum"))
                    .pattern("TTT")
                    .pattern("TTT")
                    .pattern("TTT")
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                    .save(prov))
            .transform(tagBlockAndItem("storage_blocks/aluminum"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Aluminum")
            .register();

    public static final BlockEntry<Block> RADIANT_QUARTZ_BLOCK = REGISTRATE.block("radiant_quartz_block", Block::new)
            .initialProperties(() -> Blocks.GLOWSTONE)
            .properties(p -> p.color(MaterialColor.COLOR_YELLOW))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.lightLevel(s -> 15))
            .transform(pickaxeOnly())
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .tag(BlockTags.BEACON_BASE_BLOCKS)
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.getEntry(), 1)
                    .define('T', MWTags.forgeItemTag("gems/radiant_quartz"))
                    .pattern("TTT")
                    .pattern("TTT")
                    .pattern("TTT")
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                    .save(prov))
            .transform(tagBlockAndItem("storage_blocks/radiant_quartz"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Radiant Quartz")
            .register();

    public static final BlockEntry<Block> CREATIVE_BLOCK = REGISTRATE.block("creative_block", Block::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_MAGENTA))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .tag(BlockTags.NEEDS_DIAMOND_TOOL)
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .tag(BlockTags.BEACON_BASE_BLOCKS)
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.getEntry(), 1)
                    .define('T', MWTags.forgeItemTag("ingots/creative"))
                    .pattern("TTT")
                    .pattern("TTT")
                    .pattern("TTT")
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                    .save(prov))
            .transform(tagBlockAndItem("storage_blocks/creative"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Creativity")
            .register();

    public static final BlockEntry<Block> TARNISHED_BLOCK = REGISTRATE.block("tarnished_block", Block::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_MAGENTA))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .tag(BlockTags.NEEDS_DIAMOND_TOOL)
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .tag(BlockTags.BEACON_BASE_BLOCKS)
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.getEntry(), 1)
                    .define('T', MWTags.forgeItemTag("ingots/tarnished"))
                    .pattern("TTT")
                    .pattern("TTT")
                    .pattern("TTT")
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                    .save(prov))
            .transform(tagBlockAndItem("storage_blocks/tarnished"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .item()
            .properties(p -> p.rarity(tarnished))
            .build()
            .lang("Block of Tarnished Creativity")
            .register();

    public static final BlockEntry<ConverterBlock> MECHANICAL_CONVERTER =
            REGISTRATE.block("converter", ConverterBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.horizontalBlockProvider(true))
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item(AssemblyOperatorBlockItem::new)
                    .transform(customItemModel())
                    .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.getEntry(), 1)
                            .define('P', com.simibubi.create.AllBlocks.PISTON_EXTENSION_POLE.get())
                            .define('C', ALUMINUM_CASING.get())
                            .define('R', AllItems.RADIANT_QUARTZ.get())
                            .pattern("P")
                            .pattern("C")
                            .pattern("R")
                            .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                            .save(prov))
                    .register();

    public static final BlockEntry<CombustionEngineBlock> COMBUSTION_ENGINE =
            REGISTRATE.block("combustion_engine", CombustionEngineBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(axeOrPickaxe())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setCapacity(32.0))
                    .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.getEntry(), 1)
                            .define('P', com.simibubi.create.AllItems.PROPELLER.get())
                            .define('I', MWTags.forgeItemTag("plates/industrial_iron"))
                            .define('B', MWTags.forgeItemTag("ingots/brass"))
                            .define('G', com.simibubi.create.AllBlocks.COGWHEEL.get())
                            .define('C', MWTags.casingTag("industrial"))
                            .define('M', MWTags.mechanismTag("infernal"))
                            .pattern("CMC")
                            .pattern("BGB")
                            .pattern("IPI")
                            .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                            .save(prov))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<WelderBlock> WELDER =
            REGISTRATE.block("welder", WelderBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(axeOrPickaxe())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setCapacity(32.0))
                    .item(AssemblyOperatorBlockItem::new)
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<RollerBlock> ROLLER =
            REGISTRATE.block("roller", RollerBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.horizontalBlockProvider(true))
                    .transform(BlockStressDefaults.setCapacity(32.0))
                    .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.getEntry(), 1)
                            .define('S', MWTags.forgeItemTag("plates/industrial_iron"))
                            .define('C', com.simibubi.create.AllBlocks.BRASS_CASING.get())
                            .define('A', com.simibubi.create.AllBlocks.SHAFT.get())
                            .define('B', MWTags.forgeItemTag("ingots/brass"))
                            .define('M', com.simibubi.create.AllItems.PRECISION_MECHANISM.get())
                            .define('W', MWTags.forgeItemTag("storage_blocks/industrial_iron"))
                            .pattern("SWS")
                            .pattern("ACA")
                            .pattern("BMB")
                            .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                            .save(prov))
                    .item(AssemblyOperatorBlockItem::new)
                    .transform(customItemModel())
                    .register();
    
    public static final BlockEntry<InfuserBlock> INFUSER =
            REGISTRATE.block("infuser", InfuserBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.color(MaterialColor.RAW_IRON))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(axeOrPickaxe())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setCapacity(32.0))
                    .item(AssemblyOperatorBlockItem::new)
                    .transform(customItemModel())
                    .register();
    
    public static final BlockEntry<ReactorCogwheelBlock> reactorCogwheel =
            REGISTRATE.block("reactor_cogwheel", ReactorCogwheelBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setCapacity(32.0))
                    .item()
                    .transform(customItemModel())
                    .register();
    public static final BlockEntry<ReactorChamberBlock> reactorChamber =
            REGISTRATE.block("reactor_chamber", ReactorChamberBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_GRAY))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .item()
                    .transform(customItemModel())
                    .register();
    
    public static final BlockEntry<ReactorInputBlock> pipeJunction =
            REGISTRATE.block("pipe_junction", ReactorInputBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_ORANGE))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .item()
                    .transform(customItemModel())
                    .register();
    public static void register() {}
}
