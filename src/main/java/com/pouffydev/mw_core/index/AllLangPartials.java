package com.pouffydev.mw_core.index;

import com.google.gson.JsonElement;
import com.pouffydev.mw_core.MWCore;
import com.simibubi.create.foundation.data.LangPartial;
import com.simibubi.create.foundation.utility.Lang;

import java.util.function.Supplier;

public enum AllLangPartials implements LangPartial {

    INTERFACE("Create: Milkyway's UI & Messages"),
    TOOLTIPS("Create: Milkyway's Item Descriptions"),
    TINKERS("Create: Milkyway's Tinkers' Construct Compatibility"),
    FLUIDS("Create: Milkyway's Fluids"),

            ;

    private final String displayName;
    private final Supplier<JsonElement> provider;

    private AllLangPartials(String displayName) {
        this.displayName = displayName;
        String fileName = Lang.asId(name());
        this.provider = () -> LangPartial.fromResource(MWCore.ID, fileName);
    }

    private AllLangPartials(String displayName, Supplier<JsonElement> provider) {
        this.displayName = displayName;
        this.provider = provider;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public JsonElement provide() {
        return provider.get();
    }
}
