package com.birdy.fragileLife.missions;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import java.time.Duration;

public class KillPlayersMission extends Mission {

    private final int targetKills;

    public KillPlayersMission() {
        super("kill_player", "Murderer","Eliminate 1 Player", Duration.ofHours(24));
        this.targetKills = 1;
    }

    @Override
    public void trigger(Profile profile, Player player, Object context) {
        if (player == null) return;
        if (isOnCooldown(profile)) return;

        int progress = getProgress(profile);
        setProgress(profile, progress + 1);

        player.sendMessage(FragileLife.pluginPrefix
                .append(Component.text(name, NamedTextColor.AQUA))
                .append(Component.text(" mission progress: ", NamedTextColor.GRAY))
                .append(Component.text(progress + 1, NamedTextColor.AQUA))
                .append(Component.text("/", NamedTextColor.GRAY))
                .append(Component.text(targetKills, NamedTextColor.AQUA)));

        if (isComplete(profile)) {
            // Give reward
            player.sendMessage(FragileLife.pluginPrefix
                    .append(Component.text("You have completed the ", NamedTextColor.GRAY))
                    .append(Component.text(name, NamedTextColor.AQUA))
                    .append(Component.text(" mission! +10", NamedTextColor.GRAY))
                    .append(Component.text("♥", NamedTextColor.RED)));

            // Reward
            double currentHP = player.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
            player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(currentHP + 10 * 2);

            startCooldown(profile);
            resetProgress(profile);
        }
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetKills;
    }

    @Override
    public String getProgressDisplay(Profile profile) {
        return getProgress(profile) + " / " + targetKills + " player kills";
    }
}