package com.pouffydev.mw_core.index;

import com.jozufozu.flywheel.core.PartialModel;
import com.pouffydev.mw_core.MWCore;

public class AllBlockPartials {
    public static final PartialModel CONVERTER_HEAD = block("converter/head"),
    WELDER_COG = block("welder/cog"), WELDER_BASE = block("welder/base"),
    WELDER_LOWER_BODY = block("welder/lower_body"), WELDER_UPPER_BODY = block("welder/upper_body"),
    WELDER_HEAD = block("welder/head");

    public AllBlockPartials() {
    }

    private static PartialModel block(String path) {
        return new PartialModel(MWCore.asResource("block/" + path));
    }

    public static void register() {
    }
}
