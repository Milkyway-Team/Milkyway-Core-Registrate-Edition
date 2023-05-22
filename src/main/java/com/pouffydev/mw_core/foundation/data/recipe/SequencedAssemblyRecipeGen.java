package com.pouffydev.mw_core.foundation.data.recipe;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import net.minecraft.data.DataGenerator;

import java.util.function.UnaryOperator;

public class SequencedAssemblyRecipeGen extends MilkywayRecipeProvider {

	//GeneratedRecipe

//	TEST = create("test", b -> b.require(I.goldSheet())
//		.transitionTo(AllItems.INCOMPLETE_CLOCKWORK_COMPONENT.get())
//		.addOutput(AllItems.CLOCKWORK_COMPONENT.get(), 10)
//		.addOutput(AllItems.GOLDEN_SHEET.get(), 5)
//		.addOutput(AllItems.ANDESITE_ALLOY.get(), 2)
//		.addOutput(AllBlocks.COGWHEEL.get(), 1)
//		.loops(5)
//		.addStep(DeployerApplicationRecipe::new, rb -> rb.require(I.cog()))
//		.addStep(PressingRecipe::new, rb -> rb)
//		.addStep(FillingRecipe::new, rb -> rb.require(Fluids.LAVA, 500))
//		.addStep(CuttingRecipe::new, rb -> rb.averageProcessingDuration())
//		.addStep(FillingRecipe::new, rb -> rb.require(Fluids.WATER, 100))
//		)

	;

	public SequencedAssemblyRecipeGen(DataGenerator p_i48262_1_) {
		super(p_i48262_1_);
	}

	protected GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
		GeneratedRecipe generatedRecipe =
			c -> transform.apply(new SequencedAssemblyRecipeBuilder(Create.asResource(name)))
				.build(c);
		all.add(generatedRecipe);
		return generatedRecipe;
	}

	@Override
	public String getName() {
		return "Create: Milkyway's Sequenced Assembly Recipes";
	}

}
