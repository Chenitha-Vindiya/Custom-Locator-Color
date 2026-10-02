package org.trivait.customlocatorcolor.config;

import me.shedaniel.autoconfig.ConfigData;

import java.util.ArrayList;
import java.util.List;

@me.shedaniel.autoconfig.annotation.Config(name = "customlocatorcolor")
public class Config implements ConfigData {

    /** Master switch: when false, every locator keeps its normal color. */
    public boolean enabled = true;

    public List<CustomLocator> customLocators = new ArrayList<>();

    public static class CustomLocator {
        public String name = "Steve";

        /** RGB color, e.g. 0xFF0000 = red. */
        public int color = 0xFFFFFF;

        public CustomLocator() {}

        public CustomLocator(String name, int color) {
            this.name = name;
            this.color = color;
        }
    }
}
