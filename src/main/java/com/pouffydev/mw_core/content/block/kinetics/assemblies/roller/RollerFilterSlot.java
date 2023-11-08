package com.pouffydev.mw_core.content.block.kinetics.assemblies.roller;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class RollerFilterSlot extends ValueBoxTransform {
    
    @Override
    public Vec3 getLocalOffset(BlockState state) {
        int offset = state.getValue(RollerBlock.FLIPPED) ? -3 : 3;
        Vec3 x = VecHelper.voxelSpace(8, 12.5f, 8 + offset);
        Vec3 z = VecHelper.voxelSpace(8 + offset, 12.5f, 8);
        return state.getValue(RollerBlock.AXIS_ALONG_FIRST_COORDINATE) ? z : x;
    }
    
    @Override
    public void rotate(BlockState state, PoseStack ms) {
        int yRot = (state.getValue(RollerBlock.AXIS_ALONG_FIRST_COORDINATE) ? 90 : 0)
                + (state.getValue(RollerBlock.FLIPPED) ? 0 : 180);
        TransformStack.cast(ms)
                .rotateY(yRot)
                .rotateX(90);
    }
}
