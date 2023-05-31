package com.pouffydev.mw_core.content.tinkers.tools.data.material;

import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.IRON;
import static net.minecraft.world.item.Tiers.NETHERITE;

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
                new HeadMaterialStats(500, 3.0f, NETHERITE, 2.4f),
                HandleMaterialStats.DEFAULT.withDurability(2.5f).withAttackSpeed(0.75f),
                ExtraMaterialStats.DEFAULT);
    }
    private void addRanged() {
        addMaterialStats(MWMaterialIds.transium,
                new LimbMaterialStats(400, 0.25f, 0.25f, 0.3f),
                new GripMaterialStats(2.0f, 0.25f, 2.5f));
    }
    private void addMisc() {
        addMaterialStats(MWMaterialIds.transium, new RepairKitStats(500));

    }

}
