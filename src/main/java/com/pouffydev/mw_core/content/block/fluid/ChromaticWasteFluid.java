package com.pouffydev.mw_core.content.block.fluid;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ChromaticWasteFluid extends VirtualFluid {
    public ChromaticWasteFluid(Properties properties) {
        super(properties);
    }
    
    public void smoke(ServerLevel level, Vec3 pos, int fluidAmount) {
        Random random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        Color colorCode = new Color(red, green, blue);
        while(fluidAmount > 0) {
            int smokeAmount = fluidAmount;
            fluidAmount -= smokeAmount;
            //add smoke particles based on smokeAmount
            for (int i = 0; i < smokeAmount; i++) {
                //add smoke particles
                ParticleOptions colourfulSmoke = new DustParticleOptions(colorCode.asVectorF(), 1.0f);
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x, pos.y, pos.z, 1.0D, 1.1D, 1.0D);
            }
        }
    }
}
