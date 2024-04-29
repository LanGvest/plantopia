package by.langvest.plantopia.mixin;

import by.langvest.plantopia.block.PlantopiaOffsetSeedAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class PlantopiaBlockBehaviour$BlockStateBaseMixin {
	@Redirect(
		method = "getOffset(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/Vec3;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/Mth;getSeed(III)J"
		)
	)
	private long getOffset(int x, int y, int z, BlockGetter level, BlockPos pos) {
		BlockBehaviour.BlockStateBase that = (BlockBehaviour.BlockStateBase)(Object)this;
		Block block = that.getBlock();
		if(block instanceof PlantopiaOffsetSeedAccessor accessor) return accessor.getOffsetSeed(level.getBlockState(pos), pos);
		return Mth.getSeed(x, y, z);
	}
}