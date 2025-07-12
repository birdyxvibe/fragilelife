package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.missions.KillMonstersMission;
import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private final ProfileManager profileManager;
    public EntityDeathListener(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) { return; }
        if (e.getEntity() instanceof Player) { return; }
        if (e.getEntity() instanceof Monster) {
            Player killer = e.getEntity().getKiller();
            Profile profile = profileManager.getProfile(killer.getUniqueId());
            KillMonstersMission killMonstersMission = new KillMonstersMission();

            killMonstersMission.trigger(profile, killer, e);
        }
    }
}
