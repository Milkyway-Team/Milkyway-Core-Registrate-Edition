package com.pouffydev.mw_core.foundation.client;

import com.pouffydev.mw_core.MWClient;
import com.pouffydev.mw_core.index.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class MWClientEvents {
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (!isGameActive())
            return;
    }
    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }
    @SubscribeEvent
    public static void getFogDensity(EntityViewRenderEvent.RenderFogEvent event) {
        Camera camera = event.getCamera();
        Level level = Minecraft.getInstance().level;
        BlockPos blockPos = camera.getBlockPosition();
        FluidState fluidState = level.getFluidState(blockPos);
        if (camera.getPosition().y >= blockPos.getY() + fluidState.getHeight(level, blockPos))
            return;
        
        Fluid fluid = fluidState.getType();
        Entity entity = camera.getEntity();
        
        if (entity.isSpectator())
            return;
        
        ItemStack divingHelmet = DivingHelmetItem.getWornItem(entity);
        if (!divingHelmet.isEmpty()) {
            if (FluidHelper.isWater(fluid)) {
                event.scaleFarPlaneDistance(6.25f);
                event.setCanceled(true);
                return;
            } else if (FluidHelper.isLava(fluid) && AllItems.NETHERITE_DIVING_HELMET.isIn(divingHelmet)) {
                event.setNearPlaneDistance(-4.0f);
                event.setFarPlaneDistance(20.0f);
                event.setCanceled(true);
                return;
            }
        }
    }
    private static int colorInPhase(int phase, int progress) {
        phase = phase % 6;
        if (phase <= 1)
            return 0;
        if (phase == 2)
            return progress;
        if (phase <= 4)
            return 255;
        else
            return 255 - progress;
    }
    @SubscribeEvent
    public static void getFogColor(EntityViewRenderEvent.FogColors event) {
        Camera info = event.getCamera();
        Level level = Minecraft.getInstance().level;
        BlockPos blockPos = info.getBlockPosition();
        FluidState fluidState = level.getFluidState(blockPos);
        int timeStep = (int) (level.getDayTime() % 24000);
        int localTimeStep = Math.abs(timeStep) % 1536;
        int timeStepInPhase = localTimeStep % 256;
        int phaseBlue = localTimeStep / 256;
        int red = colorInPhase(phaseBlue + 4, timeStepInPhase);
        int green = colorInPhase(phaseBlue + 2, timeStepInPhase);
        int blue = colorInPhase(phaseBlue, timeStepInPhase);
        if (info.getPosition().y > blockPos.getY() + fluidState.getHeight(level, blockPos))
            return;
        
        Fluid fluid = fluidState.getType();
        
    }
}
