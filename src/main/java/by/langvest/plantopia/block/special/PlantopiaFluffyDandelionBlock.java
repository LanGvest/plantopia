package by.langvest.plantopia.block.special;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.particle.PlantopiaParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Supplier;

public class PlantopiaFluffyDandelionBlock extends FlowerBlock {
	public PlantopiaFluffyDandelionBlock(Supplier<MobEffect> effectSupplier, int effectDuration, Properties properties) {
		super(effectSupplier, effectDuration, properties);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Random random) {
		super.animateTick(state, level, pos, random);
		if(random.nextFloat() > 0.1F) return;
		if(level.isRainingAt(pos)) return;
		blowOutSeedParticle(state, level, pos);
	}

	// @Override
	// @SuppressWarnings("deprecation")
	// public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
	// 	Plantopia.LOGGER.info("@@@@@ isClientSide = " + level.isClientSide() + "; entity = " + entity);
	//
	// 	if(!level.isClientSide()) return;
	//
	// 	if(Math.random() > 0.7F) return;
	// 	if(!(entity instanceof LivingEntity)) return;
	// 	if(entity.getType() == EntityType.BEE) return;
	// 	if(entity.isShiftKeyDown()) return;
	// 	if(level.isRainingAt(pos)) return;
	// 	if(entity.xOld == entity.getX() && entity.zOld == entity.getZ()) return;
	// 	double dX = Math.abs(entity.getX() - entity.xOld);
	// 	double dY = Math.abs(entity.getZ() - entity.zOld);
	//  // + Y
	// 	if(dX < 0.14F && dY < 0.14F) return;
	// 	VoxelShape shape = state.getShape(level, pos, CollisionContext.of(entity)).move(pos.getX(), pos.getY(), pos.getZ());
	// 	boolean isColliding = Shapes.joinIsNotEmpty(shape, Shapes.create(entity.getBoundingBox()), BooleanOp.AND);
	// 	if(!isColliding) return;
	// 	blowOutSeedParticle(state, level, pos, true);
	// }

	protected static void blowOutSeedParticle(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		blowOutSeedParticle(state, level, pos, false);
	}

	protected static void blowOutSeedParticle(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, boolean stepped) {
		Vec3 blockOffset = state.getOffset(level, pos);
		level.addParticle(
			PlantopiaParticleTypes.FLUFFY_DANDELION_SEED.get(),
			pos.getX() + 0.5D + blockOffset.x(),
			pos.getY() + 0.5D,
			pos.getZ() + 0.5D + blockOffset.z(),
			0,
			stepped ? -0.26F : 0,
			0
		);
	}
}