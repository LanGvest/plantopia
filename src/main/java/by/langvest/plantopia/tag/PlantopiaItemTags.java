package by.langvest.plantopia.tag;

import by.langvest.plantopia.util.PlantopiaIdentifier;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class PlantopiaItemTags {
	public static final TagKey<Item> IGNORED_BY_BEES = createItemTag("ignored_by_bees");
	public static final TagKey<Item> PREFERRED_BY_BEES = createItemTag("preferred_by_bees");
	public static final TagKey<Item> NATURE_BLOCKS = createItemTag("nature_blocks");

	private PlantopiaItemTags() {}

	public static @NotNull TagKey<Item> createItemTag(String name) {
		return TagKey.create(Registry.ITEM_REGISTRY, new PlantopiaIdentifier(name));
	}
}