package by.langvest.plantopia.block;

import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlantopiaCompats {
	public static void setup() {
		registerAll();

		registerFlammable(Blocks.OAK_SAPLING, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.SPRUCE_SAPLING, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.BIRCH_SAPLING, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.JUNGLE_SAPLING, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.ACACIA_SAPLING, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.DARK_OAK_SAPLING, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.BAMBOO_SAPLING, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.SUGAR_CANE, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.SEA_PICKLE, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.MOSS_BLOCK, Encouragement.PLANT, Flammability.PLANT);
		registerFlammable(Blocks.MOSS_CARPET, Encouragement.PLANT, Flammability.PLANT);
	}

	private static void registerAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			Block block = blockMeta.getBlock();

			if(blockMeta.isFlammable()) registerFlammable(blockMeta.getBlock(), blockMeta.getEncouragement(), blockMeta.getFlammability());
			if(blockMeta.isCompostable()) registerCompostable(blockMeta.getBlock(), blockMeta.getCompostability());
			if(block instanceof FlowerPotBlock) registerPotted(blockMeta);
		});
	}

	private static void registerFlammable(Block block, int encouragement, int flammability) {
		FireBlock fireBlock = (FireBlock)Blocks.FIRE;
		fireBlock.setFlammable(block, encouragement, flammability);
	}

	public static void registerCompostable(@NotNull ItemLike item, float compostability) {
		ComposterBlock.COMPOSTABLES.put(item.asItem(), compostability);
	}

	public static void registerPotted(@NotNull PlantopiaBlockMeta blockMeta) {
		FlowerPotBlock flowerPotBlock = (FlowerPotBlock)Blocks.FLOWER_POT;
		FlowerPotBlock block = (FlowerPotBlock)blockMeta.getBlock();
		flowerPotBlock.addPlant(Objects.requireNonNull(block.getContent().getRegistryName()), blockMeta.getObject());
	}

	@SuppressWarnings("unused")
	public static final class Compostability {
		public static final float CHANCE_30 = 0.3F;
		public static final float CHANCE_50 = 0.5F;
		public static final float CHANCE_60 = 0.6F;
		public static final float CHANCE_65 = 0.65F;
		public static final float CHANCE_85 = 0.85F;
		public static final float CHANCE_100 = 1.0F;
		public static final float PLANT_1 = CHANCE_30;
		public static final float PLANT_2 = CHANCE_50;
		public static final float PLANT_3 = CHANCE_60;
		public static final float FLOWER = CHANCE_65;
		public static final float MUSHROOM = CHANCE_65;
		public static final float MUSHROOM_STEM = CHANCE_65;
		public static final float MUSHROOM_BLOCK = CHANCE_85;
		public static final float HAS_FLOWERS = 0.05F;
	}

	@SuppressWarnings("unused")
	public static final class Encouragement {
		public static final int PLANT = 60;
	}

	@SuppressWarnings("unused")
	public static final class Flammability {
		public static final int PLANT = 100;
	}
}