package by.langvest.plantopia.item;

import by.langvest.plantopia.util.PlantopiaFluidHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PlantopiaTripleHighBlockItem extends BlockItem {
	public PlantopiaTripleHighBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	protected boolean placeBlock(@NotNull BlockPlaceContext context, @NotNull BlockState state) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockPos posAbove1 = pos.above(1);
		BlockPos posAbove2 = pos.above(2);
		level.setBlock(posAbove1, PlantopiaFluidHelper.getFluidBlockState(level, posAbove1), 27);
		level.setBlock(posAbove2, PlantopiaFluidHelper.getFluidBlockState(level, posAbove2), 27);
		return super.placeBlock(context, state);
	}
}