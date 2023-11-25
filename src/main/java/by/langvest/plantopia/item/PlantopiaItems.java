package by.langvest.plantopia.item;

import by.langvest.plantopia.Plantopia;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class PlantopiaItems {
	private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Plantopia.MOD_ID);

	public static <T extends Item> void registerItem(String name, Supplier<T> supplier) {
		ITEM_REGISTER.register(name, supplier);
	}

	public static void setup(IEventBus bus) {
		ITEM_REGISTER.register(bus);
	}
}