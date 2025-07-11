package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.TeamManager;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final TeamManager teamManager;

    public PlayerDeathListener(TeamManager teamManager){
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        String currentTeam = teamManager.getPlayerTeam(p).getName();


        switch (currentTeam.toLowerCase()) {
            case "green" -> {
                teamManager.assignPlayerToTeam(p, "yellow");
                p.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30 * 2);
            }
            case "yellow" -> {
                teamManager.assignPlayerToTeam(p, "red");
                p.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30 * 2);
            }
            case "red" -> {
                teamManager.assignPlayerToTeam(p, "ghost");
                p.setGameMode(GameMode.SPECTATOR);
                e.setCancelled(true);

                World w = e.getPlayer().getWorld();
                w.strikeLightningEffect(e.getPlayer().getLocation());
            }
        }
    }
}
