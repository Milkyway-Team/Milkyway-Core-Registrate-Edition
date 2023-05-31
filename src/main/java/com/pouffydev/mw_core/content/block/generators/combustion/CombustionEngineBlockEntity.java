package com.pouffydev.mw_core.content.block.generators.combustion;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import static com.pouffydev.mw_core.content.block.generators.combustion.CombustionSources.campfire;

public class CombustionEngineBlockEntity extends GeneratingKineticBlockEntity {
    BlockPos pos = getBlockPos(); // get the position of the block below your block entity
    public CombustionEngineBlockEntity(BlockEntityType<CombustionEngineBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public float getGeneratedSpeed() {
        ServerLevel world = (ServerLevel) getLevel(); // get the world
        // calculate and return the generated speed of the machine here
        //CombustionSources.campfireCheck(world, pos.getX(), pos.getY(), pos.getZ());
        //CombustionSources.smoulderingCheck(world, pos.getX(), pos.getY(), pos.getZ());
        //CombustionSources.kindledCheck(world, pos.getX(), pos.getY(), pos.getZ());
        //CombustionSources.seethingCheck(world, pos.getX(), pos.getY(), pos.getZ());
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        if ((world.getBlockState(new BlockPos(x, y - 1, z)))
                .getBlock() == (Blocks.CAMPFIRE.getStateDefinition().getProperty("lit") instanceof BooleanProperty _withbp1 ? Blocks.CAMPFIRE.defaultBlockState().setValue(_withbp1, (true)) : Blocks.CAMPFIRE.defaultBlockState()).getBlock()) {
            return campfire;
        }
        else if ((world.getBlockState(new BlockPos(x, y - 1, z))).getBlock() == Blocks.AIR) {
            return 0;
        }
        return 0;
    }

    @Override
    public boolean isSource() {
        // return true if the machine is a source of rotation, false otherwise
        return true;
    }
}
