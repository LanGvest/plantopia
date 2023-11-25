package by.langvest.plantopia.item;

import by.langvest.plantopia.Plantopia;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PlantopiaItems {
	private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Plantopia.MOD_ID);

	public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> supplier) {
		return ITEM_REGISTER.register(name, supplier);
	}

	public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> blockRegistryObject, CreativeModeTab tab) {
		registerItem(name, () -> new BlockItem(blockRegistryObject.get(), new Item.Properties().tab(tab)));
	}

	public static <T extends Block> void registerDoubleHighBlockItem(String name, RegistryObject<T> blockRegistryObject, CreativeModeTab tab) {
		registerItem(name, () -> new DoubleHighBlockItem(blockRegistryObject.get(), new Item.Properties().tab(tab)));
	}

	public static void setup(IEventBus bus) {
		ITEM_REGISTER.register(bus);
	}
}