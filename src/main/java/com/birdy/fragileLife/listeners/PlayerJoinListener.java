package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class PlayerJoinListener implements Listener {

    private final FragileLife plugin;
    private final TeamManager teamManager;

    public PlayerJoinListener(FragileLife plugin, TeamManager teamManager){
        this.plugin = plugin;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        if (!p.hasPlayedBefore()) {
            p.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30 * 2);
            p.setHealth(30 * 2);

            Bukkit.getScheduler().runTask(plugin, () -> {
                teamManager.assignPlayerToTeam(p, "green");
            });
        }
    }
}
