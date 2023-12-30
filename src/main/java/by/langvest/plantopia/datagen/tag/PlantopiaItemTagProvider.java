package by.langvest.plantopia.datagen.tag;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.tag.PlantopiaItemTags;
import com.google.common.collect.Sets;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;

public class PlantopiaItemTagProvider extends ItemTagsProvider {
	private final Set<Item> IGNORED_BY_BEES = Sets.newHashSet();
	private final Set<Item> PREFERRED_BY_BEES = Sets.newHashSet();

	public PlantopiaItemTagProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generator, blockTagsProvider, Plantopia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		generateAll();

		add(IGNORED_BY_BEES, Blocks.WITHER_ROSE);

		saveAll();
	}

	private void add(@NotNull Set<Item> tag, ItemLike... items) {
		ArrayList<Item> itemsToBeAdded = new ArrayList<>();
		if(items != null) for(ItemLike item : items) itemsToBeAdded.add(item.asItem());
		tag.addAll(itemsToBeAdded);
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			Block block = blockMeta.getBlock();

			if(blockMeta.isIgnoredByBees()) IGNORED_BY_BEES.add(block.asItem());
			if(blockMeta.isPreferredByBees()) PREFERRED_BY_BEES.add(block.asItem());
		});
	}

	private void saveAll() {
		if(!IGNORED_BY_BEES.isEmpty()) tag(PlantopiaItemTags.IGNORED_BY_BEES).add(IGNORED_BY_BEES.toArray(Item[]::new));
		if(!PREFERRED_BY_BEES.isEmpty()) tag(PlantopiaItemTags.PREFERRED_BY_BEES).add(PREFERRED_BY_BEES.toArray(Item[]::new));
	}
}