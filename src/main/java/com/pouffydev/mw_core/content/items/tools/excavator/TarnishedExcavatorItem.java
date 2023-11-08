package com.pouffydev.mw_core.content.items.tools.excavator;

import com.pouffydev.mw_core.index.AllItems;
import com.pouffydev.mw_core.index.MWTags;
import com.pouffydev.mw_core.index.AllToolTiers;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.function.Consumer;
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TarnishedExcavatorItem extends PickaxeItem {
    public TarnishedExcavatorItem(Properties properties) {
        super(AllToolTiers.TARNISHED, 0, 1.5f, properties);
    }
    
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }
    public boolean isExcavator(ItemStack stack) {
        return stack.getItem() instanceof TarnishedExcavatorItem;
    }
    BlockPos MineRandomBlock(Level world, Entity player, BlockPos pos) {
        Random rand = new Random();
        int x = rand.nextInt(3)-1;
        int y = rand.nextInt(3)-1;
        int z = rand.nextInt(3)-1;
        BlockPos newpos = new BlockPos(pos.getX() + x,pos.getY() + y,pos.getZ() + z);

        if (world.getBlockState(newpos).getDestroySpeed(world, newpos) > -1) {
            if (!world.getBlockState(newpos).getBlock().defaultBlockState().is(MWTags.AllBlockTags.EXCAVATOR_BLACKLIST.tag) || world.getBlockState(newpos).getBlock().defaultBlockState().is(world.getBlockState(pos).getBlock().defaultBlockState().getBlock())) {
                world.destroyBlock(newpos, true, player, 5);
            }
            findAndDamageExcavator((Player) player);
        }
        return newpos;
    }
    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState block, BlockPos pos, LivingEntity entity) {
        CompoundTag nbt = stack.getOrCreateTag();
        for (int i = 0; i < 20; i++) {
            if (nbt.getBoolean("active")) {
                MineRandomBlock(world, entity, MineRandomBlock(world, entity, MineRandomBlock(world, entity, MineRandomBlock(world, entity, pos))));
            }
        }
        return super.mineBlock(stack, world, block, pos, entity);
    }
    private static void findAndDamageExcavator(Player player) {
        if (player == null)
            return;
        if (player.level.isClientSide)
            return;
        InteractionHand hand = InteractionHand.MAIN_HAND;
        ItemStack excavator = player.getMainHandItem();
        if (!AllItems.EXCAVATOR.isIn(excavator)) {
            excavator = player.getOffhandItem();
            hand = InteractionHand.OFF_HAND;
        }
        if (!AllItems.EXCAVATOR.isIn(excavator))
            return;
        final InteractionHand h = hand;
        excavator.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(h));
    }
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundTag tag = itemstack.getOrCreateTag();
        if (tag.getBoolean("active")) {
            tag.putBoolean("active", false);
            return InteractionResultHolder.success(itemstack);
        }
        if (!tag.getBoolean("active")) {
            tag.putBoolean("active", true);
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.success(itemstack);
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new TarnishedExcavatorRenderer()));
    }
}
