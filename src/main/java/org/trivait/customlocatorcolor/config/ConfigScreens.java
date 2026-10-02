package org.trivait.customlocatorcolor.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.MultiElementListEntry;
import me.shedaniel.clothconfig2.gui.entries.NestedListListEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.trivait.customlocatorcolor.CustomLocatorColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Builds the settings screen. Used by both Mod Menu and the in-game hotkey. */
public final class ConfigScreens {
    private static final String K = "customlocatorcolor.config.";

    private ConfigScreens() {}

    public static Screen create(Screen parent) {
        Config cfg = CustomLocatorColor.CONFIG;

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable(K + "title"))
                .setSavingRunnable(() -> AutoConfig.getConfigHolder(Config.class).save());

        ConfigEntryBuilder eb = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable(K + "category"));

        general.addEntry(eb.startTextDescription(Component.translatable(K + "description")).build());

        general.addEntry(eb.startBooleanToggle(Component.translatable(K + "enabled"), cfg.enabled)
                .setDefaultValue(true)
                .setTooltip(Component.translatable(K + "enabled.tooltip"))
                .setSaveConsumer(v -> cfg.enabled = v)
                .build());

        NestedListListEntry<Config.CustomLocator, MultiElementListEntry<Config.CustomLocator>> players =
                new NestedListListEntry<>(
                        Component.translatable(K + "players"),
                        cfg.customLocators,
                        true,
                        () -> Optional.of(new Component[]{Component.translatable(K + "players.tooltip")}),
                        newList -> cfg.customLocators = new ArrayList<>(newList),
                        ArrayList::new,
                        eb.getResetButtonKey(),
                        true,   // show delete button
                        true,   // new entries appear at the top
                        (elem, self) -> createCell(eb, elem == null ? new Config.CustomLocator() : elem)
                );
        general.addEntry(players);

        return builder.build();
    }

    private static MultiElementListEntry<Config.CustomLocator> createCell(ConfigEntryBuilder eb, Config.CustomLocator loc) {
        AbstractConfigListEntry<?> name = eb.startStrField(Component.translatable(K + "player.name"), loc.name)
                .setDefaultValue("Steve")
                .setTooltip(Component.translatable(K + "player.name.tooltip"))
                .setErrorSupplier(v -> v.trim().isEmpty()
                        ? Optional.of(Component.translatable(K + "player.name.empty"))
                        : Optional.empty())
                .setSaveConsumer(v -> loc.name = v.trim())
                .build();

        AbstractConfigListEntry<?> color = eb.startColorField(Component.translatable(K + "player.color"), loc.color)
                .setDefaultValue(0xFFFFFF)
                .setTooltip(Component.translatable(K + "player.color.tooltip"))
                .setSaveConsumer(v -> loc.color = v & 0xFFFFFF)
                .build();

        List<AbstractConfigListEntry<?>> children = new ArrayList<>();
        children.add(name);
        children.add(color);

        Component title = loc.name == null || loc.name.isBlank()
                ? Component.translatable(K + "player.new")
                : Component.literal(loc.name);
        return new MultiElementListEntry<>(title, loc, children, true);
    }
}
