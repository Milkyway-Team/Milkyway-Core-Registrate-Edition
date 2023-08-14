package com.pouffydev.mw_core.content.block.kinetics.assemblies.roller;

import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RollerBlock extends DirectionalAxisKineticBlock implements IBE<RollerBlockEntity> {
    public RollerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<RollerBlockEntity> getBlockEntityClass() {
        return null;
    }

    @Override
    public BlockEntityType<? extends RollerBlockEntity> getBlockEntityType() {
        return null;
    }
}
