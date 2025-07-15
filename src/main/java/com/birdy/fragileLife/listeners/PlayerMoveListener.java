package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.WalkDistanceMission;
import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    public PlayerMoveListener(ProfileManager profileManager, TeamManager teamManager){
        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();

        if(from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) return;

        Profile profile = profileManager.getProfile(p.getUniqueId());
        WalkDistanceMission walkDistanceMission = new WalkDistanceMission();
        walkDistanceMission.trigger(profile, teamManager, p, 1);
    }
}
