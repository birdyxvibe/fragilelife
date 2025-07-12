package com.birdy.fragileLife.missions;

import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public abstract class Mission {

    protected final String id;
    protected final String name;
    protected final String desc;
    protected final Duration cooldownDuration;

    public Mission(String id, String name, String desc, Duration cooldownDuration) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.cooldownDuration = cooldownDuration;
    }

    public String getId() { return id; }

    public abstract void trigger(Profile profile, Player player, Object context);

    public boolean isOnCooldown(Profile profile) {
        String cooldownStr = profile.getMissionCooldowns(id);
        if (cooldownStr.isEmpty()) return false;
        long expiresAt = Instant.parse(cooldownStr).toEpochMilli();
        return System.currentTimeMillis() < expiresAt;
    }

    public void startCooldown(Profile profile) {
        Instant nextAvailable = Instant.now().plus(cooldownDuration);
        profile.setMissionCooldowns(id, nextAvailable.toString());
    }

    public String getCooldown(Profile profile) {
        String cooldownStr = profile.getMissionCooldowns(id);
        if (cooldownStr.isEmpty()) return "";
        Instant cooldownInstant = Instant.parse(cooldownStr);


        return formatHoursUntil(cooldownInstant);
    }

    private static String formatHoursUntil(Instant futureInstant){
        Instant now = Instant.now();
        long diffMillis  =  futureInstant.toEpochMilli()- now.toEpochMilli();

        if(diffMillis <= 0){
            return "now";
        }

        long totalMinutes = diffMillis / (60 * 1000);
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append(" hour").append(hours != 1 ? "s" : "");
        }
        if (minutes > 0) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(minutes).append(" minute").append(minutes != 1 ? "s" : "");
        }
        return sb.toString();
    }

    public int getProgress(Profile profile) { return profile.getMissionProgress(id); }

    public void setProgress(Profile profile, int value) {
        profile.setMissionProgress(id, value);
    }

    public void resetProgress(Profile profile) {
        profile.setMissionProgress(id, 0);
    }

    public abstract boolean isComplete(Profile profile);

    public abstract String getProgressDisplay(Profile profile);
}
