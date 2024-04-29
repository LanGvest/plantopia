package by.langvest.plantopia.block;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PlantopiaBlockStateProperties {
	public static final IntegerProperty FLOWER_AMOUNT = IntegerProperty.create("flower_amount", 1, 4);
	public static final IntegerProperty PLANT_AMOUNT = IntegerProperty.create("plant_amount", 1, 4);
	public static final IntegerProperty SHARDS = IntegerProperty.create("shards", 1, 4);
	public static final IntegerProperty POLLINATION_COUNT = IntegerProperty.create("pollination_count", 1, 4);
	public static final EnumProperty<PlantopiaQuarter> QUARTER = EnumProperty.create("quarter", PlantopiaQuarter.class);
	public static final EnumProperty<PlantopiaTripleBlockHalf> TRIPLE_BLOCK_HALF = EnumProperty.create("half", PlantopiaTripleBlockHalf.class);
}