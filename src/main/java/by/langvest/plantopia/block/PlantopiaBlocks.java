package by.langvest.plantopia.block;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.item.PlantopiaItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PlantopiaBlocks {
	private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Plantopia.MOD_ID);

	public static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> supplier) {
		return BLOCK_REGISTER.register(name, supplier);
	}

	public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> supplier, CreativeModeTab tab) {
		RegistryObject<T> blockRegistryObject = registerBlockWithoutItem(name, supplier);
		PlantopiaItems.registerBlockItem(name, blockRegistryObject, tab);
		return blockRegistryObject;
	}

	public static <T extends Block> RegistryObject<T> registerDoubleHighBlock(String name, Supplier<T> supplier, CreativeModeTab tab) {
		RegistryObject<T> blockRegistryObject = registerBlockWithoutItem(name, supplier);
		PlantopiaItems.registerDoubleHighBlockItem(name, blockRegistryObject, tab);
		return blockRegistryObject;
	}

	public static void setup(IEventBus bus) {
		BLOCK_REGISTER.register(bus);
	}
}