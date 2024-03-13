package by.langvest.plantopia.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class PlantopiaTickHelper {
	private static int clientTick = 0;
	private static int inGameTick = 0;

	public static int getClientTick() {
		return clientTick;
	}

	public static int getInGameTick() {
		return inGameTick;
	}

	public static int getPlayerTick() {
		return Objects.requireNonNull(Minecraft.getInstance().player).tickCount;
	}

	public static void tick() {
		clientTick++;
		boolean isPaused = Minecraft.getInstance().screen instanceof PauseScreen && Minecraft.getInstance().isPaused();
		if(!isPaused) inGameTick++;
	}
}