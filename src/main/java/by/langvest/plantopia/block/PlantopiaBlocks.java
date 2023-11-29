package by.langvest.plantopia.block;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.item.PlantopiaItems;
import by.langvest.plantopia.util.semantics.PlantopiaSemanticRegistry;
import by.langvest.plantopia.util.semantics.PlantopiaBlockSemantics;
import by.langvest.plantopia.util.semantics.PlantopiaBlockSemantics.SemanticProperties;
import by.langvest.plantopia.util.semantics.PlantopiaBlockSemantics.SemanticType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PlantopiaBlocks {
	private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Plantopia.MOD_ID);

	static {
		registerPottedBlocks();
	}

	public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> supplier, SemanticProperties semanticProperties) {
		RegistryObject<T> blockRegistryObject = BLOCK_REGISTER.register(name, supplier);
		PlantopiaBlockSemantics blockSemantics = PlantopiaSemanticRegistry.register(name, blockRegistryObject, semanticProperties);
		PlantopiaItems.registerBlockItem(blockSemantics);
		return blockRegistryObject;
	}

	private static void registerPottedBlocks() {
		PlantopiaSemanticRegistry.getBlocks(PlantopiaBlockSemantics::isPottable).forEach(blockSemantics ->
			registerBlock("potted_" + blockSemantics.getName(), () -> new FlowerPotBlock(null, blockSemantics.getObject(), Properties.of(Material.DECORATION).instabreak()), SemanticProperties.of(SemanticType.POTTED).pottedBy(blockSemantics.getObject()))
		);
	}

	public static void setup(IEventBus bus) {
		BLOCK_REGISTER.register(bus);
	}
}