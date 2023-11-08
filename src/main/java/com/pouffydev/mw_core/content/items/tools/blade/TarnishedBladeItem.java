package com.pouffydev.mw_core.content.items.tools.blade;

import com.pouffydev.mw_core.foundation.data.advancement.MWAdvancements;
import com.pouffydev.mw_core.index.AllToolTiers;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TarnishedBladeItem extends SwordItem {

    public TarnishedBladeItem(Properties builder) {
        super(AllToolTiers.TARNISHED, 3, -2.4F, builder);
    }
    
    public boolean isActivated(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getBoolean("active");
    }
    public boolean isBlade(ItemStack stack) {
            return stack.getItem() instanceof TarnishedBladeItem;
    }
    public static void playActivateSound(Level world, Vec3 location, float pitch) {
        AllSoundEvents.WORLDSHAPER_PLACE.playAt(world, location, 1, pitch, true);
    }
    
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity1, LivingEntity entity2) {
        stack.hurtAndBreak(1, entity2, (item) -> {
            item.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.getBoolean("active")) {
            return super.hurtEnemy(stack, entity1, entity2);
        }
        return false;
    }
    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.getBoolean("active")) {
                return super.canPerformAction(stack, toolAction);
            }
        return false;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        ItemStack stack = new ItemStack(this);
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.getBoolean("active")) {
            return super.canAttackBlock(state, level, pos, player);
        }
        if (!nbt.getBoolean("active")) {
            return true;
        }
        return super.canAttackBlock(state, level, pos, player);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundTag tag = itemstack.getOrCreateTag();
        if (tag.getBoolean("active")) {
            tag.putBoolean("active", false);
            return InteractionResultHolder.success(itemstack);
        }
        if (!tag.getBoolean("active")) {
            //if (player.getY() > 200 && !MWAdvancements.HIGH_GROUND.isAlreadyAwardedTo(player)){
            //    MWAdvancements.HIGH_GROUND.awardTo(player);
            //}
            tag.putBoolean("active", true);
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new TarnishedBladeRenderer()));
    }
}
