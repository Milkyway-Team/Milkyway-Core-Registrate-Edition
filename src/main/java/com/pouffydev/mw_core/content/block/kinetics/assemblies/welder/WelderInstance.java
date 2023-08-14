package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.google.common.collect.Lists;
import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.util.Mth;

import java.util.ArrayList;

public class WelderInstance extends SingleRotatingInstance<WelderBlockEntity> implements DynamicInstance {
    final ModelData base;
    final ModelData lowerBody;
    final ModelData upperBody;
    final ModelData head;
    private final ArrayList<ModelData> models;
    private float baseAngle = Float.NaN;
    private float lowerArmAngle = Float.NaN;
    private float upperArmAngle = Float.NaN;
    private float headAngle = Float.NaN;
    private boolean firstRender = true;
    public WelderInstance(MaterialManager materialManager, WelderBlockEntity blockEntity) {
        super(materialManager, blockEntity);
        Material<ModelData> mat = getTransformMaterial();
        base = mat.getModel(AllBlockPartials.WELDER_BASE, blockState)
                .createInstance();
        lowerBody = mat.getModel(AllBlockPartials.WELDER_LOWER_BODY, blockState)
                .createInstance();
        upperBody = mat.getModel(AllBlockPartials.WELDER_UPPER_BODY, blockState)
                .createInstance();
        head = mat.getModel(AllBlockPartials.WELDER_HEAD, blockState)
                .createInstance();
        models = Lists.newArrayList(base, lowerBody, upperBody, head);
        animateArm();
    }

    @Override
    public void beginFrame() {
        float pt = AnimationTickHolder.getPartialTicks();

        //float baseAngleNow = blockEntity.baseAngle.getValue(pt);
        //float lowerArmAngleNow = blockEntity.lowerArmAngle.getValue(pt);
        //float upperArmAngleNow = blockEntity.upperArmAngle.getValue(pt);
        //float headAngleNow = blockEntity.headAngle.getValue(pt);
        //boolean settled = Mth.equal(baseAngle, baseAngleNow) && Mth.equal(lowerArmAngle, lowerArmAngleNow)
        //       && Mth.equal(upperArmAngle, upperArmAngleNow) && Mth.equal(headAngle, headAngleNow);

        //this.baseAngle = baseAngleNow;
        //this.lowerArmAngle = lowerArmAngleNow;
        //this.upperArmAngle = upperArmAngleNow;
        //this.headAngle = headAngleNow;

        //if (!settled || firstRender)
        //    animateArm();

        if (firstRender)
            firstRender = false;
    }
    private void animateArm() {
        float baseAngle;
        float lowerArmAngle;
        float upperArmAngle;
        float headAngle;
        int color;

        baseAngle = this.baseAngle;
        lowerArmAngle = this.lowerArmAngle - 135;
        upperArmAngle = this.upperArmAngle - 90;
        headAngle = this.headAngle;
        color = 0xFFFFFF;


        PoseStack msLocal = new PoseStack();
        TransformStack msr = TransformStack.cast(msLocal);
        msr.translate(getInstancePosition());
        msr.centre();

        msr.rotateX(180);

        ArmRenderer.transformBase(msr, baseAngle);
        base.setTransform(msLocal);

        ArmRenderer.transformLowerArm(msr, lowerArmAngle);
        lowerBody.setTransform(msLocal)
                .setColor(color);

        ArmRenderer.transformUpperArm(msr, upperArmAngle);
        upperBody.setTransform(msLocal)
                .setColor(color);

        ArmRenderer.transformHead(msr, headAngle);
    }
    @Override
    public void update() {
        super.update();
        updateLight();
        animateArm();
    }

    @Override
    public void updateLight() {
        super.updateLight();
        relight(pos, models.stream());
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(AllBlockPartials.WELDER_COG, blockEntity.getBlockState());
    }

    @Override
    public void remove() {
        super.remove();
        models.forEach(InstanceData::delete);
    }
}
