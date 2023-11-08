package com.pouffydev.mw_core.foundation.data.recipe;

import com.pouffydev.mw_core.MWCore;
import com.pouffydev.mw_core.content.block.kinetics.assemblies.welder.WeldingRecipe;
import com.pouffydev.mw_core.index.AllItems;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.material.Fluids;

import java.util.function.UnaryOperator;

public class SequencedAssemblyRecipeGen extends MilkywayRecipeProvider {
	GeneratedRecipe

	STURDY_IRON = create("sturdy_iron_sheet", b -> b.require(M.ironSheet())
		.transitionTo(AllItems.unprocessedIron.get())
		.addOutput(AllItems.sturdyIron.get(), 10)
		.loops(2)
		.addStep(DeployerApplicationRecipe::new, rb -> rb.require(M.ironSheet()))
		.addStep(WeldingRecipe::new, rb -> rb)
		.addStep(FillingRecipe::new, rb -> rb.require(Fluids.LAVA, 500))
		.addStep(PressingRecipe::new, rb -> rb)
		.addStep(FillingRecipe::new, rb -> rb.require(Fluids.WATER, 100))
		)

	;

	public SequencedAssemblyRecipeGen(DataGenerator generator) {
		super(generator);
	}

	protected GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
		GeneratedRecipe generatedRecipe =
				c -> transform.apply(new SequencedAssemblyRecipeBuilder(MWCore.asResource(name)))
						.build(c);
		all.add(generatedRecipe);
		return generatedRecipe;
	}

	@Override
	public String getName() {
		return "Create: Milkyway's Sequenced Assembly Recipes";
	}

}
