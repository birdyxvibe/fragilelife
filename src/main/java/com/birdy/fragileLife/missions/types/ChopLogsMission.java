package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class ChopLogsMission extends Mission {

    private final int targetLogs;

    public ChopLogsMission() {
        super("chop_logs", "Lumberjack","Chop 128 Logs (any type)",
                1, Duration.ofHours(24), Material.IRON_AXE, false);
        this.targetLogs = 128;
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetLogs;
    }

    @Override
    public Component getProgressComponent(Profile profile) {
        return Component.text(getProgress(profile), NamedTextColor.WHITE)
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(targetLogs, NamedTextColor.WHITE));
    }
}