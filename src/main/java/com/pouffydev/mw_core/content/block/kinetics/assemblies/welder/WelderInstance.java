package com.pouffydev.mw_core.content.block.kinetics.assemblies.welder;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogInstance;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.utility.AnimationTickHolder;

public class WelderInstance extends EncasedCogInstance implements DynamicInstance {
    
    private final OrientedData welderHead;
    private final WelderBlockEntity welder;
    
    public WelderInstance(MaterialManager materialManager, WelderBlockEntity blockEntity) {
        super(materialManager, blockEntity, false);
        this.welder = blockEntity;
        
        
        
        welderHead = getOrientedMaterial()
                .getModel(AllBlockPartials.WELDER_HEAD, blockState)
                .createInstance();
        
        
        float renderedHeadOffset = getRenderedHeadOffset();
        
        transformPole(renderedHeadOffset);
    }
    
    @Override
    protected Instancer<RotatingData> getCogModel() {
        return materialManager.defaultSolid()
                .material(AllMaterialSpecs.ROTATING)
                .getModel(AllBlockPartials.WELDER_COG, blockEntity.getBlockState());
    }
    
    @Override
    public void beginFrame() {
        
        float renderedHeadOffset = getRenderedHeadOffset();
        
        transformPole(renderedHeadOffset);
    }
    
    
    private void transformPole(float renderedHeadOffset) {
        welderHead.setPosition(getInstancePosition())
                .nudge(0, -renderedHeadOffset, 0);
    }
    
    private float getRenderedHeadOffset() {
        return welder.getRenderedHeadOffset(AnimationTickHolder.getPartialTicks());
    }
    
    @Override
    public void updateLight() {
        super.updateLight();
        
        relight(pos, welderHead);
    }
    
    @Override
    public void remove() {
        super.remove();
        welderHead.delete();
    }
}
