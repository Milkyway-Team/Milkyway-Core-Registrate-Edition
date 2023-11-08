package com.pouffydev.mw_core.content.items.tools.deforester;

import com.pouffydev.mw_core.index.AllItems;
import com.pouffydev.mw_core.index.AllToolTiers;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TarnishedDeforesterItem extends AxeItem {
    private static boolean deforesting = false; // required as to not run into "recursions" over forge events on tree cutting
    public TarnishedDeforesterItem(Properties builder) {
        super(AllToolTiers.TARNISHED, 5.0F, -3.1F, builder);
    }

    // Moved away from Item#onBlockDestroyed as it does not get called in Creative
    public static void destroyTree(Level iWorld, BlockState state, BlockPos pos,
                                   Player player) {

        if (deforesting ||!(state.is(BlockTags.LOGS) || AllTags.AllBlockTags.SLIMY_LOGS.matches(state)) || player.isCrouching() || !(iWorld instanceof  Level))
            return;
        Level worldIn = (Level) iWorld;
        Vec3 vec = player.getLookAngle();

        deforesting = true;
        TreeCutter.findTree(worldIn, pos).destroyBlocks(worldIn, player, (dropPos, item) -> dropItemFromCutTree(worldIn, pos, vec, dropPos, item));
        deforesting = false;
    }
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }
    @SubscribeEvent
    public static void onBlockDestroyed(BlockEvent.BreakEvent event) {
        ItemStack heldItemMainhand = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
        if (!AllItems.DEFORESTER.isIn(heldItemMainhand))
            return;
        destroyTree((Level) event.getWorld(), event.getState(), event.getPos(), event.getPlayer());
    }

    public static void dropItemFromCutTree(Level world, BlockPos breakingPos, Vec3 fallDirection, BlockPos pos,
                                           ItemStack stack) {
        float distance = (float) Math.sqrt(pos.distSqr(breakingPos));
        Vec3 dropPos = VecHelper.getCenterOf(pos);
        ItemEntity entity = new ItemEntity(world, dropPos.x, dropPos.y, dropPos.z, stack);
        entity.setDeltaMovement(fallDirection.scale(distance / 20f));
        world.addFreshEntity(entity);
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new TarnishedDeforesterRenderer()));
    }
}
