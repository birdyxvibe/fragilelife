package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.KillAnimalsMission;
import com.birdy.fragileLife.missions.types.KillMonstersMission;
import com.birdy.fragileLife.missions.types.KillWitchMission;
import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private final ProfileManager profileManager;
    private final TeamManager teamManager;
    public EntityDeathListener(ProfileManager profileManager, TeamManager teamManager) {

        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) { return; }
        if (e.getEntity() instanceof Player) { return; }
        if (e.getEntity() instanceof Monster) {
            Player killer = e.getEntity().getKiller();
            Profile profile = profileManager.getProfile(killer.getUniqueId());
            KillMonstersMission killMonstersMission = new KillMonstersMission();

            killMonstersMission.trigger(profile, teamManager,  killer, 1);

            if(e.getEntity() instanceof Witch){
                KillWitchMission killWitchMission = new KillWitchMission();
                killWitchMission.trigger(profile, teamManager, killer, 1);
            }

        } else if (e.getEntity() instanceof Animals) {
            Player killer = e.getEntity().getKiller();
            Profile profile = profileManager.getProfile(killer.getUniqueId());
            KillAnimalsMission killAnimalsMission = new KillAnimalsMission();

            killAnimalsMission.trigger(profile, teamManager,  killer, 1);
        }
    }
}
