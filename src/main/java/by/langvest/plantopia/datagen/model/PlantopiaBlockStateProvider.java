package by.langvest.plantopia.datagen.model;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.meta.property.PlantopiaBlockModelType;
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
			Block block = blockMeta.getBlock();
			PlantopiaBlockModelType modelType = blockMeta.getModelType();

			if(modelType != PlantopiaBlockModelType.GENERATED) return;

			if(block instanceof DoublePlantBlock) {
				doublePlantBlock(blockMeta);
			}
		});
	}

	private void doublePlantBlock(@NotNull PlantopiaBlockMeta blockMeta) {
		String baseName = blockMeta.getName();

		ResourceLocation topTexture = textureLoc(baseName + "_top");
		ResourceLocation bottomTexture = textureLoc(baseName + "_bottom");

		ModelFile topModel = models().cross(baseName + "_top", topTexture);
		ModelFile bottomModel = models().cross(baseName + "_bottom", bottomTexture);

		itemModels().withExistingParent(baseName, "generated").texture("layer0", topTexture);

		getVariantBuilder(blockMeta.getBlock()).forAllStates(state -> {
			DoubleBlockHalf half = state.getValue(DoublePlantBlock.HALF);
			ModelFile modelFile = half == DoubleBlockHalf.UPPER ? topModel : bottomModel;
			return ConfiguredModel.builder().modelFile(modelFile).build();
		});
	}

	@Contract("_ -> new")
	private @NotNull ResourceLocation textureLoc(String name) {
		return new PlantopiaIdentifier("block/" + name);
	}
}