package org.trivait.customlocatorcolor.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@me.shedaniel.autoconfig.annotation.Config(name = "customlocatorcolor")
public class Config implements ConfigData {

    public List<CustomLocator> customLocators = new ArrayList<>();

    public static class CustomLocator {
        public String name = "steve";

        @ConfigEntry.ColorPicker(allowAlpha = false)
        public int color = 0xFFFFFF;

        public CustomLocator() {}

        public CustomLocator(String name, int color) {
            this.name = name;
            this.color = color;
        }
    }
}
