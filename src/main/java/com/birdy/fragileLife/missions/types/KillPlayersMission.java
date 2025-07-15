package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import java.time.Duration;

public class KillPlayersMission extends Mission {

    private final int targetKills;

    public KillPlayersMission() {
        super("kill_player", "Murderer","Kill 2 Players",
                10, Duration.ofHours(8), Material.NETHERITE_SWORD, true);
        this.targetKills = 1;
    }

    @Override
    public boolean isComplete(Profile profile) {
        return getProgress(profile) >= targetKills;
    }

    @Override
    public Component getProgressComponent(Profile profile) {
        return Component.text(getProgress(profile), NamedTextColor.WHITE)
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(targetKills, NamedTextColor.WHITE));
    }

}