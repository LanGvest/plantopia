package by.langvest.plantopia.datagen.recipe;

import by.langvest.plantopia.block.PlantopiaBlocks;
import by.langvest.plantopia.meta.object.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.object.PlantopiaBlockMeta.MetaType;
import by.langvest.plantopia.meta.store.PlantopiaMetaStore;
import by.langvest.plantopia.util.PlantopiaIdentifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static by.langvest.plantopia.util.PlantopiaContentHelper.*;

public class PlantopiaRecipeProvider extends RecipeProvider implements IConditionBuilder {
	private Consumer<FinishedRecipe> consumer;

	public PlantopiaRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
		setConsumer(consumer);
		generateAll();

		fullBlockRecipe(Blocks.COBBLESTONE, PlantopiaBlocks.COBBLESTONE_SHARD.get());
		fullBlockRecipe(Blocks.MOSSY_COBBLESTONE, PlantopiaBlocks.MOSSY_COBBLESTONE_SHARD.get());
		stonecutterRecipe(PlantopiaBlocks.COBBLESTONE_SHARD.get(), Blocks.COBBLESTONE, 9);
		stonecutterRecipe(PlantopiaBlocks.MOSSY_COBBLESTONE_SHARD.get(), Blocks.MOSSY_COBBLESTONE, 9);
	}

	private void setConsumer(@NotNull Consumer<FinishedRecipe> consumer) {
		this.consumer = consumer;
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			if(!blockMeta.shouldGenerateRecipe()) return;

			MetaType type = blockMeta.getType();

			if(type.isFlowerLike()) {
				dyeFromFlower(blockMeta);
			}
		});
	}

	/* RECIPES GENERATION ******************************************/

	private void dyeFromFlower(@NotNull PlantopiaBlockMeta blockMeta) {
		Item dye = blockMeta.getDye();
		if(dye == null) return;
		oneToOneConversionRecipe(dye, blockMeta.getBlock(), nameOf(dye), blockMeta.getBlockHeightType().getBaseHeight());
	}

	/* RECIPE GENERATION HELPER METHODS ******************************************/

	private void oneToOneConversionRecipe(ItemLike result, ItemLike ingredient, String group, int resultAmount) {
		ShapelessRecipeBuilder.shapeless(result, resultAmount)
			.requires(ingredient)
			.group(group)
			.unlockedBy(getHasName(ingredient), has(ingredient))
			.save(consumer, new PlantopiaIdentifier(getConversionRecipeName(result, ingredient)));
	}

	private void fullBlockRecipe(ItemLike result, ItemLike ingredient) {
		ShapedRecipeBuilder.shaped(result)
			.define('#', ingredient)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.unlockedBy(getHasName(ingredient), has(ingredient))
			.save(consumer, new PlantopiaIdentifier(getSimpleRecipeName(result)));
	}

	private void stonecutterRecipe(ItemLike result, ItemLike ingredient, int resultAmount) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), result, resultAmount)
			.unlockedBy(getHasName(ingredient), has(ingredient))
			.save(consumer, new PlantopiaIdentifier(getConversionRecipeName(result, ingredient) + "_stonecutting"));
	}
}