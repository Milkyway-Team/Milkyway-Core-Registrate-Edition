package com.pouffydev.mw_core.content.items.tools;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pouffydev.mw_core.content.items.tools.blade.TarnishedBladeItem;
import com.pouffydev.mw_core.index.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonPacket;
import com.simibubi.create.content.equipment.zapper.ShootableGadgetRenderHandler;
import com.simibubi.create.foundation.particle.AirParticleData;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class TarnishedBladeRenderHandler extends ShootableGadgetRenderHandler {
    @Override
    protected void playSound(InteractionHand hand, Vec3 position) {
        TarnishedBladeItem.playActivateSound(Minecraft.getInstance().level, position, 1);
    }
    @Override
    protected boolean appliesTo(ItemStack stack) {
        return AllItems.BLADE.get().isBlade(stack);
    }
    
    @Override
    protected void transformTool(PoseStack ms, float flip, float equipProgress, float recoil, float pt) {
    
    }
    
    @Override
    protected void transformHand(PoseStack ms, float flip, float equipProgress, float recoil, float pt) {
    
    }
    
    
}
