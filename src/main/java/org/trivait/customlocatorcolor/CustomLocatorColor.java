package org.trivait.customlocatorcolor;

import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.sdl.SDLScancode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trivait.customlocatorcolor.config.Config;
import org.trivait.customlocatorcolor.config.ConfigScreens;

public class CustomLocatorColor implements ClientModInitializer {
	public static final String MOD_ID = "customlocatorcolor";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Config CONFIG;

	private static KeyMapping openConfigKey;

	@Override
	public void onInitializeClient() {
		AutoConfig.register(Config.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(Config.class).getConfig();

		KeyMapping.Category category = KeyMapping.Category.register(id("keys"));
		openConfigKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key." + MOD_ID + ".open_config",
				InputConstants.Type.KEYBOARD,
				SDLScancode.SDL_SCANCODE_X,
				category
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openConfigKey.consumeClick()) {
				if (client.gui.screen() == null) {
					client.gui.setScreen(ConfigScreens.create(null));
				}
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
