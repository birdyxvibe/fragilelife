package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class CollectXPMission extends Mission {
    private final int targetXP;

    public CollectXPMission() {
        super("collect_xp", "Orb Collector","Collect 500 Experience",
                1, Duration.ofHours(24), Material.EXPERIENCE_BOTTLE, false);
        this.targetXP = 500;
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetXP;
    }

    @Override
    public Component getProgressComponent(Profile profile) {
        return Component.text(getProgress(profile), NamedTextColor.WHITE)
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(targetXP, NamedTextColor.WHITE));
    }
}
