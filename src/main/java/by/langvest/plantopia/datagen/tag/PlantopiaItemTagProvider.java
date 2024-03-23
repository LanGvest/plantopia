package by.langvest.plantopia.datagen.tag;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.meta.object.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.store.PlantopiaMetaStore;
import by.langvest.plantopia.tag.PlantopiaItemTags;
import by.langvest.plantopia.util.PlantopiaContentHelper;
import by.langvest.plantopia.util.PlantopiaTagSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class PlantopiaItemTagProvider extends ItemTagsProvider {
	private final PlantopiaTagSet<Item> TALL_FLOWERS = PlantopiaTagSet.newTagSet();
	private final PlantopiaTagSet<Item> SMALL_FLOWERS = PlantopiaTagSet.newTagSet();
	private final PlantopiaTagSet<Item> LEAVES = PlantopiaTagSet.newTagSet();
	private final PlantopiaTagSet<Item> SAPLINGS = PlantopiaTagSet.newTagSet();
	private final PlantopiaTagSet<Item> IGNORED_BY_BEES = PlantopiaTagSet.newTagSet();
	private final PlantopiaTagSet<Item> PREFERRED_BY_BEES = PlantopiaTagSet.newTagSet();

	public PlantopiaItemTagProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, PlantopiaBlockTagProvider.getInstance(), Plantopia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		generateAll();

		add(IGNORED_BY_BEES, Blocks.WITHER_ROSE);

		saveAll();
	}

	private void add(@NotNull PlantopiaTagSet<Item> tagSet, ItemLike... items) {
		tagSet.add(Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new));
	}

	@SafeVarargs
	@SuppressWarnings("unused")
	private void add(@NotNull PlantopiaTagSet<Item> tagSet, TagKey<Item>... tags) {
		tagSet.addTags(tags);
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			if(!blockMeta.hasItem()) return;

			Block block = blockMeta.getBlock();
			Item item = block.asItem();
			PlantopiaBlockMeta.MetaType type = blockMeta.getType();
			int baseHeight = blockMeta.getBlockHeightType().getBaseHeight();

			if(blockMeta.isIgnoredByBees()) IGNORED_BY_BEES.add(item);
			if(blockMeta.isPreferredByBees()) PREFERRED_BY_BEES.add(item);

			if(type.isFlowerLike()) {
				if(baseHeight > 1) TALL_FLOWERS.add(item);
				else SMALL_FLOWERS.add(item);
			}

			if(type.isLeavesLike()) LEAVES.add(item);
			if(type.isSaplingLike()) SAPLINGS.add(item);
		});
	}

	private void saveAll() {
		save(ItemTags.TALL_FLOWERS, TALL_FLOWERS);
		save(ItemTags.SMALL_FLOWERS, SMALL_FLOWERS);
		save(ItemTags.LEAVES, LEAVES);
		save(ItemTags.SAPLINGS, SAPLINGS);
		save(PlantopiaItemTags.IGNORED_BY_BEES, IGNORED_BY_BEES);
		save(PlantopiaItemTags.PREFERRED_BY_BEES, PREFERRED_BY_BEES);
	}

	private void save(TagKey<Item> key, @NotNull PlantopiaTagSet<Item> tagSet) {
		if(tagSet.isEmpty()) return;

		TagsProvider.TagAppender<Item> targetTag = tag(key);

		ArrayList<TagKey<Item>> tags = tagSet.getTags();
		ArrayList<Item> items = tagSet.getElements();

		tags.sort(Comparator.comparing(PlantopiaContentHelper::idOf));
		items.sort(Comparator.comparing(PlantopiaContentHelper::idOf));

		for(TagKey<Item> tag : tags) targetTag.addTag(tag);
		for(Item item : items) targetTag.add(item);
	}
}