package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.CatchFishMission;
import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishEventListener implements Listener {

    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    public FishEventListener(ProfileManager profileManager, TeamManager teamManager){
        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onFishCatch(PlayerFishEvent e){
        if(e.getState() == PlayerFishEvent.State.CAUGHT_FISH){
            Player p = e.getPlayer();
            Profile profile = profileManager.getProfile(p.getUniqueId());

            Item caught = (Item)e.getCaught();
            if (caught == null) return;
            ItemStack item = caught.getItemStack();
            if(isFish(item.getType())){
                CatchFishMission catchFishMission = new CatchFishMission();
                catchFishMission.trigger(profile, teamManager, p, 1);
            }
        }
    }

    private boolean isFish(Material m){
        return m == Material.COD || m == Material.SALMON || m == Material.PUFFERFISH || m == Material.TROPICAL_FISH;
    }
}

