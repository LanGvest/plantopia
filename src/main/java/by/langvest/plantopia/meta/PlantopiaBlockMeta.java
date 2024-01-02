package by.langvest.plantopia.meta;

import by.langvest.plantopia.block.PlantopiaCompats.*;
import by.langvest.plantopia.meta.core.*;
import by.langvest.plantopia.meta.property.*;
import by.langvest.plantopia.tab.PlantopiaCreativeModeTabs;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
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
	private final PlantopiaDisplayNameType displayNameType;
	private final PlantopiaTintType tintType;
	private final int encouragement;
	private final int flammability;
	private final float compostability;
	private final boolean isPottable;
	private final boolean isIgnoredByBees;
	private final boolean isPreferredByBees;
	private final boolean hasTintedParticles;

	public PlantopiaBlockMeta(String name, RegistryObject<? extends Block> registryObject, @NotNull PlantopiaBlockMeta.MetaProperties metaProperties) {
		super(name, registryObject);
		type = PlantopiaMetaAccessor.getMetaType(metaProperties);
		group = metaProperties.group;
		blockHighType = metaProperties.blockHighType;
		renderType = metaProperties.renderType;
		modelType = metaProperties.modelType;
		dropType = metaProperties.dropType;
		displayNameType = metaProperties.displayNameType;
		tintType = metaProperties.tintType;
		encouragement = metaProperties.encouragement;
		flammability = metaProperties.flammability;
		compostability = metaProperties.compostability;
		isPottable = metaProperties.isPottable;
		isIgnoredByBees = metaProperties.isIgnoredByBees;
		isPreferredByBees = metaProperties.isPreferredByBees;
		hasTintedParticles = metaProperties.hasTintedParticles;
	}

	public Block getBlock() {
		return getObject().get();
	}

	public Material getMaterial() {
		return getBlock().material;
	}

	public MetaType getType() {
		return type;
	}

	public boolean isIgnoredByBees() {
		return isIgnoredByBees;
	}

	public boolean isPreferredByBees() {
		return isPreferredByBees;
	}

	@Nullable
	public CreativeModeTab getGroup() {
		return group;
	}

	public boolean hasItem() {
		return group != null;
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

	public PlantopiaDisplayNameType getDisplayNameType() {
		return displayNameType;
	}

	public PlantopiaTintType getTintType() {
		return tintType;
	}

	public boolean isTinted() {
		return tintType != PlantopiaTintType.NONE;
	}

	public boolean hasDrop() {
		return dropType != PlantopiaBlockDropType.NONE;
	}

	public boolean shouldGenerateModel() {
		return modelType != PlantopiaBlockModelType.NONE && modelType != PlantopiaBlockModelType.CUSTOM;
	}

	public boolean shouldGenerateLootTable() {
		return dropType != PlantopiaBlockDropType.NONE && dropType != PlantopiaBlockDropType.CUSTOM;
	}

	public boolean shouldGenerateTranslation() {
		return hasItem() && displayNameType != PlantopiaDisplayNameType.NONE && displayNameType != PlantopiaDisplayNameType.CUSTOM;
	}

	public boolean shouldApplyTint() {
		return tintType != PlantopiaTintType.NONE && tintType != PlantopiaTintType.CUSTOM;
	}

	public boolean shouldApplyTintToParticles() {
		return shouldApplyTint() && hasTintedParticles;
	}

	public boolean shouldApplyRenderLayer() {
		return renderType != PlantopiaRenderType.NONE;
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
		return hasItem() && isPottable && blockHighType.getBaseHigh() == 1;
	}

	public float getCompostability() {
		return compostability;
	}

	public boolean isCompostable() {
		return hasItem() && compostability > 0.0F;
	}

	public static final class MetaType extends PlantopiaObjectMetaType<MetaType, MetaProperties> {
		public static final MetaType PLANT = new MetaProperties().cutoutRender().flammable(Encouragement.PLANT, Flammability.PLANT).compostable(Compostability.PLANT_1).group(PlantopiaCreativeModeTabs.TAB_PLANTOPIA).makeType("plant");
		public static final MetaType FLOWER = MetaProperties.of(PLANT).pottable().compostable(Compostability.FLOWER).makeType("flower");
		public static final MetaType SAPLING = MetaProperties.of(PLANT).pottable().makeType("sapling");
		public static final MetaType MUSHROOM = MetaProperties.of(PLANT).pottable().notFlammable().compostable(Compostability.MUSHROOM).makeType("mushroom");
		public static final MetaType MUSHROOM_STEM = new MetaProperties().compostable(Compostability.MUSHROOM_STEM).group(PlantopiaCreativeModeTabs.TAB_PLANTOPIA).makeType("mushroom_stem");
		public static final MetaType MUSHROOM_BLOCK = MetaProperties.of(MUSHROOM_STEM).compostable(Compostability.MUSHROOM_BLOCK).makeType("mushroom_block");
		public static final MetaType POTTED = new MetaProperties().cutoutRender().notTintedParticles().noGroup().makeType("potted");
		public static final MetaType LEAVES = new MetaProperties().cutoutMippedRender().group(PlantopiaCreativeModeTabs.TAB_PLANTOPIA).makeType("leaves");

		private MetaType(String name, MetaProperties properties) {
			super("block", name, properties);
		}

		public boolean isPlant() {
			return type == PLANT
				|| type == FLOWER
				|| type == SAPLING
				|| type == MUSHROOM;
		}

		public boolean isMushroom() {
			return type == MUSHROOM
				|| type == MUSHROOM_STEM
				|| type == MUSHROOM_BLOCK;
		}

		public boolean isAbleToBePotted() {
			return isPlant();
		}
	}

	public static final class MetaProperties extends PlantopiaObjectMetaProperties<MetaType> implements Cloneable {
		private CreativeModeTab group = null;
		private PlantopiaBlockHighType blockHighType = PlantopiaBlockHighType.NORMAL;
		private PlantopiaRenderType renderType = PlantopiaRenderType.NONE;
		private PlantopiaBlockModelType modelType = PlantopiaBlockModelType.GENERATED;
		private PlantopiaBlockDropType dropType = PlantopiaBlockDropType.GENERATED;
		private PlantopiaDisplayNameType displayNameType = PlantopiaDisplayNameType.GENERATED;
		private PlantopiaTintType tintType = PlantopiaTintType.NONE;
		private int encouragement = 0;
		private int flammability = 0;
		private float compostability = 0.0F;
		private boolean isPottable = false;
		private boolean isIgnoredByBees = false;
		private boolean isPreferredByBees = false;
		private boolean hasTintedParticles = true;

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

		public MetaProperties tintedParticles() {
			this.hasTintedParticles = true;
			return this;
		}

		public MetaProperties notTintedParticles() {
			this.hasTintedParticles = false;
			return this;
		}

		public MetaProperties noDisplayName() {
			this.displayNameType = PlantopiaDisplayNameType.NONE;
			return this;
		}

		public MetaProperties customDisplayName() {
			this.displayNameType = PlantopiaDisplayNameType.CUSTOM;
			return this;
		}

		public MetaProperties generatedDisplayName() {
			this.displayNameType = PlantopiaDisplayNameType.GENERATED;
			return this;
		}

		public MetaProperties ignoredByBees() {
			this.isIgnoredByBees = true;
			return this;
		}

		public MetaProperties notIgnoredByBees() {
			this.isIgnoredByBees = false;
			return this;
		}

		public MetaProperties preferredByBees() {
			this.isPreferredByBees = true;
			return this;
		}

		public MetaProperties notPreferredByBees() {
			this.isPreferredByBees = false;
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

		public MetaProperties cutoutMippedRender() {
			this.renderType = PlantopiaRenderType.CUTOUT_MIPPED;
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
			this.encouragement = Math.max(encouragement, 0);
			this.flammability = Math.max(flammability, 0);
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
			this.compostability = Mth.clamp(compostability, 0.0F, 1.0F);
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

		public MetaProperties generatedDrop() {
			this.dropType = PlantopiaBlockDropType.GENERATED;
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

		public MetaProperties noTint() {
			this.tintType = PlantopiaTintType.NONE;
			return this;
		}

		public MetaProperties customTint() {
			this.tintType = PlantopiaTintType.CUSTOM;
			return this;
		}

		public MetaProperties pottedTint(PlantopiaTintType tintType) {
			if(type != null && type != MetaType.POTTED) throw new PlantopiaMetaException.UnableToSet("pottedTint", type);
			this.tintType = tintType;
			return this;
		}

		public MetaProperties grassTint() {
			this.tintType = PlantopiaTintType.GRASS;
			return this;
		}

		public MetaProperties foliageTint() {
			this.tintType = PlantopiaTintType.FOLIAGE;
			return this;
		}

		public MetaProperties rainbowTint() {
			this.tintType = PlantopiaTintType.RAINBOW;
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