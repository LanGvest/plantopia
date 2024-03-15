package by.langvest.plantopia.meta;

import by.langvest.plantopia.meta.core.PlantopiaMetaAccessor;
import by.langvest.plantopia.meta.core.PlantopiaObjectMeta;
import by.langvest.plantopia.meta.core.PlantopiaObjectMetaProperties;
import by.langvest.plantopia.meta.core.PlantopiaObjectMetaType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class PlantopiaItemMeta extends PlantopiaObjectMeta<RegistryObject<? extends Item>> {
	private final MetaType type;

	public PlantopiaItemMeta(String name, RegistryObject<? extends Item> object, @NotNull MetaProperties metaProperties) {
		super(name, object);
		type = PlantopiaMetaAccessor.getMetaType(metaProperties);
	}

	public Item getItem() {
		return getObject().get();
	}

	public MetaType getType() {
		return type;
	}

	public static final class MetaType extends PlantopiaObjectMetaType<MetaType, MetaProperties> {
		public static final MetaType BLOCK = new MetaProperties().makeType("block");
		public static final MetaType ICON = new MetaProperties().makeType("icon");

		private MetaType(String name, MetaProperties properties) {
			super("item", name, properties);
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