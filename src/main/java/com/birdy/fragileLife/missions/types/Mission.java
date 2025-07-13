package com.birdy.fragileLife.missions.types;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

public abstract class Mission {

    protected final String id;
    protected final String name;
    protected final String desc;
    protected final Duration cooldownDuration;
    protected final Material guiMaterial;
    protected final int reward;
    protected final boolean isRedOnly;

    public Mission(String id, String name, String desc, int reward, Duration cooldownDuration, Material guiMaterial, boolean isRedOnly) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.reward =reward;
        this.cooldownDuration = cooldownDuration;
        this.guiMaterial = guiMaterial;
        this.isRedOnly = isRedOnly;
    }

    public void trigger(Profile profile, TeamManager teamManager, Player player, Object context) {
        if (player == null) return;
        if (teamManager.getPlayerTeamColor(player) == NamedTextColor.GRAY) return;
        if (isRedOnly() && isRedTeam(teamManager, player)) return;
        if (isOnCooldown(profile)) return;

        int progress = getProgress(profile);
        setProgress(profile, progress + 1);

        if (isComplete(profile)) {
            // Give reward
            sendRewardMessage(player);
            profile.setSoulFragments(profile.getSoulFragments() + reward);

            startCooldown(profile);
            resetProgress(profile);
        }
    }

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

    public boolean isRedTeam(TeamManager teamManager, Player p) {
        return (teamManager.getPlayerTeamColor(p) == NamedTextColor.RED);
    }

    public void sendRewardMessage(Player p){
        p.sendMessage(FragileLife.pluginPrefix
                .append(Component.text(name, NamedTextColor.AQUA))
                .append(Component.text(" mission complete! +", NamedTextColor.GRAY))
                .append(Component.text(reward, NamedTextColor.DARK_RED))
                .append(Component.text(" Soul Fragment(s)", NamedTextColor.GRAY)));
    }

    public String getId() { return id; }
    public Material getGuiMaterial() { return guiMaterial; }
    public String getName() {return name; }
    public String getDesc() { return desc; }
    public int getReward() {return reward; }
    public boolean isRedOnly() { return isRedOnly; }


    public abstract boolean isComplete(Profile profile);
    public abstract Component getProgressComponent(Profile profile);
}
