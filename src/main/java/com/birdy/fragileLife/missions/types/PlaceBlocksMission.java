package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class PlaceBlocksMission extends Mission {
    private final int targetBlocks;

    public PlaceBlocksMission() {
        super("place_blocks", "Builder","Place 250 Blocks",
                1, Duration.ofHours(8), Material.BRICKS, false);
        this.targetBlocks = 250;
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetBlocks;
    }

    @Override
    public Component getProgressComponent(Profile profile) {
        return Component.text(getProgress(profile), NamedTextColor.WHITE)
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(targetBlocks, NamedTextColor.WHITE));
    }
}
