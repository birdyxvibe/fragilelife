package com.birdy.fragileLife.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ProjectileHitListener implements Listener {

    private final Plugin plugin;

    public ProjectileHitListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball snowball)) return;

        NamespacedKey key = new NamespacedKey(plugin, "exploding_snowball");
        PersistentDataContainer container = snowball.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.INTEGER)) {
            Location loc = snowball.getLocation();
            loc.getWorld().createExplosion(loc, 0.6F, false, false);

            // Slow target down
            ThrownPotion potion = loc.getWorld().spawn(loc, ThrownPotion.class);
            PotionMeta meta = (PotionMeta) Bukkit.getItemFactory().getItemMeta(Material.SPLASH_POTION);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOWNESS, 600, 1), true);
            ItemStack item = new ItemStack(Material.SPLASH_POTION);
            item.setItemMeta(meta);
            potion.setItem(item);

            int radius = 3;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    Location checkLoc = loc.clone().add(x, -1, z);
                    Block block = loc.getWorld().getBlockAt(checkLoc);
                    Block above = block.getRelative(BlockFace.UP);

                    if (block.getType().isSolid() && above.getType() == Material.AIR) {
                        above.setType(Material.SNOW); // Places a single snow layer
                    }
                }
            }
        }
    }
}
