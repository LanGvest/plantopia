package by.langvest.plantopia.util.semantics;

import by.langvest.plantopia.tab.PlantopiaCreativeModeTabs;
import by.langvest.plantopia.util.PlantopiaBlockHighType;
import by.langvest.plantopia.util.PlantopiaRenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class PlantopiaBlockSemantics extends PlantopiaBaseSemantics<RegistryObject<? extends Block>> {
	private final SemanticType type;
	private final CreativeModeTab group;
	private final PlantopiaRenderType renderType;
	private final PlantopiaBlockHighType blockHighType;
	private final int encouragement;
	private final int flammability;
	private final float compostability;
	private final boolean isPottable;
	private final RegistryObject<? extends Block> pottedBy;

	public PlantopiaBlockSemantics(String name, RegistryObject<? extends Block> registryObject, @NotNull SemanticProperties semanticProperties) {
		super(name, registryObject);
		type = semanticProperties.type;
		group = semanticProperties.group;
		renderType = semanticProperties.renderType;
		blockHighType = semanticProperties.blockHighType;
		encouragement = semanticProperties.encouragement;
		flammability = semanticProperties.flammability;
		compostability = semanticProperties.compostability;
		isPottable = semanticProperties.isPottable;
		pottedBy = semanticProperties.pottedBy;

		if(type == SemanticType.POTTED && pottedBy == null) throw new PlantopiaSemanticException.Required("pottedBy", type);
	}

	public Block getBlock() {
		return getObject().get();
	}

	public SemanticType getType() {
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

	public PlantopiaRenderType getRenderType() {
		return renderType;
	}

	public PlantopiaBlockHighType getBlockHighType() {
		return blockHighType;
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

	public static class SemanticType extends PlantopiaSemanticType<SemanticType, SemanticProperties> {
		public static final SemanticType PLANT = new SemanticProperties().cutoutRender().flammable(60, 100).compostable(0.5F).group(PlantopiaCreativeModeTabs.TAB_PLANTOPIA).makeType("plant");
		public static final SemanticType FLOWER = SemanticProperties.of(PLANT).compostable(0.65F).pottable().makeType("flower");
		public static final SemanticType POTTED = new SemanticProperties().cutoutRender().noGroup().makeType("potted");

		private SemanticType(String name, SemanticProperties properties) {
			super("block", name, properties);
		}

		public boolean isOrganic() {
			return type == PLANT || type == FLOWER;
		}

		public boolean isAbleToBePotted() {
			return isOrganic();
		}
	}

	public static class SemanticProperties extends PlantopiaSemanticProperties<SemanticType> implements Cloneable {
		private CreativeModeTab group = null;
		private PlantopiaRenderType renderType = PlantopiaRenderType.NONE;
		private PlantopiaBlockHighType blockHighType = PlantopiaBlockHighType.NORMAL;
		private int encouragement = 0;
		private int flammability = 0;
		private float compostability = 0.0F;
		private boolean isPottable = false;
		private RegistryObject<? extends Block> pottedBy = null;

		private SemanticProperties() {}

		public static @NotNull SemanticProperties of(@NotNull SemanticType semanticType) {
			return semanticType.properties.clone();
		}

		private @NotNull SemanticType makeType(String name) {
			SemanticType semanticType = new SemanticType(name, this);
			type = semanticType;
			return semanticType;
		}

		public SemanticProperties pottedBy(RegistryObject<? extends Block> pottedBy) {
			if(type != null && type != SemanticType.POTTED) throw new PlantopiaSemanticException.UnableToSet("pottedBy", type);
			this.pottedBy = pottedBy;
			return this;
		}

		public SemanticProperties noPottedBy() {
			this.pottedBy = null;
			return this;
		}

		public SemanticProperties group(CreativeModeTab group) {
			this.group = group;
			return this;
		}

		public SemanticProperties noGroup() {
			this.group = null;
			return this;
		}

		public SemanticProperties cutoutRender() {
			this.renderType = PlantopiaRenderType.CUTOUT;
			return this;
		}

		public SemanticProperties translucentRender() {
			this.renderType = PlantopiaRenderType.TRANSLUCENT;
			return this;
		}

		public SemanticProperties noRender() {
			this.renderType = null;
			return this;
		}

		public SemanticProperties flammable(int encouragement, int flammability) {
			this.encouragement = encouragement;
			this.flammability = flammability;
			return this;
		}

		public SemanticProperties notFlammable() {
			this.encouragement = 0;
			this.flammability = 0;
			return this;
		}

		public SemanticProperties pottable() {
			if(type != null && !type.isAbleToBePotted()) throw new PlantopiaSemanticException.UnableToSet("pottable", type);
			this.isPottable = true;
			return this;
		}

		public SemanticProperties notPottable() {
			this.isPottable = false;
			return this;
		}

		public SemanticProperties compostable(float compostability) {
			this.compostability = compostability;
			return this;
		}

		public SemanticProperties notCompostable() {
			this.compostability = 0.0F;
			return this;
		}

		public SemanticProperties normalHigh() {
			this.blockHighType = PlantopiaBlockHighType.NORMAL;
			return this;
		}

		public SemanticProperties doubleHigh() {
			this.blockHighType = PlantopiaBlockHighType.DOUBLE;
			return this;
		}

		public SemanticProperties tripleHigh() {
			this.blockHighType = PlantopiaBlockHighType.TRIPLE;
			return this;
		}

		public SemanticProperties columnHigh() {
			this.blockHighType = PlantopiaBlockHighType.COLUMN;
			return this;
		}

		@Override
		public SemanticProperties clone() {
			try {
				return (SemanticProperties)super.clone();
			} catch(CloneNotSupportedException e) {
				throw new AssertionError();
			}
		}
	}
}