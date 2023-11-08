package com.pouffydev.mw_core.foundation.config.server;

import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.infrastructure.config.CKinetics;

public class MWKinetics extends ConfigBase {
    public final ConfigGroup generators = group(1, "generators", "Generators");
    public final ConfigInt generatingEngineSpeed = i(24, 4, "generatingEngineSpeed", Comments.generatingEngineSpeed);
    @Override
    public String getName() {
        return "kinetics";
    }

    private static class Comments {
        static String generatingEngineSpeed = "The speed of the generating engine in ticks per operation";
    }

}
