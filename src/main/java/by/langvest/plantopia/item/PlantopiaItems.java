package by.langvest.plantopia.item;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlantopiaItems {
	private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Plantopia.MOD_ID);

	public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> supplier) {
		return ITEM_REGISTER.register(name, supplier);
	}

	public static void registerBlockItem(@NotNull PlantopiaBlockMeta blockMeta) {
		CreativeModeTab group = blockMeta.getGroup();
		if(group == null) return;
		Properties properties = new Properties().tab(group);
		registerItem(blockMeta.getName(), () -> switch(blockMeta.getBlockHeightType()) {
			case DOUBLE -> new DoubleHighBlockItem(blockMeta.getBlock(), properties);
			case TRIPLE -> new PlantopiaTripleHighBlockItem(blockMeta.getBlock(), properties);
			default -> new BlockItem(blockMeta.getBlock(), properties);
		});
	}

	public static void setup(IEventBus bus) {
		ITEM_REGISTER.register(bus);
	}
}