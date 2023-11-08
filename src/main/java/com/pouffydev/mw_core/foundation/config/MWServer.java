package com.pouffydev.mw_core.foundation.config;

import com.pouffydev.mw_core.foundation.config.server.MWEquipment;
import com.pouffydev.mw_core.foundation.config.server.MWKinetics;
import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.infrastructure.config.CKinetics;
import com.simibubi.create.infrastructure.config.CServer;

public class MWServer extends ConfigBase {

    public final MWKinetics kinetics = nested(0, MWKinetics::new, Comments.kinetics);
    public final MWEquipment equipment = nested(1, MWEquipment::new, Comments.equipment);
    @Override
    public String getName() {
        return "server";
    }
    private static class Comments {
        static String kinetics = "Parameters and abilities of Create: Milkyway's kinetic machinery";
        static String equipment = "Equipment and gadgets added by Create: Milkyway";
    }
}
