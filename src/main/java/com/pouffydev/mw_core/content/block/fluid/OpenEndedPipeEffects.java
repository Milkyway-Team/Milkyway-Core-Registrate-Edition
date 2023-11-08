package com.pouffydev.mw_core.content.block.fluid;

import com.simibubi.create.content.fluids.OpenEndedPipe;

public class OpenEndedPipeEffects {
    public static void register() {
        OpenEndedPipe.registerEffectHandler(new ChromaticEffectHandler());
    }
}
