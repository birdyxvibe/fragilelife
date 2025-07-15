package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class MineOreMission extends Mission {

    private final int targetBlocks;

    public MineOreMission() {
        super("mine_iron", "Miner","Mine 100 Ore Blocks",
                1, Duration.ofHours(24), Material.IRON_PICKAXE, false);
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