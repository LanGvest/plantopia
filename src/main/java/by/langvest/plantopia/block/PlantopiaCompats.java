package by.langvest.plantopia.block;

import by.langvest.plantopia.meta.PlantopiaMetaStore;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;

public class PlantopiaCompats {
	public static void setup() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			if(blockMeta.isFlammable()) registerFlammable(blockMeta.getBlock(), blockMeta.getEncouragement(), blockMeta.getFlammability());
			if(blockMeta.isCompostable()) registerCompostable(blockMeta.getBlock().asItem(), blockMeta.getCompostability());
		});
	}

	private static void registerFlammable(Block block, int encouragement, int flammability) {
		FireBlock fireBlock = (FireBlock)Blocks.FIRE;
		fireBlock.setFlammable(block, encouragement, flammability);
	}

	public static void registerCompostable(ItemLike item, float compostability) {
		ComposterBlock.COMPOSTABLES.put(item, compostability);
	}
}