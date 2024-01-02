package by.langvest.plantopia.datagen.recipe;

import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.PlantopiaBlockMeta.MetaType;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.util.PlantopiaIdentifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class PlantopiaRecipeProvider extends RecipeProvider implements IConditionBuilder {
	private Consumer<FinishedRecipe> consumer;

	public PlantopiaRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
		setConsumer(consumer);
		generateAll();
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
		oneToOneConversionRecipe(blockMeta.getBlock(), dye, nameOf(dye), blockMeta.getBlockHighType().getBaseHigh());
	}

	/* RECIPE GENERATION HELPER METHODS ******************************************/

	private void oneToOneConversionRecipe(ItemLike ingredient, ItemLike result, String group, int amount) {
		ShapelessRecipeBuilder.shapeless(result, amount)
			.requires(ingredient)
			.group(group)
			.unlockedBy(getHasName(ingredient), has(ingredient))
			.save(consumer, new PlantopiaIdentifier(getConversionRecipeName(result, ingredient)));
	}

	/* HELPER METHODS ******************************************/

	private static @NotNull String nameOf(@NotNull Item item) {
		return Objects.requireNonNull(item.getRegistryName()).getPath();
	}
}