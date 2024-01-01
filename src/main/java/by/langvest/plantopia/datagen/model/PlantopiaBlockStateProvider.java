package by.langvest.plantopia.datagen.model;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.block.PlantopiaBlocks;
import by.langvest.plantopia.block.PlantopiaTripleBlockHalf;
import by.langvest.plantopia.block.special.PlantopiaTriplePlantBlock;
import by.langvest.plantopia.meta.property.PlantopiaBlockModelType;
import by.langvest.plantopia.util.PlantopiaIdentifier;
import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlantopiaBlockStateProvider extends BlockStateProvider {
	private static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");
	private static final Set<Direction> HORIZONTAL_DIRECTIONS = ImmutableSet.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
	private final ExistingFileHelper existingFileHelper;

	public PlantopiaBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Plantopia.MOD_ID, existingFileHelper);
		this.existingFileHelper = existingFileHelper;
	}

	@Override
	protected void registerStatesAndModels() {
		generateAll();

		fireweedBlock(PlantopiaBlocks.FIREWEED.get());
		giantFernBlock(PlantopiaBlocks.GIANT_FERN.get());
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			if(!blockMeta.shouldGenerateModel()) return;

			Block block = blockMeta.getBlock();

			if(block instanceof FlowerPotBlock) {
				flowerPotBlock(blockMeta);
				return;
			}

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

	private void flowerPotBlock(@NotNull PlantopiaBlockMeta blockMeta) {
		FlowerPotBlock block = (FlowerPotBlock)blockMeta.getBlock();
		Block plant = block.getContent();
		PlantopiaBlockMeta plantMeta = PlantopiaMetaStore.getBlock(plant);

		if(plantMeta != null && plantMeta.getModelType() == PlantopiaBlockModelType.CUSTOM) return;

		ResourceLocation pottedPlantTexture = texture("potted_" + nameOf(plant));

		ResourceLocation plantTexture = isTextureExists(pottedPlantTexture) ? pottedPlantTexture : blockTexture(plant);

		ModelFile model = flowerPotCrossModel(blockMeta.getName(), plantTexture, blockMeta.isTinted());

		simpleBlock(block, model);
	}

	private void doublePlantBlock(@NotNull PlantopiaBlockMeta blockMeta) {
		String baseName = blockMeta.getName();
		boolean isTinted = blockMeta.isTinted();

		ResourceLocation topTexture = texture(baseName + "_top");
		ResourceLocation bottomTexture = texture(baseName + "_bottom");

		ModelFile topModel = crossModel(baseName + "_top", topTexture, isTinted);
		ModelFile bottomModel = crossModel(baseName + "_bottom", bottomTexture, isTinted);

		generatedItemModel(baseName, topTexture);

		doubleHighBlock(blockMeta.getBlock(), topModel, bottomModel);
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

		tripleHighBlock(blockMeta.getBlock(), topModel, middleModel, bottomModel);
	}

	/* CUSTOM MODELS GENERATION ******************************************/

	private void fireweedBlock(Block block) {
		String baseName = nameOf(block);

		ResourceLocation topTexture = texture(baseName + "_top");
		ResourceLocation flowersTexture = texture(baseName + "_top_flowers");
		ResourceLocation bottomTexture = texture(baseName + "_bottom");

		ModelFile topModel = existingModel(baseName + "_top");
		ModelFile bottomModel = tintedCrossModel(baseName + "_bottom", bottomTexture);

		generatedItemModel(baseName, topTexture, flowersTexture);

		doubleHighBlock(block, topModel, bottomModel);
	}

	private void giantFernBlock(Block block) {
		String baseName = nameOf(block);

		ResourceLocation topTexture = texture(baseName + "_top");
		ResourceLocation middleTexture = texture(baseName + "_middle");
		ResourceLocation bottomTexture = texture(baseName + "_bottom");

		ModelFile topModel = giantFernTemplateModel(baseName + "_top", topTexture);
		ModelFile middleModel = giantFernTemplateModel(baseName + "_middle", middleTexture);
		ModelFile bottomModel = giantFernTemplateModel(baseName + "_bottom", bottomTexture);

		generatedItemModel(baseName, topTexture);

		tripleHighBlock(block, topModel, middleModel, bottomModel);
	}

	/* MODEL GENERATION HELPER METHODS ******************************************/

	private void doubleHighBlock(Block block, ModelFile topModel, ModelFile bottomModel) {
		getVariantBuilder(block).forAllStates(state -> {
			DoubleBlockHalf half = state.getValue(DoublePlantBlock.HALF);
			ModelFile modelFile = switch(half) {
				case UPPER -> topModel;
				case LOWER -> bottomModel;
			};
			return ConfiguredModel.builder().modelFile(modelFile).build();
		});
	}

	private void tripleHighBlock(Block block, ModelFile topModel, ModelFile middleModel, ModelFile bottomModel) {
		getVariantBuilder(block).forAllStates(state -> {
			PlantopiaTripleBlockHalf half = state.getValue(PlantopiaTriplePlantBlock.HALF);
			ModelFile modelFile = switch(half) {
				case UPPER -> topModel;
				case CENTRAL -> middleModel;
				case LOWER -> bottomModel;
			};
			return ConfiguredModel.builder().modelFile(modelFile).build();
		});
	}

	private void directionalPartialBlock(Block block, @NotNull IntegerProperty property) {
		String baseName = nameOf(block);
		Integer maxValue = Collections.max(property.getPossibleValues());
		MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

		property.getPossibleValues().forEach(value -> {
			ArrayList<Integer> values = Lists.newArrayList();
			for(int i = value; i <= maxValue; i++) values.add(i);

			ModelFile model = existingModel(baseName + "_" + value);

			HORIZONTAL_DIRECTIONS.forEach(direction ->
				builder.part()
					.modelFile(model).rotationY((((int)direction.toYRot()) + 180) % 360).addModel()
					.condition(BlockStateProperties.HORIZONTAL_FACING, direction)
					.condition(property, values.toArray(Integer[]::new))
					.end()
			);
		});
	}

	/* BLOCK MODELS ******************************************/

	@Contract("_ -> new")
	private @NotNull ModelFile existingModel(String name) {
		return new ModelFile.ExistingModelFile(new PlantopiaIdentifier(ModelProvider.BLOCK_FOLDER + "/" + name), existingFileHelper);
	}

	private ModelFile crossModel(String name, ResourceLocation crossTexture, boolean tinted) {
		if(tinted) return tintedCrossModel(name, crossTexture);
		return crossModel(name, crossTexture);
	}

	private ModelFile flowerPotCrossModel(String name, ResourceLocation plantTexture, boolean tinted) {
		if(tinted) return tintedFlowerPotCrossModel(name, plantTexture);
		return flowerPotCrossModel(name, plantTexture);
	}

	private ModelFile flowerPotCrossModel(String name, ResourceLocation plantTexture) {
		return models().withExistingParent(name, "flower_pot_cross")
			.texture("plant", plantTexture);
	}

	private ModelFile tintedFlowerPotCrossModel(String name, ResourceLocation plantTexture) {
		return models().withExistingParent(name, "tinted_flower_pot_cross")
			.texture("plant", plantTexture);
	}

	private ModelFile crossModel(String name, ResourceLocation crossTexture) {
		return models().cross(name, crossTexture);
	}

	private ModelFile tintedCrossModel(String name, ResourceLocation crossTexture) {
		return models().withExistingParent(name, "tinted_cross")
			.texture("cross", crossTexture);
	}

	private ModelFile giantFernTemplateModel(String name, ResourceLocation crossTexture) {
		return models().withExistingParent(name, parent("giant_fern_template"))
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

	private boolean isTextureExists(@NotNull ResourceLocation texture) {
		return existingFileHelper.exists(texture, TEXTURE);
	}

	@Contract("_ -> new")
	private static @NotNull ResourceLocation texture(String name) {
		return new PlantopiaIdentifier(ModelProvider.BLOCK_FOLDER + "/" + name);
	}

	@Contract("_ -> new")
	private static @NotNull ResourceLocation itemTexture(String name) {
		return new PlantopiaIdentifier(ModelProvider.ITEM_FOLDER + "/" + name);
	}

	@Contract("_ -> new")
	private static @NotNull ResourceLocation parent(String name) {
		return new PlantopiaIdentifier(ModelProvider.BLOCK_FOLDER + "/" + name);
	}

	private static @NotNull String nameOf(@NotNull Block block) {
		return Objects.requireNonNull(block.getRegistryName()).getPath();
	}
}