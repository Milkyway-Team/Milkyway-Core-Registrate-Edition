package com.pouffydev.mw_core.foundation.config.server;

import com.simibubi.create.foundation.config.ConfigBase;

public class MWEquipment extends ConfigBase {
    public final ConfigGroup tarnished = group(1, "tarnished", "Tarnished");
    
    @Override
    public String getName() {
        return "equipment";
    }
    
    private static class Comments {
    
    }
}
