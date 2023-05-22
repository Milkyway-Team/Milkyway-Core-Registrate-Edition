package com.pouffydev.mw_core;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;

public class MWSpriteShifts {

    public static final CTSpriteShiftEntry RADIANT_CASING = omni("radiant_casing");
    public static final CTSpriteShiftEntry TARNISHED_CASING = omni("tarnished_casing");
    public static final CTSpriteShiftEntry ALUMINUM_CASING = omni("aluminum_casing");

    public static final CTSpriteShiftEntry
            TARNISHED_TANK = getCT(AllCTTypes.RECTANGLE, "fluid_tank"), TARNISHED_TANK_TOP = getCT(AllCTTypes.RECTANGLE, "fluid_tank_top"),
            TARNISHED_TANK_INNER = getCT(AllCTTypes.RECTANGLE, "fluid_tank_inner");
    public static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, MWCore.asResource("block/" + blockTextureName), MWCore.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
}
