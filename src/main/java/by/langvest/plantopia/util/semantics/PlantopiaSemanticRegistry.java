package by.langvest.plantopia.util.semantics;

import com.google.common.collect.Sets;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class PlantopiaSemanticRegistry {
	private static final Set<PlantopiaBlockSemantics> blocks = Sets.newHashSet();

	public static Set<PlantopiaBlockSemantics> getBlocks() {
		return blocks;
	}

	public static Set<PlantopiaBlockSemantics> getBlocks(Predicate<PlantopiaBlockSemantics> predicate) {
		return blocks.stream().filter(predicate).collect(Collectors.toSet());
	}

	public static Set<PlantopiaBlockSemantics> getBlocksByType(PlantopiaBlockSemantics.SemanticType semanticType) {
		return blocks.stream().filter(blockSemantics -> blockSemantics.getType().equals(semanticType)).collect(Collectors.toSet());
	}

	@Nullable
	public static PlantopiaBlockSemantics getBlock(Predicate<PlantopiaBlockSemantics> predicate) {
		return blocks.stream().filter(predicate).findFirst().orElse(null);
	}

	public static <T extends Block> @NotNull PlantopiaBlockSemantics register(String name, RegistryObject<T> object, PlantopiaBlockSemantics.SemanticProperties semanticProperties) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(object);
		Objects.requireNonNull(semanticProperties);
		PlantopiaBlockSemantics blockSemantics = new PlantopiaBlockSemantics(name, object, semanticProperties);
		blocks.add(blockSemantics);
		return blockSemantics;
	}
}