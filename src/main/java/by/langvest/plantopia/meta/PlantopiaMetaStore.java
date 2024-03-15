package by.langvest.plantopia.meta;

import by.langvest.plantopia.adv.PlantopiaAdvancement;
import com.google.common.collect.Sets;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class PlantopiaMetaStore {
	private static final Set<PlantopiaBlockMeta> blocks = Sets.newHashSet();
	private static final Set<PlantopiaItemMeta> items = Sets.newHashSet();
	private static final Set<PlantopiaSoundEventMeta> soundEvents = Sets.newHashSet();
	private static final Set<PlantopiaAdvancementMeta> advancements = Sets.newHashSet();

	public static Set<PlantopiaBlockMeta> getBlocks() {
		return blocks;
	}

	public static Set<PlantopiaBlockMeta> getBlocks(Predicate<PlantopiaBlockMeta> predicate) {
		return blocks.stream().filter(predicate).collect(Collectors.toSet());
	}

	public static Set<PlantopiaBlockMeta> getBlocksByType(PlantopiaBlockMeta.MetaType metaType) {
		return blocks.stream().filter(blockSemantics -> blockSemantics.getType().equals(metaType)).collect(Collectors.toSet());
	}

	@Nullable
	public static PlantopiaBlockMeta getBlock(Predicate<PlantopiaBlockMeta> predicate) {
		return blocks.stream().filter(predicate).findFirst().orElse(null);
	}

	@Nullable
	public static PlantopiaBlockMeta getBlock(Block block) {
		return blocks.stream().filter(blockMeta -> blockMeta.getBlock() == block).findFirst().orElse(null);
	}

	@Nullable
	public static PlantopiaBlockMeta getPottedBlock(Block plant) {
		return PlantopiaMetaStore.getBlock(blockMeta -> {
			Block block = blockMeta.getBlock();
			if(!(block instanceof FlowerPotBlock flowerPotBlock)) return false;
			return flowerPotBlock.getContent().equals(plant);
		});
	}

	public static <T extends Block> @NotNull PlantopiaBlockMeta add(String name, RegistryObject<T> object, PlantopiaBlockMeta.MetaProperties metaProperties) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(object);
		Objects.requireNonNull(metaProperties);
		PlantopiaBlockMeta blockMeta = new PlantopiaBlockMeta(name, object, metaProperties);
		blocks.add(blockMeta);
		return blockMeta;
	}

	public static Set<PlantopiaSoundEventMeta> getSoundEvents() {
		return soundEvents;
	}

	public static <T extends SoundEvent> @NotNull PlantopiaSoundEventMeta add(String name, RegistryObject<T> object, PlantopiaSoundEventMeta.MetaProperties metaProperties) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(object);
		Objects.requireNonNull(metaProperties);
		PlantopiaSoundEventMeta soundEventMeta = new PlantopiaSoundEventMeta(name, object, metaProperties);
		soundEvents.add(soundEventMeta);
		return soundEventMeta;
	}

	public static Set<PlantopiaAdvancementMeta> getAdvancements() {
		return advancements;
	}

	@Nullable
	public static PlantopiaAdvancementMeta getAdvancement(PlantopiaAdvancement advancement) {
		return advancements.stream().filter(advancementMeta -> advancementMeta.getAdvancement() == advancement).findFirst().orElse(null);
	}

	public static <T extends PlantopiaAdvancement> @NotNull PlantopiaAdvancementMeta add(String name, T object, PlantopiaAdvancementMeta.MetaProperties metaProperties) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(object);
		Objects.requireNonNull(metaProperties);
		PlantopiaAdvancementMeta advancementMeta = new PlantopiaAdvancementMeta(name, object, metaProperties);
		advancements.add(advancementMeta);
		return advancementMeta;
	}

	public static Set<PlantopiaItemMeta> getItems() {
		return items;
	}

	@Nullable
	public static PlantopiaItemMeta getItem(Item item) {
		return items.stream().filter(itemMeta -> itemMeta.getItem() == item).findFirst().orElse(null);
	}

	public static <T extends Item> @NotNull PlantopiaItemMeta add(String name, RegistryObject<T> object, PlantopiaItemMeta.MetaProperties metaProperties) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(object);
		Objects.requireNonNull(metaProperties);
		PlantopiaItemMeta itemMeta = new PlantopiaItemMeta(name, object, metaProperties);
		items.add(itemMeta);
		return itemMeta;
	}
}