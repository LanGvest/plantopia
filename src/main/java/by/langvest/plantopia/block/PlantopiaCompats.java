package by.langvest.plantopia.block;

import by.langvest.plantopia.meta.PlantopiaMetaStore;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import org.jetbrains.annotations.NotNull;

public class PlantopiaCompats {
	public static void setup() {
		registerAll();

		registerFlammable(Blocks.OAK_SAPLING, 60, 100);
		registerFlammable(Blocks.SPRUCE_SAPLING, 60, 100);
		registerFlammable(Blocks.BIRCH_SAPLING, 60, 100);
		registerFlammable(Blocks.JUNGLE_SAPLING, 60, 100);
		registerFlammable(Blocks.ACACIA_SAPLING, 60, 100);
		registerFlammable(Blocks.DARK_OAK_SAPLING, 60, 100);
		registerFlammable(Blocks.BAMBOO_SAPLING, 60, 100);
		registerFlammable(Blocks.SUGAR_CANE, 60, 100);
		registerFlammable(Blocks.SEA_PICKLE, 60, 100);
		registerFlammable(Blocks.MOSS_BLOCK, 60, 100);
		registerFlammable(Blocks.MOSS_CARPET, 60, 100);
	}

	private static void registerAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			if(blockMeta.isFlammable()) registerFlammable(blockMeta.getBlock(), blockMeta.getEncouragement(), blockMeta.getFlammability());
			if(blockMeta.isCompostable()) registerCompostable(blockMeta.getBlock(), blockMeta.getCompostability());
		});
	}

	private static void registerFlammable(Block block, int encouragement, int flammability) {
		FireBlock fireBlock = (FireBlock)Blocks.FIRE;
		fireBlock.setFlammable(block, encouragement, flammability);
	}

	public static void registerCompostable(@NotNull ItemLike item, float compostability) {
		ComposterBlock.COMPOSTABLES.put(item.asItem(), compostability);
	}
}