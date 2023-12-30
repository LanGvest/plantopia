package by.langvest.plantopia.datagen.model;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.block.PlantopiaTripleBlockHalf;
import by.langvest.plantopia.block.special.PlantopiaTriplePlantBlock;
import by.langvest.plantopia.util.PlantopiaIdentifier;
import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PlantopiaBlockStateProvider extends BlockStateProvider {
	public PlantopiaBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Plantopia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		generateAll();
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			if(!blockMeta.shouldGenerateModel()) return;

			Block block = blockMeta.getBlock();

			if(block instanceof PlantopiaTriplePlantBlock) {
				triplePlantBlock(blockMeta);
				return;
			}

			if(block instanceof DoublePlantBlock) {
				doublePlantBlock(blockMeta);
			}
		});
	}

	/* MODELS GENERATION ******************************************/

	private void doublePlantBlock(@NotNull PlantopiaBlockMeta blockMeta) {
		String baseName = blockMeta.getName();
		boolean isTinted = blockMeta.isTinted();

		ResourceLocation topTexture = texture(baseName + "_top");
		ResourceLocation bottomTexture = texture(baseName + "_bottom");

		ModelFile topModel = crossModel(baseName + "_top", topTexture, isTinted);
		ModelFile bottomModel = crossModel(baseName + "_bottom", bottomTexture, isTinted);

		generatedItemModel(baseName, topTexture);

		getVariantBuilder(blockMeta.getBlock()).forAllStates(state -> {
			DoubleBlockHalf half = state.getValue(DoublePlantBlock.HALF);
			ModelFile modelFile = switch(half) {
				case UPPER -> topModel;
				case LOWER -> bottomModel;
			};
			return ConfiguredModel.builder().modelFile(modelFile).build();
		});
	}

	private void triplePlantBlock(@NotNull PlantopiaBlockMeta blockMeta) {
		String baseName = blockMeta.getName();
		boolean isTinted = blockMeta.isTinted();

		ResourceLocation topTexture = texture(baseName + "_top");
		ResourceLocation middleTexture = texture(baseName + "_middle");
		ResourceLocation bottomTexture = texture(baseName + "_bottom");

		ModelFile topModel = crossModel(baseName + "_top", topTexture, isTinted);
		ModelFile middleModel = crossModel(baseName + "_middle", middleTexture, isTinted);
		ModelFile bottomModel = crossModel(baseName + "_bottom", bottomTexture, isTinted);

		generatedItemModel(baseName, topTexture);

		getVariantBuilder(blockMeta.getBlock()).forAllStates(state -> {
			PlantopiaTripleBlockHalf half = state.getValue(PlantopiaTriplePlantBlock.HALF);
			ModelFile modelFile = switch(half) {
				case UPPER -> topModel;
				case CENTRAL -> middleModel;
				case LOWER -> bottomModel;
			};
			return ConfiguredModel.builder().modelFile(modelFile).build();
		});
	}

	/* BLOCK MODELS ******************************************/

	private ModelFile crossModel(String name, ResourceLocation crossTexture, boolean tinted) {
		if(tinted) return tintedCrossBlockModel(name, crossTexture);
		return crossModel(name, crossTexture);
	}

	private ModelFile crossModel(String name, ResourceLocation crossTexture) {
		return models().cross(name, crossTexture);
	}

	private ModelFile tintedCrossBlockModel(String name, ResourceLocation crossTexture) {
		return models().withExistingParent(name, "tinted_cross")
			.texture("cross", crossTexture);
	}

	/* ITEM MODELS ******************************************/

	@SuppressWarnings("UnusedReturnValue")
	private ModelFile generatedItemModel(String name, ResourceLocation... layers) {
		ItemModelBuilder itemModel = itemModels().withExistingParent(name, "generated");
		int layerIndex = 0;
		if(layers != null) for(ResourceLocation layeredTexture : layers) itemModel.texture("layer" + layerIndex++, layeredTexture);
		return itemModel;
	}

	/* HELPER METHODS ******************************************/

	@Contract("_ -> new")
	private @NotNull ResourceLocation texture(String name) {
		return new PlantopiaIdentifier("block/" + name);
	}
}