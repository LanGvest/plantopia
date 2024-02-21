package by.langvest.plantopia.particle;

import by.langvest.plantopia.Plantopia;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PlantopiaParticleTypes {
	private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Plantopia.MOD_ID);

	public static final RegistryObject<SimpleParticleType> FLUFFY_DANDELION_SEED = registerParticleType("fluffy_dandelion_seed", () -> new SimpleParticleType(false));

	private static <T extends ParticleType<?>> RegistryObject<T> registerParticleType(String name, Supplier<T> supplier) {
		return PARTICLE_TYPE_REGISTER.register(name, supplier);
	}

	public static void setup(IEventBus bus) {
		PARTICLE_TYPE_REGISTER.register(bus);
	}
}