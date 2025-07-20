package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.store.items.HeartStoreItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        HeartStoreItem heartStoreItem = new HeartStoreItem();
        heartStoreItem.addHeart(event);
        if (event.getClickedBlock() == null) return;
        Action action = event.getAction();

        // Disable anvil and enchanting table use
        if (action == Action.RIGHT_CLICK_BLOCK) {
            switch (event.getClickedBlock().getType()) {
                case ANVIL, CHIPPED_ANVIL, DAMAGED_ANVIL -> {
                    event.getPlayer().sendMessage(FragileLife.pluginWarningPrefix
                            .append(Component.text("Anvil use is not allowed.", NamedTextColor.GRAY)));
                    event.setCancelled(true);
                }
                case ENCHANTING_TABLE -> {
                    event.getPlayer().sendMessage(FragileLife.pluginWarningPrefix
                            .append(Component.text("Enchanting table use is not allowed.", NamedTextColor.GRAY)));
                    event.setCancelled(true);
                }
                // add other block types here if needed
                default -> {
                    // Do nothing or handle other block types
                }
            }
        }
    }
}
