package com.pouffydev.mw_core.index;

import com.jozufozu.flywheel.core.PartialModel;
import com.pouffydev.mw_core.MWCore;

public class AllBlockPartials {
    public static final PartialModel CONVERTER_HEAD = block("converter/head"),
            REACTOR_COG = block("reactor_cogwheel/cog"), PIPE_JUNCTION = block("pipe_junction/junction"), JUNCTION_EXTENSION = block("reactor_chamber/junction_extension"),
            CHAMBER_EXTENSION = block("reactor_chamber/chamber_extensions"), REACTOR_ROD = block("reactor_chamber/reactor_rod"),
            WELDER_COG = block("welder/cog"), WELDER_HEAD = block("welder/head"),
            ROLLER_WHEEL = block("roller/wheel"), ROLLER_WHEEL_ROTATED = block("roller/wheel_rotated"),
            COMBUSTION_COG = block("combustion_engine/cog");


    public AllBlockPartials() {
    }

    private static PartialModel block(String path) {
        return new PartialModel(MWCore.asResource("block/" + path));
    }

    public static void register() {
    }
}
