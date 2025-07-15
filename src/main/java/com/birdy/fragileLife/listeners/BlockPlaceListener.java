package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.PlaceBlocksMission;
import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    public BlockPlaceListener(ProfileManager profileManager, TeamManager teamManager){
        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Profile profile = profileManager.getProfile(e.getPlayer().getUniqueId());

        PlaceBlocksMission placeBlocksMission = new PlaceBlocksMission();
        placeBlocksMission.trigger(profile, teamManager, e.getPlayer(), 1);

        String blockName = e.getBlock().getType().name();
        if (blockName.endsWith("_LOG") || blockName.endsWith("_ORE")) {
            FragileLife.placedMissionBlockLocations.add(e.getBlock().getLocation());
        }
    }
}