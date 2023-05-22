package com.pouffydev.mw_core.index;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.content.block.contraptions.converter.ConverterRenderer;
import com.pouffydev.mw_core.content.block.contraptions.converter.ConverterTileEntity;
import com.pouffydev.mw_core.content.block.tarnished.motor.TarnishedMotorRenderer;
import com.pouffydev.mw_core.content.block.tarnished.motor.TarnishedMotorTileEntity;
import com.simibubi.create.content.contraptions.base.HalfShaftInstance;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllTileEntities {
    private static final CreateRegistrate REGISTRATE = MWCore.registrate();

    // See create git for how to register tile entities
    // - https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/AllTileEntities.java


    public static final BlockEntityEntry<TarnishedMotorTileEntity> MOTOR = REGISTRATE
            .tileEntity("motor", TarnishedMotorTileEntity::new)
            .instance(() -> HalfShaftInstance::new, false)
            .validBlocks(AllBlocks.TARNISHED_MOTOR)
            .renderer(() -> TarnishedMotorRenderer::new)
            .register();

    public static final BlockEntityEntry<ConverterTileEntity> MECHANICAL_CONVERTER = REGISTRATE
            .tileEntity("converter", ConverterTileEntity::new)
            .instance(() -> ShaftInstance::new)
            .renderer(() -> ConverterRenderer::new)
            .validBlocks(AllBlocks.MECHANICAL_CONVERTER)
            .register();
    public static void register() {}
}
