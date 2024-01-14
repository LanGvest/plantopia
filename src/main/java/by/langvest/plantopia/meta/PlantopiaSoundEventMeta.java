package by.langvest.plantopia.meta;

import by.langvest.plantopia.meta.core.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class PlantopiaSoundEventMeta extends PlantopiaObjectMeta<RegistryObject<? extends SoundEvent>> {
	private final MetaType type;

	public PlantopiaSoundEventMeta(String name, RegistryObject<? extends SoundEvent> registryObject, @NotNull MetaProperties metaProperties) {
		super(name, registryObject);
		type = PlantopiaMetaAccessor.getMetaType(metaProperties);
	}

	public SoundEvent getSoundEvent() {
		return getObject().get();
	}

	public MetaType getType() {
		return type;
	}

	public static final class MetaType extends PlantopiaObjectMetaType<MetaType, MetaProperties> {
		public static final MetaType BLOCK_BRAKE = new MetaProperties().makeType("block_break");
		public static final MetaType BLOCK_FOOTSTEPS = new MetaProperties().makeType("block_footsteps");
		public static final MetaType BLOCK_HIT = new MetaProperties().makeType("block_hit");
		public static final MetaType BLOCK_PLACE = new MetaProperties().makeType("block_place");
		public static final MetaType BLOCK_FALL = new MetaProperties().makeType("block_fall");

		private MetaType(String name, MetaProperties properties) {
			super("sound_event", name, properties);
		}
	}

	public static final class MetaProperties extends PlantopiaObjectMetaProperties<MetaType> implements Cloneable {
		private MetaProperties() {}

		public static @NotNull MetaProperties of(@NotNull MetaType metaType) {
			return PlantopiaMetaAccessor.getMetaProperties(metaType).clone();
		}

		private @NotNull MetaType makeType(String name) {
			MetaType metaType = new MetaType(name, this);
			PlantopiaMetaAccessor.setRecursiveMetaType(metaType);
			type = metaType;
			return metaType;
		}

		@Override
		public MetaProperties clone() {
			try {
				return (MetaProperties)super.clone();
			} catch(CloneNotSupportedException e) {
				throw new AssertionError();
			}
		}
	}
}