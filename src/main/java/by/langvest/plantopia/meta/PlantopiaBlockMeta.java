package by.langvest.plantopia.meta;

import by.langvest.plantopia.meta.core.*;
import by.langvest.plantopia.tab.PlantopiaCreativeModeTabs;
import by.langvest.plantopia.meta.property.PlantopiaBlockDropType;
import by.langvest.plantopia.meta.property.PlantopiaBlockHighType;
import by.langvest.plantopia.meta.property.PlantopiaBlockModelType;
import by.langvest.plantopia.meta.property.PlantopiaRenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class PlantopiaBlockMeta extends PlantopiaObjectMeta<RegistryObject<? extends Block>> {
	private final MetaType type;
	private final CreativeModeTab group;
	private final PlantopiaBlockHighType blockHighType;
	private final PlantopiaRenderType renderType;
	private final PlantopiaBlockModelType modelType;
	private final PlantopiaBlockDropType dropType;
	private final int encouragement;
	private final int flammability;
	private final float compostability;
	private final boolean isPottable;
	private final RegistryObject<? extends Block> pottedBy;

	public PlantopiaBlockMeta(String name, RegistryObject<? extends Block> registryObject, @NotNull PlantopiaBlockMeta.MetaProperties metaProperties) {
		super(name, registryObject);
		type = PlantopiaMetaAccessor.getMetaType(metaProperties);
		group = metaProperties.group;
		blockHighType = metaProperties.blockHighType;
		renderType = metaProperties.renderType;
		modelType = metaProperties.modelType;
		dropType = metaProperties.dropType;
		encouragement = metaProperties.encouragement;
		flammability = metaProperties.flammability;
		compostability = metaProperties.compostability;
		isPottable = metaProperties.isPottable;
		pottedBy = metaProperties.pottedBy;

		if(type == MetaType.POTTED && pottedBy == null) throw new PlantopiaMetaException.Required("pottedBy", type);
	}

	public Block getBlock() {
		return getObject().get();
	}

	public MetaType getType() {
		return type;
	}

	@Nullable
	public RegistryObject<? extends Block> getPottedBy() {
		return pottedBy;
	}

	@Nullable
	public CreativeModeTab getGroup() {
		return group;
	}

	public PlantopiaBlockHighType getBlockHighType() {
		return blockHighType;
	}

	public PlantopiaRenderType getRenderType() {
		return renderType;
	}

	public PlantopiaBlockModelType getModelType() {
		return modelType;
	}

	public PlantopiaBlockDropType getDropType() {
		return dropType;
	}

	public int getEncouragement() {
		return encouragement;
	}

	public int getFlammability() {
		return flammability;
	}

	public boolean isFlammable() {
		return encouragement > 0 || flammability > 0;
	}

	public boolean isPottable() {
		return isPottable && blockHighType.getBaseHigh() == 1;
	}

	public float getCompostability() {
		return compostability;
	}

	public boolean isCompostable() {
		return compostability > 0.0F;
	}

	public static final class MetaType extends PlantopiaObjectMetaType<MetaType, MetaProperties> {
		public static final MetaType PLANT = new MetaProperties().cutoutRender().generatedModel().flammable(60, 100).compostable(0.5F).group(PlantopiaCreativeModeTabs.TAB_PLANTOPIA).makeType("plant");
		public static final MetaType FLOWER = MetaProperties.of(PLANT).compostable(0.65F).pottable().makeType("flower");
		public static final MetaType POTTED = new MetaProperties().cutoutRender().generatedModel().noGroup().dropPotted().makeType("potted");

		private MetaType(String name, MetaProperties properties) {
			super("block", name, properties);
		}

		public boolean isOrganic() {
			return type == PLANT || type == FLOWER;
		}

		public boolean isAbleToBePotted() {
			return isOrganic();
		}
	}

	public static final class MetaProperties extends PlantopiaObjectMetaProperties<MetaType> implements Cloneable {
		private CreativeModeTab group = null;
		private PlantopiaBlockHighType blockHighType = PlantopiaBlockHighType.NORMAL;
		private PlantopiaRenderType renderType = PlantopiaRenderType.NONE;
		private PlantopiaBlockModelType modelType = PlantopiaBlockModelType.NONE;
		private PlantopiaBlockDropType dropType = PlantopiaBlockDropType.NONE;
		private int encouragement = 0;
		private int flammability = 0;
		private float compostability = 0.0F;
		private boolean isPottable = false;
		private RegistryObject<? extends Block> pottedBy = null;

		private MetaProperties() {}

		public static @NotNull MetaProperties of(@NotNull MetaType metaType) {
			return PlantopiaMetaAccessor.getMetaProperties(metaType).clone();
		}

		private @NotNull MetaType makeType(String name) {
			MetaType metaType = new MetaType(name, this);
			type = metaType;
			return metaType;
		}

		public MetaProperties pottedBy(RegistryObject<? extends Block> pottedBy) {
			if(type != null && type != MetaType.POTTED) throw new PlantopiaMetaException.UnableToSet("pottedBy", type);
			this.pottedBy = pottedBy;
			return this;
		}

		public MetaProperties noPottedBy() {
			this.pottedBy = null;
			return this;
		}

		public MetaProperties group(CreativeModeTab group) {
			this.group = group;
			return this;
		}

		public MetaProperties noGroup() {
			this.group = null;
			return this;
		}

		public MetaProperties cutoutRender() {
			this.renderType = PlantopiaRenderType.CUTOUT;
			return this;
		}

		public MetaProperties translucentRender() {
			this.renderType = PlantopiaRenderType.TRANSLUCENT;
			return this;
		}

		public MetaProperties noRender() {
			this.renderType = PlantopiaRenderType.NONE;
			return this;
		}

		public MetaProperties flammable(int encouragement, int flammability) {
			this.encouragement = encouragement;
			this.flammability = flammability;
			return this;
		}

		public MetaProperties notFlammable() {
			this.encouragement = 0;
			this.flammability = 0;
			return this;
		}

		public MetaProperties pottable() {
			if(type != null && !type.isAbleToBePotted()) throw new PlantopiaMetaException.UnableToSet("pottable", type);
			this.isPottable = true;
			return this;
		}

		public MetaProperties notPottable() {
			this.isPottable = false;
			return this;
		}

		public MetaProperties compostable(float compostability) {
			this.compostability = compostability;
			return this;
		}

		public MetaProperties notCompostable() {
			this.compostability = 0.0F;
			return this;
		}

		public MetaProperties normalHigh() {
			this.blockHighType = PlantopiaBlockHighType.NORMAL;
			return this;
		}

		public MetaProperties doubleHigh() {
			this.blockHighType = PlantopiaBlockHighType.DOUBLE;
			return this;
		}

		public MetaProperties tripleHigh() {
			this.blockHighType = PlantopiaBlockHighType.TRIPLE;
			return this;
		}

		public MetaProperties columnHigh() {
			this.blockHighType = PlantopiaBlockHighType.COLUMN;
			return this;
		}

		public MetaProperties noModel() {
			this.modelType = PlantopiaBlockModelType.NONE;
			return this;
		}

		public MetaProperties customModel() {
			this.modelType = PlantopiaBlockModelType.CUSTOM;
			return this;
		}

		public MetaProperties generatedModel() {
			this.modelType = PlantopiaBlockModelType.GENERATED;
			return this;
		}

		public MetaProperties noDrop() {
			this.dropType = PlantopiaBlockDropType.NONE;
			return this;
		}

		public MetaProperties customDrop() {
			this.dropType = PlantopiaBlockDropType.CUSTOM;
			return this;
		}

		public MetaProperties dropSelf() {
			this.dropType = PlantopiaBlockDropType.SELF;
			return this;
		}

		public MetaProperties dropSelfByShears() {
			this.dropType = PlantopiaBlockDropType.SELF_BY_SHEARS;
			return this;
		}

		public MetaProperties dropSeedsOrSelfByShears() {
			if(type != null && !type.isOrganic()) throw new PlantopiaMetaException.UnableToSet("dropSeeds", type);
			this.dropType = PlantopiaBlockDropType.SEEDS_OR_SELF_BY_SHEARS;
			return this;
		}

		public MetaProperties dropPotted() {
			if(type != null && type != MetaType.POTTED) throw new PlantopiaMetaException.UnableToSet("dropPotted", type);
			this.dropType = PlantopiaBlockDropType.POTTED;
			return this;
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