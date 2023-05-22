package com.pouffydev.mw_core.foundation.ponder;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.index.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderTag;

public class MWPonderIndex {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(MWCore.MODID);

    public static void register() {
        HELPER.forComponents(AllBlocks.TARNISHED_MOTOR)
                .addStoryBoard("tarnished_motor", TarnishedMotorScene::motor, PonderTag.KINETIC_SOURCES);
    }

    public static void registerTags() {
    }
}
