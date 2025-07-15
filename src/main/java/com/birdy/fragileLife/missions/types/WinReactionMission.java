package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.time.Duration;

public class WinReactionMission extends Mission {
    private final int targetWins;

    public WinReactionMission() {
        super("win_reactions", "Typer","Win 3 Reactions",
                1, Duration.ofHours(12), Material.CLOCK, false);
        this.targetWins = 3;
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetWins;
    }

    @Override
    public Component getProgressComponent(Profile profile) {
        return Component.text(getProgress(profile), NamedTextColor.WHITE)
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(targetWins, NamedTextColor.WHITE));
    }
}
