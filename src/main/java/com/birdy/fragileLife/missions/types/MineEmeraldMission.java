package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;

public class MineEmeraldMission extends Mission {

    private final int targetBlocks;

    public MineEmeraldMission() {
        super("mine_emerald", "Lucky Miner","Mine 2 Emerald Ore",
                2, Duration.ofHours(24), Material.EMERALD, false);
        this.targetBlocks = 2;
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