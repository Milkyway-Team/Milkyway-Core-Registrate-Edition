package com.pouffydev.mw_core.content.block.generators.combustion;

import com.simibubi.create.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CombustionSources {

    public static int campfire = 8;
    public static int soul_campfire = -8;
    public static int blaze_burner_smouldering = 8;
    public static int blaze_burner_kindled = 16;
    public static int blaze_burner_seething = 48;
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        execute(event, event.getWorld(), event.getPos().getX(), event.getPos().getY() - 1, event.getPos().getZ());
    }
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        execute(event, event.getWorld(), event.getPos().getX(), event.getPos().getY() - 1, event.getPos().getZ());
    }


    public static void execute(LevelAccessor world, double x, double y, double z) {
        execute(null, world, x, y, z);
    }

    private static double execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
        if ((world.getBlockState(new BlockPos(x, y - 1, z)))
                .getBlock() == (Blocks.CAMPFIRE.getStateDefinition().getProperty("lit") instanceof BooleanProperty _withbp1 ? Blocks.CAMPFIRE.defaultBlockState().setValue(_withbp1, (true)) : Blocks.CAMPFIRE.defaultBlockState()).getBlock()) {
            return campfire;
        }
        else if ((world.getBlockState(new BlockPos(x, y - 1, z))).getBlock() == (new Object() {
            public BlockState with(BlockState _bs, String _property, String _newValue) {
                Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
                return _prop instanceof EnumProperty _ep && _ep.getValue(_newValue).isPresent() ? _bs.setValue(_ep, (Enum) _ep.getValue(_newValue).get()) : _bs;
            }
        }.with(AllBlocks.BLAZE_BURNER.getDefaultState(), "blaze", "smouldering")).getBlock()) {
            return blaze_burner_smouldering;
        }
        //else if ((world.getBlockState(new BlockPos(x, y - 1, z))).getBlock() == (new Object() {
        //    public BlockState with(BlockState _bs, String _property, String _newValue) {
        //        Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
        //        return _prop instanceof EnumProperty _ep && _ep.getValue(_newValue).isPresent() ? _bs.setValue(_ep, (Enum) _ep.getValue(_newValue).get()) : _bs;
        //    }
        //}.with(AllBlocks.BLAZE_BURNER.getDefaultState(), "blaze", "kindled")).getBlock()) {
        //    return blaze_burner_kindled;
        //}
        //else if ((world.getBlockState(new BlockPos(x, y - 1, z))).getBlock() == (new Object() {
        //    public BlockState with(BlockState _bs, String _property, String _newValue) {
        //        Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
        //        return _prop instanceof EnumProperty _ep && _ep.getValue(_newValue).isPresent() ? _bs.setValue(_ep, (Enum) _ep.getValue(_newValue).get()) : _bs;
        //    }
        //}.with(AllBlocks.BLAZE_BURNER.getDefaultState(), "blaze", "seething")).getBlock()) {
        //    return blaze_burner_seething;
        //}
        else return 0;
    }

    public static int campfireCheck(LevelAccessor world, double x, double y, double z) {
        if ((world.getBlockState(new BlockPos(x, y - 1, z)))
                .getBlock() == (Blocks.CAMPFIRE.getStateDefinition().getProperty("lit") instanceof BooleanProperty _withbp1 ? Blocks.CAMPFIRE.defaultBlockState().setValue(_withbp1, (true)) : Blocks.CAMPFIRE.defaultBlockState()).getBlock()) {
            return campfire;
        }
        return 0;
    }
    //public static int smoulderingCheck(LevelAccessor world, double x, double y, double z) {
    //    if ((world.getBlockState(new BlockPos(x, y - 1, z))).getBlock() == (new Object() {
    //        public BlockState with(BlockState _bs, String _property, String _newValue) {
    //            Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
    //            return _prop instanceof EnumProperty _ep && _ep.getValue(_newValue).isPresent() ? _bs.setValue(_ep, (Enum) _ep.getValue(_newValue).get()) : _bs;
    //        }
    //    }.with(AllBlocks.BLAZE_BURNER.getDefaultState(), "blaze", "smouldering")).getBlock()) {
    //        return blaze_burner_smouldering;
    //    }
    //    return 0;
    //}
    //public static int kindledCheck(LevelAccessor world, double x, double y, double z) {
    //    if ((world.getBlockState(new BlockPos(x, y - 1, z))).getBlock() == (new Object() {
    //        public BlockState with(BlockState _bs, String _property, String _newValue) {
    //            Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
    //            return _prop instanceof EnumProperty _ep && _ep.getValue(_newValue).isPresent() ? _bs.setValue(_ep, (Enum) _ep.getValue(_newValue).get()) : _bs;
    //        }
    //    }.with(AllBlocks.BLAZE_BURNER.getDefaultState(), "blaze", "kindled")).getBlock()) {
    //        return blaze_burner_kindled;
    //    }
    //    return 0;
    //}
    //public static int seethingCheck(LevelAccessor world, double x, double y, double z) {
    //    if ((world.getBlockState(new BlockPos(x, y - 1, z))).getBlock() == (new Object() {
    //        public BlockState with(BlockState _bs, String _property, String _newValue) {
    //            Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
    //            return _prop instanceof EnumProperty _ep && _ep.getValue(_newValue).isPresent() ? _bs.setValue(_ep, (Enum) _ep.getValue(_newValue).get()) : _bs;
    //        }
    //    }.with(AllBlocks.BLAZE_BURNER.getDefaultState(), "blaze", "seething")).getBlock()) {
    //        return blaze_burner_seething;
    //    }
    //    return 0;
    //}
}
