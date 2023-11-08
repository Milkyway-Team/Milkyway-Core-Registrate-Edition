package com.pouffydev.mw_core.content.block.kinetics.assemblies.roller;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.FlatLit;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class RollerInstance extends ShaftInstance<RollerBlockEntity> {
    protected RotatingData wheel;
    protected RotatingData shaft;
    public RollerInstance(MaterialManager materialManager, RollerBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    public void init() {
        this.wheel = this.getRotatingMaterial().getModel(AllBlockPartials.ROLLER_WHEEL, this.blockState).createInstance();
        this.shaft = this.setup((RotatingData)this.getModel().createInstance());
        
        shaft.setRotationAxis(axis)
                .setRotationalSpeed(getBlockEntitySpeed())
                .setRotationOffset(-getRotationOffset(axis))
                .setColor(blockEntity)
                .setPosition(getInstancePosition());
        //wheel.setRotationAxis(axis)
        //        .setRotationalSpeed(getBlockEntitySpeed())
        //        .setRotationOffset(-getRotationOffset(axis))
        //        .setColor(blockEntity)
        //        .setPosition(getInstancePosition())
        //        .nudge(0, 9f/16f, 0)
        //        .setRotationalSpeed(-getBlockEntitySpeed());
    }
    public void update() {
        this.updateRotation(this.wheel);
        this.updateRotation(this.shaft);
        wheel.setRotationalSpeed(-getBlockEntitySpeed());
    }

    public void updateLight() {
        this.relight(this.pos, this.wheel, this.shaft);
    }

    public void remove() {
        this.wheel.delete();
        this.shaft.delete();
    }
    protected BlockState getRenderedBlockState() {
        return AllBlocks.SHAFT.getDefaultState().setValue(ShaftBlock.AXIS, blockState.getValue(RollerBlock.HORIZONTAL_FACING).getAxis());
    }

    protected Instancer<RotatingData> getModel() {
        return this.getRotatingMaterial().getModel(this.getRenderedBlockState());
    }
}
