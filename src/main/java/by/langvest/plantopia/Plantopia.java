package by.langvest.plantopia;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(Plantopia.MOD_ID)
public class Plantopia {
	public static final String MOD_ID = "plantopia";

	@SuppressWarnings("unused")
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public Plantopia() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
	}

	private void commonSetup(final @NotNull FMLCommonSetupEvent event) {}

	private void clientSetup(final @NotNull FMLClientSetupEvent event) {}
}