package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class KillWitchMission extends Mission {
    private final int targetKills;

    public KillWitchMission() {
        super("kill_witch", "Witch Hunt","Kill 1 Witch",
                3, Duration.ofHours(24), Material.SPLASH_POTION, false);
        this.targetKills = 1;
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
