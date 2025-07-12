package com.birdy.fragileLife.missions;


import net.kyori.adventure.text.Component;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MissionGUIListener implements Listener {
    private static final String GUI_TITLE = "Missions";

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (!e.getView().title().equals(Component.text(GUI_TITLE))) return;

        e.setCancelled(true);

    }
}
