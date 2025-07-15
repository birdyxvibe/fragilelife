package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.CollectXPMission;
import com.birdy.fragileLife.schemas.Profile;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PickupExperienceListener implements Listener {

    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    public PickupExperienceListener(ProfileManager profileManager, TeamManager teamManager) {
        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onXPCollect(PlayerPickupExperienceEvent e){
        Player p = e.getPlayer();
        Profile profile = profileManager.getProfile(p.getUniqueId());

        new CollectXPMission().trigger(profile, teamManager, p, e.getExperienceOrb().getExperience());
    }
}
