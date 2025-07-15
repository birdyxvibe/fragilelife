package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class WalkDistanceMission extends Mission {
    private final int targetDistance;

    public WalkDistanceMission() {
        super("walk_distance", "Marathon","Walk 2500 Blocks",
                1, Duration.ofHours(6), Material.CHAINMAIL_BOOTS, false);
        this.targetDistance = 2500;
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetDistance;
    }

    @Override
    public Component getProgressComponent(Profile profile) {
        return Component.text(getProgress(profile), NamedTextColor.WHITE)
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(targetDistance, NamedTextColor.WHITE));
    }
}
