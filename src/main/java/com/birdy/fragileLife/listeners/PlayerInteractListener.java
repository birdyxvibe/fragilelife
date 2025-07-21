package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.store.items.HeartStoreItem;
import com.birdy.fragileLife.store.items.HorcruxStoreItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class PlayerInteractListener implements Listener {

    private final Plugin plugin;
    private final ProfileManager profileManager;

    public PlayerInteractListener(Plugin plugin, ProfileManager profileManager) {
        this.plugin = plugin;
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        HeartStoreItem heartStoreItem = new HeartStoreItem();
        heartStoreItem.addHeart(event);

        HorcruxStoreItem horcruxStoreItem = new HorcruxStoreItem();
        horcruxStoreItem.beginHorcruxRitual(event, plugin, profileManager);


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
