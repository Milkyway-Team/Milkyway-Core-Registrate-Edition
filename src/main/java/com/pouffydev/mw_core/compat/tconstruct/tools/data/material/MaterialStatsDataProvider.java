package com.pouffydev.mw_core.compat.tconstruct.tools.data.material;

import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.*;

public class MaterialStatsDataProvider  extends AbstractMaterialStatsDataProvider {
    public MaterialStatsDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
        super(gen, materials);
    }

    @Override
    public String getName() {
        return "Create: Milkyway Material Stats";
    }

    @Override
    protected void addMaterialStats() {
        addMeleeHarvest();
        addRanged();
        addMisc();
    }

    private void addMeleeHarvest() {
        addMaterialStats(MWMaterialIds.transium,
                new HeadMaterialStats(500, 3.0f, DIAMOND, 2.4f),
                HandleMaterialStats.DEFAULT.withDurability(1.5f).withAttackSpeed(0.75f),
                ExtraMaterialStats.DEFAULT);
        addMaterialStats(MWMaterialIds.ancient_bismuth,
                new HeadMaterialStats(750, 4.0f, NETHERITE, 3.4f),
                HandleMaterialStats.DEFAULT.withDurability(1.75f).withAttackSpeed(0.5f),
                ExtraMaterialStats.DEFAULT);
    }
    private void addRanged() {
        addMaterialStats(MWMaterialIds.transium,
                new LimbMaterialStats(400, 0.25f, 0.25f, 0.3f),
                new GripMaterialStats(2.0f, 0.25f, 2.5f));
        addMaterialStats(MWMaterialIds.ancient_bismuth,
                new LimbMaterialStats(600, 0.35f, 0.5f, 0.6f),
                new GripMaterialStats(1.75f, 0.45f, 1.5f));
    }
    private void addMisc() {
        addMaterialStats(MWMaterialIds.transium, new RepairKitStats(500));
        addMaterialStats(MWMaterialIds.ancient_bismuth, new RepairKitStats(750));
    }

}
