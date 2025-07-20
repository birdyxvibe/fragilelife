package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.store.items.ReviveTotemStoreItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityResurrectListener implements Listener {

    @EventHandler
    public void onTotemUse(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;

        ReviveTotemStoreItem reviveTotemStoreItem = new ReviveTotemStoreItem();
        ItemMeta totemMeta = reviveTotemStoreItem.generateItem().getItemMeta();

        ItemStack main = p.getInventory().getItemInMainHand();
        ItemStack off = p.getInventory().getItemInOffHand();

        if ((main.hasItemMeta() && totemMeta.equals(main.getItemMeta())) ||
                (off.hasItemMeta() && totemMeta.equals(off.getItemMeta()))) {
            event.setCancelled(true);
        }
    }
}
