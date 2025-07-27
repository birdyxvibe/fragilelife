package com.birdy.fragileLife.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    private static final LegacyComponentSerializer legacy = LegacyComponentSerializer.legacyAmpersand();

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        for (int i = 0; i < 4; i++) {
            Component line = event.line(i); // This is the Adventure Component the player entered

            if (line == null || line == Component.empty()) continue;
            String content = legacy.serialize(line); // Turn Component into a raw string (includes & codes if typed)
            if (!content.contains("&")) continue; // Skip if no formatting

            Component colored = legacy.deserialize(content); // Re-deserialize using color codes
            event.line(i, colored); // Overwrite with colored version
        }
    }
}
