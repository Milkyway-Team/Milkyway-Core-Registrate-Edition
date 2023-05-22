package com.pouffydev.mw_core.index;

import com.google.gson.JsonElement;
import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.foundation.data.MWPonderLocalization;
import com.simibubi.create.foundation.data.LangPartial;
import com.simibubi.create.foundation.ponder.PonderLocalization;
import com.simibubi.create.foundation.utility.Lang;

import java.util.function.Supplier;

public enum AllLangPartials implements LangPartial {

    INTERFACE("UI & Messages"),
    TOOLTIPS("Item Descriptions"),
    PONDER("Ponder Content", MWPonderLocalization::provideLangEntries),

            ;

    private final String displayName;
    private final Supplier<JsonElement> provider;

    private AllLangPartials(String displayName) {
        this.displayName = displayName;
        String fileName = Lang.asId(name());
        this.provider = () -> LangPartial.fromResource(MWCore.MODID, fileName);
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
