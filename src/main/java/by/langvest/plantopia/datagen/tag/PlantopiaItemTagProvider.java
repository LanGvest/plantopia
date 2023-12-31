package by.langvest.plantopia.datagen.tag;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.tag.PlantopiaItemTags;
import by.langvest.plantopia.util.PlantopiaTagSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PlantopiaItemTagProvider extends ItemTagsProvider {
	private final PlantopiaTagSet<Item> IGNORED_BY_BEES = PlantopiaTagSet.newTagSet();
	private final PlantopiaTagSet<Item> PREFERRED_BY_BEES = PlantopiaTagSet.newTagSet();

	public PlantopiaItemTagProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generator, blockTagsProvider, Plantopia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		generateAll();

		add(IGNORED_BY_BEES, Blocks.WITHER_ROSE);

		saveAll();
	}

	private void add(@NotNull PlantopiaTagSet<Item> tagSet, ItemLike... itemLikes) {
		tagSet.add(Arrays.stream(itemLikes).map(ItemLike::asItem).toArray(Item[]::new));
	}

	@SafeVarargs
	private void addTags(@NotNull PlantopiaTagSet<Item> tagSet, TagKey<Item>... tags) {
		tagSet.addTags(tags);
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			if(!blockMeta.hasItem()) return;

			Block block = blockMeta.getBlock();

			if(blockMeta.isIgnoredByBees()) IGNORED_BY_BEES.add(block.asItem());
			if(blockMeta.isPreferredByBees()) PREFERRED_BY_BEES.add(block.asItem());
		});
	}

	private void saveAll() {
		save(PlantopiaItemTags.IGNORED_BY_BEES, IGNORED_BY_BEES);
		save(PlantopiaItemTags.PREFERRED_BY_BEES, PREFERRED_BY_BEES);
	}

	private void save(TagKey<Item> key, @NotNull PlantopiaTagSet<Item> tagSet) {
		if(tagSet.isEmpty()) return;

		TagsProvider.TagAppender<Item> targetTag = tag(key);

		ArrayList<TagKey<Item>> tags = tagSet.getTags();
		ArrayList<Item> items = tagSet.getElements();

		tags.sort((prev, next) -> idOf(prev).compareTo(idOf(next)));
		items.sort((prev, next) -> idOf(prev).compareTo(idOf(next)));

		for(TagKey<Item> tag : tags) targetTag.addTag(tag);
		for(Item item : items) targetTag.add(item);
	}

	/* HELPER METHODS ******************************************/

	private @NotNull String idOf(@NotNull Item item) {
		return Objects.requireNonNull(item.getRegistryName()).toString();
	}

	private @NotNull String idOf(@NotNull TagKey<Item> tag) {
		return tag.location().toString();
	}
}