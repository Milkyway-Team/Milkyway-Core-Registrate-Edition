package com.pouffydev.mw_core.content.block.generators.reactor;

import com.mrh0.createaddition.config.Config;
import com.pouffydev.mw_core.content.block.generators.reactor.chamber.ReactorChamberBlockEntity;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ReactorCogwheelBlockEntity extends GeneratingKineticBlockEntity {
    public ReactorCogwheelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    protected boolean updateGenerator;
    private boolean active = false;
    private boolean generatorOverreacting = false;
    @Override
    public float calculateAddedStressCapacity() {
        return lastCapacityProvided = (active ? super.calculateAddedStressCapacity() : 0);
    }
    public void initialize() {
        super.initialize();
        if (!this.hasSource() || this.getGeneratedSpeed() > this.getTheoreticalSpeed()) {
            this.updateGeneratedRotation();
        }
        
    }
    
    public boolean hasChamberAbove() {
        BlockEntity chamberBE = level.getBlockEntity(worldPosition.above(2));
        return chamberBE instanceof ReactorChamberBlockEntity;
    }
    public void updateGenerator() {
        boolean shouldGenerate = false;
        if (hasChamberAbove()) {
            ReactorChamberBlockEntity chamber = (ReactorChamberBlockEntity) level.getBlockEntity(worldPosition.above(2));
            if (chamber != null)
                shouldGenerate = chamber.isRunning;
        }
        
        if (active != shouldGenerate) {
            active = shouldGenerate;
        }
        
        updateGeneratedRotation();
    }
    public void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        this.active = compound.getBoolean("active");
        this.generatorOverreacting = compound.getBoolean("generatorOverreacting");
    }
    
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putBoolean("active", this.active);
        compound.putBoolean("generatorOverreacting", this.generatorOverreacting);
    }
    public int generatorSpeed() {
        return generatorOverreacting ? 128 : 32;
    }
    public int produceSpeed() {
        ReactorChamberBlockEntity chamber = (ReactorChamberBlockEntity) level.getBlockEntity(worldPosition.above(2));
        if (level == null || chamber == null)
            return 0;
        
        return chamber.isRunning ? generatorSpeed() : 0;
    }
    @Override
    public float getGeneratedSpeed() {
        return produceSpeed();
    }
    @Override
    public void lazyTick() {
        super.lazyTick();
        if (level == null)
            return;
        if (hasChamberAbove()) {
            ReactorChamberBlockEntity chamber = (ReactorChamberBlockEntity) level.getBlockEntity(worldPosition.above(2));
            if (chamber != null) {
                updateGenerator = chamber.isRunning;
                active = chamber.isRunning;
                generatorOverreacting = chamber.isOverreacting;
                if (!chamber.isRunning)
                    updateGenerator();
            }
        }
    }
    @Override
    public void tick() {
        super.tick();
    
        assert level != null;
        boolean server = !level.isClientSide || isVirtual();
    
        if (updateGenerator) {
            updateGenerator = false;
            updateGenerator();
        }
    
        if (active || getSpeed() == 0)
            return;
    
    }
}
