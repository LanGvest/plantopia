package by.langvest.plantopia.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface PlantopiaOffsetSeedAccessor {
	long getOffsetSeed(BlockState state, BlockPos pos);
}