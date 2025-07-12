package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;

public class MineIronMission extends Mission {

    private final int targetBlocks;

    public MineIronMission() {
        super("mine_iron", "Blacksmith","Mine 100 Iron Ore",
                1, Duration.ofHours(24), Material.IRON_INGOT, false);
        this.targetBlocks = 100;
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