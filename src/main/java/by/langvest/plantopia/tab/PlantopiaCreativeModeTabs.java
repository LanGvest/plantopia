package by.langvest.plantopia.tab;

import by.langvest.plantopia.Plantopia;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PlantopiaCreativeModeTabs {
	public static final CreativeModeTab TAB_PLANTOPIA = new CreativeModeTab(Plantopia.MOD_ID) {
		@Override
		@Contract(" -> new")
		public @NotNull ItemStack makeIcon() {
			return new ItemStack(Blocks.DIRT);
		}
	};
}