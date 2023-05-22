package com.pouffydev.mw_core.index;

import com.jozufozu.flywheel.core.PartialModel;
import com.pouffydev.mw_core.MWCore;

public class AllBlockPartials {
    public static final PartialModel CONVERTER_HEAD = block("converter/head");

    public AllBlockPartials() {
    }

    private static PartialModel block(String path) {
        return new PartialModel(MWCore.asResource("block/" + path));
    }

    public static void register() {
    }
}
