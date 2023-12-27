package by.langvest.plantopia.datagen.tag;

import by.langvest.plantopia.Plantopia;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class PlantopiaItemTagProvider extends ItemTagsProvider {
	public PlantopiaItemTagProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generator, blockTagsProvider, Plantopia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		generateAll();
		saveAll();
	}

	private void add(@NotNull Set<Block> tag, Block... blocks) {
		tag.addAll(List.of(blocks));
	}

	private void generateAll() {}

	private void saveAll() {}
}