package com.pouffydev.mw_core.mixin;

import com.pouffydev.mw_core.content.block.fluid.ChromaticWasteFluid;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.drain.ItemDrainBlock;
import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDrainBlock.class)
public abstract class ItemDrainBlockMixin extends Block implements IBE<ItemDrainBlockEntity>, IWrenchable {
    public ItemDrainBlockMixin(Properties pProperties) {
        super(pProperties);
    }
    
    // Support Smoke with Block Break
    @Inject(method = "onRemove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;removeBlockEntity(Lnet/minecraft/core/BlockPos;)V"))
    private void injected(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci) {
        if (!(level instanceof ServerLevel serverLevel))
            return;
        withBlockEntityDo(level, pos, te -> {
            var fluidStack = ((ItemDrainBlockEntityAccessor) te).getInternalTank().getPrimaryHandler().getFluid();
            if (fluidStack.getFluid() instanceof ChromaticWasteFluid chromaticWasteFluid) {
                chromaticWasteFluid.smoke(serverLevel, VecHelper.getCenterOf(pos), fluidStack.getAmount());
            }
        });
    }
    
}
