package by.langvest.plantopia.block;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.block.special.*;
import by.langvest.plantopia.block.PlantopiaCompats.Compostability;
import by.langvest.plantopia.item.PlantopiaItems;
import by.langvest.plantopia.meta.store.PlantopiaMetaStore;
import by.langvest.plantopia.meta.object.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.object.PlantopiaBlockMeta.MetaProperties;
import by.langvest.plantopia.meta.object.PlantopiaBlockMeta.MetaType;
import by.langvest.plantopia.meta.property.PlantopiaTintType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static by.langvest.plantopia.util.PlantopiaContentHelper.*;

public class PlantopiaBlocks {
	private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Plantopia.MOD_ID);

	public static final RegistryObject<Block> FIREWEED = registerBlock("fireweed", () -> new PlantopiaFireweedBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.PLANT).doubleHigh().customModel().customTint().dropSelfByShears().preferredByBees().compostable(Compostability.PLANT_2 + Compostability.HAS_FLOWERS));
	public static final RegistryObject<Block> GIANT_GRASS = registerBlock("giant_grass", () -> new PlantopiaTriplePlantBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.PLANT).tripleHigh().grassTint().customDrop().compostable(Compostability.PLANT_3));
	public static final RegistryObject<Block> GIANT_FERN = registerBlock("giant_fern", () -> new PlantopiaTriplePlantBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.PLANT).tripleHigh().customModel().grassTint().customDrop().compostable(Compostability.PLANT_3));
	public static final RegistryObject<Block> CLOVER = registerBlock("clover", () -> new PlantopiaCloverBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.AZALEA)), MetaProperties.of(MetaType.PLANT).customModel().grassTint().customDrop().compostable(Compostability.PLANT_1 * 0.75F));
	public static final RegistryObject<Block> BIG_CLOVER = registerBlock("big_clover", () -> new PlantopiaBigCloverBlock(Properties.of(Material.PLANT).instabreak().sound(SoundType.AZALEA)), MetaProperties.of(MetaType.PLANT).customModel().grassTint().pottable());
	public static final RegistryObject<Block> WHITE_CLOVER_BLOSSOM = registerBlock("white_clover_blossom", () -> new PlantopiaCloverBlossomBlock(() -> MobEffects.LUCK, 14, Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.AZALEA)), MetaProperties.of(MetaType.FLOWER).customModel().grassTint().dye(Items.LIGHT_GRAY_DYE));
	public static final RegistryObject<Block> PINK_CLOVER_BLOSSOM = registerBlock("pink_clover_blossom", () -> new PlantopiaCloverBlossomBlock(() -> MobEffects.LUCK, 14, Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.AZALEA)), MetaProperties.of(MetaType.FLOWER).customModel().grassTint().dye(Items.PINK_DYE));
	public static final RegistryObject<Block> COBBLESTONE_SHARD = registerBlock("cobblestone_shard", () -> new PlantopiaCobblestoneShardBlock(Properties.of(Material.DECORATION).strength(0.2F).sound(SoundType.DRIPSTONE_BLOCK)), MetaProperties.of(MetaType.STONE).customModel().customDrop());
	public static final RegistryObject<Block> MOSSY_COBBLESTONE_SHARD = registerBlock("mossy_cobblestone_shard", () -> new PlantopiaCobblestoneShardBlock(Properties.of(Material.DECORATION).strength(0.2F).sound(SoundType.DRIPSTONE_BLOCK)), MetaProperties.of(MetaType.STONE).customModel().customDrop());
	public static final RegistryObject<Block> BUSH = registerBlock("bush", () -> new PlantopiaBushBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.PLANT).customModel().foliageTint().customDrop().pottable());
	public static final RegistryObject<Block> RED_FOXGLOVE = registerBlock("red_foxglove", () -> new PlantopiaFoxgloveBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.RED_DYE));
	public static final RegistryObject<Block> ORANGE_FOXGLOVE = registerBlock("orange_foxglove", () -> new PlantopiaFoxgloveBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.ORANGE_DYE));
	public static final RegistryObject<Block> YELLOW_FOXGLOVE = registerBlock("yellow_foxglove", () -> new PlantopiaFoxgloveBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.YELLOW_DYE));
	public static final RegistryObject<Block> WHITE_FOXGLOVE = registerBlock("white_foxglove", () -> new PlantopiaFoxgloveBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.WHITE_DYE));
	public static final RegistryObject<Block> PINK_FOXGLOVE = registerBlock("pink_foxglove", () -> new PlantopiaFoxgloveBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.PINK_DYE));
	public static final RegistryObject<Block> MAGENTA_FOXGLOVE = registerBlock("magenta_foxglove", () -> new PlantopiaFoxgloveBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.MAGENTA_DYE));
	public static final RegistryObject<Block> RED_HOLLYHOCK = registerBlock("red_hollyhock", () -> new TallFlowerBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.RED_DYE));
	public static final RegistryObject<Block> ORANGE_HOLLYHOCK = registerBlock("orange_hollyhock", () -> new TallFlowerBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.ORANGE_DYE));
	public static final RegistryObject<Block> YELLOW_HOLLYHOCK = registerBlock("yellow_hollyhock", () -> new TallFlowerBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.YELLOW_DYE));
	public static final RegistryObject<Block> WHITE_HOLLYHOCK = registerBlock("white_hollyhock", () -> new TallFlowerBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.WHITE_DYE));
	public static final RegistryObject<Block> PINK_HOLLYHOCK = registerBlock("pink_hollyhock", () -> new TallFlowerBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.PINK_DYE));
	public static final RegistryObject<Block> MAGENTA_HOLLYHOCK = registerBlock("magenta_hollyhock", () -> new TallFlowerBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).doubleHigh().customModel().dye(Items.MAGENTA_DYE));
	public static final RegistryObject<Block> POLLINATED_DANDELION = registerBlock("pollinated_dandelion", () -> new PlantopiaPollinatedDandelionBlock(Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).customModel().customDrop().noGroup());
	public static final RegistryObject<Block> FLUFFY_DANDELION = registerBlock("fluffy_dandelion", () -> new PlantopiaFluffyDandelionBlock(() -> MobEffects.SLOW_FALLING, 7, Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)), MetaProperties.of(MetaType.FLOWER).ignoredByBees().noDye());

	static {
		registerPottedBlocks();
	}

	public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> supplier, MetaProperties metaProperties) {
		RegistryObject<T> registryObject = BLOCK_REGISTER.register(name, supplier);
		PlantopiaBlockMeta blockMeta = PlantopiaMetaStore.add(name, registryObject, metaProperties);
		PlantopiaItems.registerBlockItem(blockMeta);
		return registryObject;
	}

	@SuppressWarnings("UnusedReturnValue")
	public static RegistryObject<FlowerPotBlock> registerPottedBlock(String plantName, Supplier<? extends Block> plantSupplier, PlantopiaTintType plantTintType) {
		return registerBlock(pottedNameOf(plantName), () -> new FlowerPotBlock(() -> FLOWER_POT_BLOCK, plantSupplier, Properties.of(Material.DECORATION).instabreak()), MetaProperties.of(MetaType.POTTED).pottedTint(plantTintType));
	}

	private static void registerPottedBlocks() {
		registerPottedBlock(nameOf(Blocks.GRASS), () -> Blocks.GRASS, PlantopiaTintType.GRASS);

		PlantopiaMetaStore.getBlocks(PlantopiaBlockMeta::isPottable).forEach(blockMeta ->
			registerPottedBlock(blockMeta.getName(), blockMeta.getObject(), blockMeta.getTintType())
		);
	}

	public static void setup(IEventBus bus) {
		BLOCK_REGISTER.register(bus);
	}
}