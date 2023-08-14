package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WelderBlock extends KineticBlock implements IBE<WelderBlockEntity>, ICogWheel {
    public WelderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return null;
    }

    @Override
    public Class getBlockEntityClass() {
        return WelderBlockEntity.class;
    }

    @Override
    public BlockEntityType getBlockEntityType() {
        return null;
    }
}
