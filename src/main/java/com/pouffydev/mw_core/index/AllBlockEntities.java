package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.content.block.contraptions.converter.ConverterBlockEntity;
import com.pouffydev.mw_core.content.block.contraptions.converter.ConverterRenderer;
import com.pouffydev.mw_core.content.block.generators.combustion.CombustionEngineBlockEntity;
import com.pouffydev.mw_core.content.block.generators.combustion.CombustionEngineInstance;
import com.pouffydev.mw_core.content.block.generators.combustion.CombustionEngineRenderer;
import com.pouffydev.mw_core.content.block.generators.motor.TarnishedMotorBlockEntity;
import com.pouffydev.mw_core.content.block.generators.motor.TarnishedMotorRenderer;
import com.pouffydev.mw_core.content.block.generators.reactor.ReactorCogwheelBlockEntity;
import com.pouffydev.mw_core.content.block.generators.reactor.ReactorCogwheelRenderer;
import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlockEntity;
import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberRenderer;
import com.pouffydev.mw_core.content.block.generators.reactor.input.ReactorInputBlockEntity;
import com.pouffydev.mw_core.content.block.generators.reactor.input.ReactorInputRenderer;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.roller.RollerBlockEntity;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.roller.RollerInstance;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.roller.RollerRenderer;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.welder.WelderBlockEntity;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.welder.WelderInstance;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.welder.WelderRenderer;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.pouffydev.mw_core.MWCore.registrate;

public class AllBlockEntities {

    // See create git for how to register tile entities
    // - https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/AllTileEntities.java


    public static final BlockEntityEntry<TarnishedMotorBlockEntity> MOTOR = registrate
            .blockEntity("tarnished_motor", TarnishedMotorBlockEntity::new)
            .instance(() -> HalfShaftInstance::new, false)
            .validBlocks(MWBlocks.TARNISHED_MOTOR)
            .renderer(() -> TarnishedMotorRenderer::new)
            .register();

    public static final BlockEntityEntry<ConverterBlockEntity> MECHANICAL_CONVERTER = registrate
            .blockEntity("converter", ConverterBlockEntity::new)
            .instance(() -> ShaftInstance::new)
            .renderer(() -> ConverterRenderer::new)
            .validBlocks(MWBlocks.MECHANICAL_CONVERTER)
            .register();

    public static final BlockEntityEntry<CombustionEngineBlockEntity> COMBUSTION_ENGINE = registrate
            .blockEntity("combustion_engine", CombustionEngineBlockEntity::new)
            .instance(() -> CombustionEngineInstance::new)
            .validBlocks(MWBlocks.COMBUSTION_ENGINE)
            .renderer(() -> CombustionEngineRenderer::new)
            .register();

    public static final BlockEntityEntry<WelderBlockEntity> WELDER = registrate
            .blockEntity("welder", WelderBlockEntity::new)
            .instance(() -> WelderInstance::new)
            .validBlocks(MWBlocks.WELDER)
            .renderer(() -> WelderRenderer::new)
            .register();

    public static final BlockEntityEntry<RollerBlockEntity> ROLLER = registrate
            .blockEntity("roller", RollerBlockEntity::new)
            .instance(() -> RollerInstance::new)
            .validBlocks(MWBlocks.ROLLER)
            .renderer(() -> RollerRenderer::new)
            .register();
    
    public static final BlockEntityEntry<ReactorInputBlockEntity> PIPE_JUNCTION = registrate
            .blockEntity("pipe_junction", ReactorInputBlockEntity::new)
            .validBlocks(MWBlocks.pipeJunction)
            .renderer(() -> ReactorInputRenderer::new)
            .register();
    public static final BlockEntityEntry<ReactorCogwheelBlockEntity> REACTOR_COGWHEEL = registrate
            .blockEntity("reactor_cogwheel", ReactorCogwheelBlockEntity::new)
            .validBlocks(MWBlocks.reactorCogwheel)
            .renderer(() -> ReactorCogwheelRenderer::new)
            .register();
    
    public static final BlockEntityEntry<ReactorChamberBlockEntity> REACTOR_CHAMBER = registrate
            .blockEntity("reactor_chamber", ReactorChamberBlockEntity::new)
            .validBlocks(MWBlocks.reactorChamber)
            .renderer(() -> ReactorChamberRenderer::new)
            .register();
    
    
    public static void register() {}
}
