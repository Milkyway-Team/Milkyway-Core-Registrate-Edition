package com.pouffydev.mw_core.content.block.generators.combustion;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.pouffydev.mw_core.index.AllBlockPartials;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogInstance;
import com.simibubi.create.foundation.render.AllMaterialSpecs;

public class CombustionEngineInstance extends EncasedCogInstance implements DynamicInstance {

    public CombustionEngineInstance(MaterialManager modelManager, CombustionEngineBlockEntity blockEntity) {
        super(modelManager, blockEntity, false);
    }

    protected Instancer<RotatingData> getCogModel() {
        return this.materialManager.defaultSolid().material(AllMaterialSpecs.ROTATING).getModel(AllBlockPartials.COMBUSTION_COG, ((KineticBlockEntity)this.blockEntity).getBlockState());
    }

    @Override
    public void beginFrame() {

    }
}
