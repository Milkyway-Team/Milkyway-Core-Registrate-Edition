package com.pouffydev.mw_core.content.block.generators.combustion;

import com.simibubi.create.Create;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.utility.Lang;

public enum CombustionHeatConditions {
    NONE(0xffffff), SMOULDERING(0x7A5019), KINDLED(0xE88300), SEETHING(0x5C93E8),

    ;

    private int color;

    private CombustionHeatConditions(int color) {
        this.color = color;
    }

    public boolean testBlazeBurner(BlazeBurnerBlock.HeatLevel level) {
        if (this == SEETHING)
            return level == BlazeBurnerBlock.HeatLevel.SEETHING;
        if (this == KINDLED)
            return level == BlazeBurnerBlock.HeatLevel.KINDLED;
        if (this == SMOULDERING)
            return level == BlazeBurnerBlock.HeatLevel.SMOULDERING;
        return true;
    }

    public BlazeBurnerBlock.HeatLevel visualizeAsBlazeBurner() {
        if (this == SEETHING)
            return BlazeBurnerBlock.HeatLevel.SEETHING;
        if (this == KINDLED)
            return BlazeBurnerBlock.HeatLevel.KINDLED;
        if (this == SMOULDERING)
            return BlazeBurnerBlock.HeatLevel.SMOULDERING;
        return BlazeBurnerBlock.HeatLevel.NONE;
    }

    public String serialize() {
        return Lang.asId(name());
    }

    public String getTranslationKey() {
        return "combustion_engine.heat_requirement." + serialize();
    }

    public static CombustionHeatConditions deserialize(String name) {
        for (CombustionHeatConditions heatCondition : values())
            if (heatCondition.serialize()
                    .equals(name))
                return heatCondition;
        Create.LOGGER.warn("Tried to deserialize invalid heat condition: \"" + name + "\"");
        return NONE;
    }

    public int getColor() {
        return color;
    }
}
