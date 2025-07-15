package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import java.time.Duration;

public class KillAnimalsMission extends Mission {

    private final int targetKills;

    public KillAnimalsMission() {
        super("kill_animals", "Butcher","Kill 50 Passive Mobs",
                1, Duration.ofHours(24), Material.COOKED_CHICKEN, false);
        this.targetKills = 50;
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