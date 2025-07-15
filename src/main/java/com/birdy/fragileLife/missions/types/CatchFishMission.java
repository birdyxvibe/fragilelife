package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class CatchFishMission extends Mission {

    private final int targetCatches;

    public CatchFishMission() {
        super("catch_fish", "Fisherman","Catch 25 Fish",
                2, Duration.ofHours(24), Material.FISHING_ROD, false);
        this.targetCatches = 25;
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetCatches;
    }

    @Override
    public Component getProgressComponent(Profile profile) {
        return Component.text(getProgress(profile), NamedTextColor.WHITE)
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(targetCatches, NamedTextColor.WHITE));
    }
}