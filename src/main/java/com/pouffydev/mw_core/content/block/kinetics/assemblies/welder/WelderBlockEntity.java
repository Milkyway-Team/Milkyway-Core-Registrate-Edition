package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.pouffydev.mw_core.index.AllRecipeTypes;
import com.pouffydev.mw_core.index.MWTags;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;

public class WelderBlockEntity extends BasinOperatingBlockEntity implements WeldingBehaviour.WeldingBehaviourSpecifics {
    public WeldingBehaviour weldingBehaviour;
    private static final Object weldingRecipesKey = new Object();
    private int sheetsCreated;
    public int runningTicks;
    public int processingTicks;
    float chasedPointProgress;
    LerpedFloat upperArmAngle;
    LerpedFloat headAngle;
    WelderTarget previousTarget;
    protected WelderTarget target;
    public boolean running;
    public WelderBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        previousTarget = WelderTarget.NO_TARGET;
        upperArmAngle = LerpedFloat.angular();
        upperArmAngle.startWithValue(previousTarget.upperArmAngle);
        headAngle = LerpedFloat.angular();
        headAngle.startWithValue(previousTarget.headAngle);
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        super.tickAudio();

        // SoundEvents.BLOCK_STONE_BREAK
        boolean slow = Math.abs(getSpeed()) < 65;
        if (slow && AnimationTickHolder.getTicks() % 2 == 0)
            return;
        if (runningTicks == 20)
            AllSoundEvents.STEAM.playAt(level, worldPosition, .75f, 1, true);
    }
    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(3);
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        weldingBehaviour = new WeldingBehaviour(this);
        behaviours.add(weldingBehaviour);
    }
    public WeldingBehaviour getWeldingBehaviour() {
        return weldingBehaviour;
    }
    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putBoolean("Running", running);
        compound.putInt("Ticks", runningTicks);
    }
    int getProcessingDuration(WeldingRecipe recipe) {
        int duration = recipe.getProcessingDuration();

        if (duration == 0) {
            return 20 * 256;
        }
        return duration;
    }
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        running = compound.getBoolean("Running");
        runningTicks = compound.getInt("Ticks");
        sheetsCreated = compound.getInt("SheetsCreated");
    }
    public void onItemPressed(ItemStack result) {
        if (MWTags.AllItemTags.STURDY_SHEETS.matches(result))
            sheetsCreated += result.getCount();
        if (sheetsCreated >= 1000) {
            sheetsCreated = 0;
        }
    }
    private static final RecipeWrapper weldingInv = new RecipeWrapper(new ItemStackHandler(1));
    public Optional<WeldingRecipe> getRecipe(ItemStack item) {
        Optional<WeldingRecipe> assemblyRecipe =
                SequencedAssemblyRecipe.getRecipe(level, item, AllRecipeTypes.WELDING.getType(), WeldingRecipe.class);
        if (assemblyRecipe.isPresent())
            return assemblyRecipe;

        weldingInv.setItem(0, item);
        return AllRecipeTypes.WELDING.find(weldingInv, level);
    }
    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        Optional<WeldingRecipe> recipe = getRecipe(input.stack);
        if (!recipe.isPresent())
            return false;
        if (simulate)
            return true;
        weldingBehaviour.particleItems.add(input.stack);
        List<ItemStack> outputs = RecipeApplier.applyRecipeOn(
                canProcessInBulk() ? input.stack : ItemHandlerHelper.copyStackWithSize(input.stack, 1), recipe.get());

        for (ItemStack created : outputs) {
            if (!created.isEmpty()) {
                onItemPressed(created);
                break;
            }
        }

        outputList.addAll(outputs);
        return true;
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        ItemStack item = itemEntity.getItem();
        Optional<WeldingRecipe> recipe = getRecipe(item);
        if (!recipe.isPresent())
            return false;
        if (simulate)
            return true;

        ItemStack itemCreated = ItemStack.EMPTY;
        weldingBehaviour.particleItems.add(item);
        if (canProcessInBulk() || item.getCount() == 1) {
            RecipeApplier.applyRecipeOn(itemEntity, recipe.get());
            itemCreated = itemEntity.getItem()
                    .copy();
        } else {
            for (ItemStack result : RecipeApplier.applyRecipeOn(ItemHandlerHelper.copyStackWithSize(item, 1),
                    recipe.get())) {
                if (itemCreated.isEmpty())
                    itemCreated = result.copy();
                ItemEntity created =
                        new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                created.setDefaultPickUpDelay();
                created.setDeltaMovement(VecHelper.offsetRandomly(Vec3.ZERO, Create.RANDOM, .05f));
                level.addFreshEntity(created);
            }
            item.shrink(1);
        }

        if (!itemCreated.isEmpty())
            onItemPressed(itemCreated);
        return true;
    }
    public WelderTarget getTargetAngles(BlockPos armPos) {
        if (target == null)
            target = new WelderTarget(armPos, VecHelper.getCenterOf(worldPosition.below(2)), Direction.DOWN, true);
        
        return target;
    }
    private boolean tickMovementProgress() {
        boolean targetReachedPreviously = chasedPointProgress >= 1;
        chasedPointProgress += Math.min(256, Math.abs(getSpeed())) / 1024f;
        if (chasedPointProgress > 1)
            chasedPointProgress = 1;
        if (!level.isClientSide)
            return !targetReachedPreviously && chasedPointProgress >= 1;
        
        WelderTarget previousTarget = this.previousTarget;
        WelderTarget target = !running ? WelderTarget.NO_TARGET : getTargetAngles(worldPosition);
        
        
        // Arm's angles first backup to resting position and then continue
        if (chasedPointProgress < .5f)
            target = WelderTarget.NO_TARGET;
        else
            previousTarget = WelderTarget.NO_TARGET;
        float progress = chasedPointProgress == 1 ? 1 : (chasedPointProgress % .5f) * 2;
        
        upperArmAngle.setValue(Mth.lerp(progress, previousTarget.upperArmAngle, target.upperArmAngle));
        headAngle.setValue(AngleHelper.angleLerp(progress, previousTarget.headAngle % 360, target.headAngle % 360));
        
        return false;
    }
    public void renderParticles() {
        if (level == null)
            return;
        boolean belt = level.getBlockEntity(worldPosition.below()) instanceof BeltBlockEntity;
        boolean depot = level.getBlockEntity(worldPosition.below()) instanceof DepotBlockEntity;
        if (!belt || !depot)
            return;
        spillParticle();
    }
    protected void spillParticle() {
        float angle = level.random.nextFloat() * 360;
        Vec3 offset = new Vec3(0, 0, 0.25f);
        offset = VecHelper.rotate(offset, angle, Direction.Axis.Y);
        Vec3 target = VecHelper.rotate(offset, getSpeed() > 0 ? 25 : -25, Direction.Axis.Y)
                .add(0, .25f, 0);
        Vec3 center = offset.add(VecHelper.getCenterOf(worldPosition));
        target = VecHelper.offsetRandomly(target.subtract(offset), level.random, 1 / 128f);
        level.addParticle(ParticleTypes.ELECTRIC_SPARK, center.x, center.y - 1.75f, center.z, target.x, target.y, target.z);
    }
    @Override
    public void tick() {
        super.tick();
        boolean targetReached = tickMovementProgress();
        if (targetReached)
            lazyTick();
        if (runningTicks >= 40) {
            running = false;
            runningTicks = 0;
            return;
        }
        
        float speed = Math.abs(getSpeed());
        if (running && level != null) {
            if (level.isClientSide && runningTicks == 20)
                renderParticles();
            
            if ((!level.isClientSide || isVirtual()) && runningTicks == 20) {
                if (processingTicks < 0) {
                    float recipeSpeed = 1;
                    
                    processingTicks = Mth.clamp((Mth.log2((int) (512 / speed))) * Mth.ceil(recipeSpeed * 15) + 1, 1, 512);
                    
                } else {
                    processingTicks--;
                    if (processingTicks == 0) {
                        runningTicks++;
                        processingTicks = -1;
                        sendData();
                    }
                }
            }
            
            if (runningTicks != 20)
                runningTicks++;
        }
    }
    
    @Override
    protected boolean isRunning() {
        return weldingBehaviour.running;
    }
    
    @Override
    protected void onBasinRemoved() {
        weldingBehaviour.particleItems.clear();
        weldingBehaviour.running = false;
        weldingBehaviour.runningTicks = 0;
        sendData();
    }
    
    @Override
    protected <C extends Container> boolean matchStaticFilters(Recipe<C> recipe) {
        return (!com.simibubi.create.AllRecipeTypes.shouldIgnoreInAutomation(recipe)) || recipe.getType() == AllRecipeTypes.DECIMATING.getType();
    }
    public float getRenderedHeadOffset(float partialTicks) {
        int localTick;
        float offset = 0;
        if (running) {
            if (runningTicks < 20) {
                localTick = runningTicks;
                float num = (localTick + partialTicks) / 20f;
                num = ((2 - Mth.cos((float) (num * Math.PI))) / 2);
                offset = num - .5f;
            } else if (runningTicks <= 20) {
                offset = 1;
            } else {
                localTick = 40 - runningTicks;
                float num = (localTick - partialTicks) / 20f;
                num = ((2 - Mth.cos((float) (num * Math.PI))) / 2);
                offset = num - .5f;
            }
        }
        return offset + 0 / 16f;
    }
    //public float getRenderedHeadRotationSpeed(float partialTicks) {
    //    float speed = getSpeed();
    //    if (!hasFoundryLid())
    //        return 0;
    //    if (running) {
    //        if (runningTicks < 15) {
    //            return speed;
    //        }
    //        if (runningTicks <= 20) {
    //            return speed * 2;
    //        }
    //        return speed;
    //    }
    //    return speed / 2;
    //}
    @Override
    protected Object getRecipeCacheKey() {
        return weldingRecipesKey;
    }
    
    @Override
    public void lazyTick() {
        super.lazyTick();
        
        if (level.isClientSide)
            return;
        if (chasedPointProgress < .5f)
            return;
    }
    @Override
    public boolean canProcessInBulk() {
        return false;
    }

    @Override
    public int getParticleAmount() {
        return 15;
    }

    @Override
    public float getKineticSpeed() {
        return getSpeed();
    }
    
    private boolean hasFoundryLid() {
        if (level == null)
            return false;
        return /*level.getBlockState(worldPosition.below()).getBlock() instanceof TrapDoorBlock;*/ true;
    }
    
    @Override
    public void startProcessingBasin() {
        if (weldingBehaviour.running && weldingBehaviour.runningTicks <= WeldingBehaviour.CYCLE / 2 && !hasFoundryLid())
            return;
        super.startProcessingBasin();
        weldingBehaviour.start(WeldingBehaviour.Mode.BASIN);
    }
}
