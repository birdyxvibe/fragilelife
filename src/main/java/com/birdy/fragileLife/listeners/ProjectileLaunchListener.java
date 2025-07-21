package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.store.items.CryoChargeStoreItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class ProjectileLaunchListener implements Listener {

    private final Plugin plugin;

    public ProjectileLaunchListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Snowball snowball)) return;

        if (!(snowball.getShooter() instanceof Player player)) return;

        // Check if the item in hand is your special snowball
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.SNOWBALL || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        CryoChargeStoreItem cryoChargeStoreItem = new CryoChargeStoreItem();
        if (meta == cryoChargeStoreItem.generateItem().getItemMeta()) return;
        // Tag the snowball entity with metadata
        PersistentDataContainer container = snowball.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "exploding_snowball");
        container.set(key, PersistentDataType.INTEGER, 1);
    }
}
