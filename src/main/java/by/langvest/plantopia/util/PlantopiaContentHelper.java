package by.langvest.plantopia.util;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.meta.object.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.store.PlantopiaMetaStore;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PlantopiaContentHelper {
	public static final String POTTED_PREFIX = "potted_";
	public static final FlowerPotBlock FLOWER_POT_BLOCK = (FlowerPotBlock)Blocks.FLOWER_POT;
	public static final FireBlock FIRE_BLOCK = (FireBlock)Blocks.FIRE;
	public static final Object2FloatMap<ItemLike> COMPOSTABLES = ComposterBlock.COMPOSTABLES;
	private static List<Block> ALL_FLOWERS = null;
	private static List<Block> ALL_FLOWERS_ORDERED_BY_ID = null;

	public static List<Block> getAllFlowers() {
		if(ALL_FLOWERS != null) return ALL_FLOWERS;
		Set<Block> allFlowers = Sets.newLinkedHashSet();

		allFlowers.add(Blocks.FLOWERING_AZALEA);
		allFlowers.add(Blocks.FLOWERING_AZALEA_LEAVES);
		allFlowers.add(Blocks.SPORE_BLOSSOM);
		allFlowers.add(Blocks.CHORUS_FLOWER);

		ForgeRegistries.BLOCKS.getEntries().stream().filter(blockEntry -> {
			Block block = blockEntry.getValue();
			PlantopiaBlockMeta blockMeta = PlantopiaMetaStore.getBlock(block);
			if(blockMeta != null) return blockMeta.hasItem() && blockMeta.getType().isFlowerLike();
			return block instanceof FlowerBlock || block instanceof TallFlowerBlock;
		}).forEach(blockEntry -> allFlowers.add(blockEntry.getValue()));

		return ALL_FLOWERS = allFlowers.stream().toList();
	}

	public static List<Block> getAllFlowersOrderedById() {
		if(ALL_FLOWERS_ORDERED_BY_ID != null) return ALL_FLOWERS_ORDERED_BY_ID;
		return ALL_FLOWERS_ORDERED_BY_ID = getAllFlowers().stream().sorted(Comparator.comparing(PlantopiaContentHelper::idOf)).toList();
	}

	@Nullable
	public static Block pottedBlockOf(Block plant) {
		Supplier<? extends Block> supplier = FLOWER_POT_BLOCK.getFullPotsView().get(locationOf(plant));
		if(supplier != null) return supplier.get();

		PlantopiaBlockMeta pottedBlockMeta = PlantopiaMetaStore.getBlock(blockMeta -> {
			Block block = blockMeta.getBlock();
			if(!(block instanceof FlowerPotBlock flowerPotBlock)) return false;
			return flowerPotBlock.getContent().equals(plant);
		});

		if(pottedBlockMeta != null) return pottedBlockMeta.getBlock();
		return null;
	}

	@Contract(pure = true)
	public static @NotNull String pottedNameOf(String baseName) {
		return POTTED_PREFIX + baseName;
	}

	public static @NotNull String pottedNameOf(Block plant) {
		String baseName = nameOf(plant);
		return pottedNameOf(baseName);
	}

	public static @NotNull ResourceLocation locationOf(@NotNull RegistryObject<?> registryObject) {
		return registryObject.getId();
	}

	public static <T extends IForgeRegistryEntry<?>> @NotNull ResourceLocation locationOf(@NotNull T object) {
		return Objects.requireNonNull(object.getRegistryName());
	}

	public static @NotNull ResourceLocation locationOf(@NotNull TagKey<?> tag) {
		return tag.location();
	}

	public static @NotNull String idOf(@NotNull RegistryObject<?> registryObject) {
		return locationOf(registryObject).toString();
	}

	public static <T extends IForgeRegistryEntry<?>> @NotNull String idOf(@NotNull T object) {
		return locationOf(object).toString();
	}

	public static @NotNull String idOf(@NotNull TagKey<?> tag) {
		return locationOf(tag).toString();
	}

	public static @NotNull String nameOf(@NotNull RegistryObject<?> registryObject) {
		return locationOf(registryObject).getPath();
	}

	public static <T extends IForgeRegistryEntry<?>> @NotNull String nameOf(@NotNull T object) {
		return locationOf(object).getPath();
	}

	public static @NotNull String nameOf(@NotNull TagKey<?> tag) {
		return locationOf(tag).getPath();
	}
}