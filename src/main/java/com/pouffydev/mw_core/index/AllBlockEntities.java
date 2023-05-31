package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.content.block.contraptions.converter.ConverterRenderer;
import com.pouffydev.mw_core.content.block.contraptions.converter.ConverterBlockEntity;
import com.pouffydev.mw_core.content.block.generators.combustion.CombustionEngineBlockEntity;
import com.pouffydev.mw_core.content.block.generators.motor.TarnishedMotorRenderer;
import com.pouffydev.mw_core.content.block.generators.motor.TarnishedMotorBlockEntity;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.crafter.ShaftlessCogwheelInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.pouffydev.mw_core.MWCore.registrate;

public class AllBlockEntities {

    // See create git for how to register tile entities
    // - https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/AllTileEntities.java


    public static final BlockEntityEntry<TarnishedMotorBlockEntity> MOTOR = registrate
            .blockEntity("tarnished_motor", TarnishedMotorBlockEntity::new)
            .instance(() -> HalfShaftInstance::new, false)
            .validBlocks(AllBlocks.TARNISHED_MOTOR)
            .renderer(() -> TarnishedMotorRenderer::new)
            .register();

    public static final BlockEntityEntry<ConverterBlockEntity> MECHANICAL_CONVERTER = registrate
            .blockEntity("converter", ConverterBlockEntity::new)
            .instance(() -> ShaftInstance::new)
            .renderer(() -> ConverterRenderer::new)
            .validBlocks(AllBlocks.MECHANICAL_CONVERTER)
            .register();

    public static final BlockEntityEntry<CombustionEngineBlockEntity> COMBUSTION_ENGINE = registrate
            .blockEntity("combustion_engine", CombustionEngineBlockEntity::new)
            .instance(() -> ShaftlessCogwheelInstance::new)
            .validBlocks(AllBlocks.COMBUSTION_ENGINE)
            .register();
    public static void register() {}
}
