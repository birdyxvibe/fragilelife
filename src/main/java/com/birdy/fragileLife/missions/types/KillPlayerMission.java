package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;

public class KillPlayerMission extends Mission {

    private final int targetKills;

    public KillPlayerMission() {
        super("kill_player", "Murderer","Kill 1 Players",
                -1, Duration.ofHours(24), Material.NETHERITE_SWORD, true);
        this.targetKills = 1;
    }

    @Override
    public void trigger(Profile profile, TeamManager teamManager, Player player, int increment) {
        if (player == null) return;
        if (teamManager.getPlayerTeamColor(player) == NamedTextColor.GRAY) return;
        if (isRedOnly() && teamManager.getPlayerTeamColor(player) != NamedTextColor.RED) return;
        if (isOnCooldown(profile)) return;

        int progress = getProgress(profile);
        setProgress(profile, progress + increment);

        if (isComplete(profile)) {
            // Give reward
            player.sendMessage(FragileLife.pluginPrefix
                    .append(Component.text(name, NamedTextColor.AQUA))
                    .append(Component.text(" mission complete! You are back on your", NamedTextColor.GRAY))
                    .append(Component.text(" 2nd life", NamedTextColor.YELLOW)));

            teamManager.assignPlayerToTeam(player, "Yellow");

            startCooldown(profile);
            resetProgress(profile);
        }
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetKills;
    }

    @Override
    public Component getProgressComponent(Profile profile) {
        return Component.text(getProgress(profile), NamedTextColor.WHITE)
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(targetKills, NamedTextColor.WHITE));
    }

}