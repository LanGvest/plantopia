package by.langvest.plantopia.adv;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class PlantopiaAdvancement {
	private Advancement instance = null;
	private final Advancement.Builder builder;

	public PlantopiaAdvancement() {
		this.builder = Advancement.Builder.advancement();
	}

	public Advancement.Builder getBuilder() {
		return builder;
	}

	@Nullable
	public Advancement getInstance() {
		return instance;
	}

	public PlantopiaAdvancement apply(@NotNull Consumer<PlantopiaAdvancement> consumer) {
		consumer.accept(this);
		return this;
	}

	public void save(@NotNull Consumer<Advancement> consumer, ResourceLocation location) {
		instance = builder.build(location);
		consumer.accept(instance);
	}
}