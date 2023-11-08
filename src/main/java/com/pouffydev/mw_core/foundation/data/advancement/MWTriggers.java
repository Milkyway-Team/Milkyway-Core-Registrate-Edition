package com.pouffydev.mw_core.foundation.data.advancement;

import com.mojang.logging.LogUtils;
import com.pouffydev.mw_core.MWCore;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MWTriggers {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<CriterionTrigger<?>> TRIGGERS = new ArrayList<>();
    public static SimpleTrigger addSimple(String id) {
        return add(new SimpleTrigger(MWCore.asResource(id)));
    }
    
    private static <T extends CriterionTrigger<?>> T add(T instance) {
        TRIGGERS.add(instance);
        return instance;
    }
    
    public static void register() {
        TRIGGERS.forEach(CriteriaTriggers::register);
        LOGGER.debug("Register {} builtin triggers", TRIGGERS.size());
    }
}
