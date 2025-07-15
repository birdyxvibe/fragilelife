package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class MineDiamondsMission extends Mission {

    private final int targetBlocks;

    public MineDiamondsMission() {
        super("mine_diamonds", "Diamond Digger","Mine 5 Diamond Ore",
                2, Duration.ofHours(24), Material.DIAMOND, false);
        this.targetBlocks = 5;
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