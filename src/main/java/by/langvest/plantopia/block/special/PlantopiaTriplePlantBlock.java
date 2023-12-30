package by.langvest.plantopia.block.special;

import by.langvest.plantopia.block.PlantopiaBlockStateProperties;
import by.langvest.plantopia.block.PlantopiaTripleBlockHalf;
import by.langvest.plantopia.util.PlantopiaFluidHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlantopiaTriplePlantBlock extends BushBlock {
	public static final EnumProperty<PlantopiaTripleBlockHalf> HALF = PlantopiaBlockStateProperties.TRIPLE_BLOCK_HALF;

	public PlantopiaTriplePlantBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(HALF, PlantopiaTripleBlockHalf.LOWER));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		if(pos.getY() > level.getMaxBuildHeight() - 3) return null;
		if(!level.getBlockState(pos.above(1)).canBeReplaced(context)) return null;
		if(!level.getBlockState(pos.above(2)).canBeReplaced(context)) return null;
		return super.getStateForPlacement(context);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		PlantopiaTripleBlockHalf half = state.getValue(HALF);
		if(half == PlantopiaTripleBlockHalf.LOWER) return super.canSurvive(state, level, pos);
		BlockState stateBelow = level.getBlockState(pos.below());
		return stateBelow.is(this) && stateBelow.getValue(HALF) != PlantopiaTripleBlockHalf.UPPER;
	}

	public static void placeAt(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, int flags) {
		BlockPos posAbove1 = pos.above(1);
		BlockPos posAbove2 = pos.above(2);
		level.setBlock(pos, PlantopiaFluidHelper.copyWaterloggedFrom(level, pos, state.setValue(HALF, PlantopiaTripleBlockHalf.LOWER)), flags);
		level.setBlock(posAbove1, PlantopiaFluidHelper.copyWaterloggedFrom(level, posAbove1, state.setValue(HALF, PlantopiaTripleBlockHalf.CENTRAL)), flags);
		level.setBlock(posAbove2, PlantopiaFluidHelper.copyWaterloggedFrom(level, posAbove2, state.setValue(HALF, PlantopiaTripleBlockHalf.UPPER)), flags);
	}

	@Override
	public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
		BlockPos posAbove1 = pos.above(1);
		BlockPos posAbove2 = pos.above(2);
		level.setBlock(posAbove1, PlantopiaFluidHelper.copyWaterloggedFrom(level, posAbove1, defaultBlockState().setValue(HALF, PlantopiaTripleBlockHalf.CENTRAL)), 3);
		level.setBlock(posAbove2, PlantopiaFluidHelper.copyWaterloggedFrom(level, posAbove2, defaultBlockState().setValue(HALF, PlantopiaTripleBlockHalf.UPPER)), 3);
	}

	@Override
	public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
		PlantopiaTripleBlockHalf half = state.getValue(HALF);
		if(half != PlantopiaTripleBlockHalf.UPPER && direction == Direction.UP && (!neighborState.is(this) || neighborState.getValue(HALF) == half)) return Blocks.AIR.defaultBlockState();
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
		if(!level.isClientSide) {
			if(player.isCreative()) {
				preventCreativeDropFromBottomPart(level, pos, state, player);
			} else {
				dropResources(state, level, pos, null, player, player.getMainHandItem());
			}
		}
		super.playerWillDestroy(level, pos, state, player);
	}

	protected static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, @NotNull BlockState state, Player player) {
		PlantopiaTripleBlockHalf half = state.getValue(HALF);
		if(half == PlantopiaTripleBlockHalf.LOWER) return;
		BlockPos baseBlockPos = getBaseBlockPos(state, pos);
		BlockState baseBlockState = level.getBlockState(baseBlockPos);
		if(baseBlockState.getBlock() != state.getBlock()) return;
		if(baseBlockState.getValue(HALF) != PlantopiaTripleBlockHalf.LOWER) return;
		level.setBlock(baseBlockPos, PlantopiaFluidHelper.getFluidBlockState(level, pos), 35);
		level.levelEvent(player, 2001, baseBlockPos, Block.getId(baseBlockState));
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
		super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), blockEntity, tool);
	}

	@Override
	public @NotNull OffsetType getOffsetType() {
		return OffsetType.XZ;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(HALF);
	}

	public static BlockPos getBaseBlockPos(@NotNull BlockState state, BlockPos pos) {
		return switch(state.getValue(HALF)) {
			case UPPER -> pos.below(2);
			case CENTRAL -> pos.below(1);
			case LOWER -> pos;
		};
	}

	@Override
	@SuppressWarnings("deprecation")
	public long getSeed(@NotNull BlockState state, @NotNull BlockPos pos) {
		return Mth.getSeed(getBaseBlockPos(state, pos));
	}
}