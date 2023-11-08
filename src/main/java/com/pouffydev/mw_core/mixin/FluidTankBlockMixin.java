package com.pouffydev.mw_core.mixin;

import com.pouffydev.mw_core.content.block.fluid.ChromaticWasteFluid;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.tank.CreativeFluidTankBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidTankBlock.class)
public abstract class FluidTankBlockMixin extends Block implements IBE<FluidTankBlockEntity>, IWrenchable {
    public FluidTankBlockMixin(Properties pProperties) {
        super(pProperties);
    }
    
    // Support Smoke with Block Break
    @Inject(method = "onRemove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;removeBlockEntity(Lnet/minecraft/core/BlockPos;)V"), cancellable = true)
    private void injected(BlockState state, Level level, BlockPos pos, BlockState newState, boolean var4, CallbackInfo ci) {
        if(!(level instanceof ServerLevel serverLevel))
            return;
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof FluidTankBlockEntity tankBE) || be instanceof CreativeFluidTankBlockEntity)
            return;
        var controllerBE = tankBE.getControllerBE();
        var fluidStack = controllerBE.getFluid(0);
        var fluidStackBackup = fluidStack.copy();
        var maxSize = controllerBE.getTotalTankSize();
        if (fluidStack.getFluid() instanceof ChromaticWasteFluid chromaticWasteFluid) {
            level.removeBlockEntity(pos);
            ConnectivityHandler.splitMulti(tankBE);
            if (maxSize == 1) {
                chromaticWasteFluid.smoke(serverLevel, VecHelper.getCenterOf(pos), fluidStackBackup.getAmount());
            } else {
                var total = maxSize * (FluidTankBlockEntity.getCapacityMultiplier() - 1);
                var leftover = fluidStackBackup.getAmount() - total;
                if(leftover > 0) {
                    chromaticWasteFluid.smoke(serverLevel, VecHelper.getCenterOf(pos), leftover);
                }
            }
            ci.cancel();
        }
    }
}
