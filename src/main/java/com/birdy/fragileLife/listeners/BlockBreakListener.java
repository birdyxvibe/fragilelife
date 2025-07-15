package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.*;
import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private final ProfileManager profileManager;
    private final TeamManager teamManager;
    public BlockBreakListener(ProfileManager profileManager, TeamManager teamManager) {

        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Profile profile = profileManager.getProfile(p.getUniqueId());
        
        Material block = e.getBlock().getType();

        if(FragileLife.placedMissionBlockLocations.contains(e.getBlock().getLocation())){
            FragileLife.placedMissionBlockLocations.remove(e.getBlock().getLocation());
            return;
        }

        if (block.name().endsWith("_ORE")) {
            MineOreMission mineOreMission = new MineOreMission();
            mineOreMission.trigger(profile, teamManager, p, 1);

            if (block == Material.DIAMOND_ORE || block == Material.DEEPSLATE_DIAMOND_ORE) {
                MineDiamondsMission mineDiamondsMission = new MineDiamondsMission();
                mineDiamondsMission.trigger(profile, teamManager, p, 1);
            }
        } else if (block.name().endsWith("_LOG")){
            ChopLogsMission chopLogsMission = new ChopLogsMission();
            chopLogsMission.trigger(profile, teamManager, p, 1);
        }
    }
}
