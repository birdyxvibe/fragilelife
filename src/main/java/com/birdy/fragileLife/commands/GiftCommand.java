package com.birdy.fragileLife.commands;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class GiftCommand implements CommandExecutor {

    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    public GiftCommand(ProfileManager profileManager, TeamManager teamManager) {
        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            Player recipient = Bukkit.getPlayer(args[0]);

            Profile profile = profileManager.getProfile(p.getUniqueId());
            Date now = new Date();
            Date cooldownEndDate = Date.from(Instant.parse(profile.getGiftCooldown()));

            // Ensure command not on cooldown
            if (now.before(cooldownEndDate)) {
                p.sendMessage(FragileLife.pluginWarningPrefix
                        .append(Component.text("You may use this command again in ").color(NamedTextColor.GRAY))
                        .append(Component.text(formatHoursUntil(cooldownEndDate)).color(NamedTextColor.YELLOW)));
                return true;
            }

            if (recipient == null) {
                p.sendMessage(FragileLife.pluginWarningPrefix
                        .append(Component.text("Player ").color(NamedTextColor.GRAY))
                        .append(Component.text(args[0]).color(NamedTextColor.DARK_GRAY))
                        .append(Component.text(" is not online.").color(NamedTextColor.GRAY)));
                return true;
            }


            NamedTextColor pColor = teamManager.getPlayerTeamColor(p);
            NamedTextColor rColor = teamManager.getPlayerTeamColor(recipient);

            if (pColor == NamedTextColor.GRAY) {
                p.sendMessage(FragileLife.pluginWarningPrefix
                        .append(Component.text("Dead players cannot gift.").color(NamedTextColor.GRAY)));
                return true;
            }

            if (rColor == NamedTextColor.GRAY) {
                p.sendMessage(FragileLife.pluginWarningPrefix
                        .append(Component.text("You cannot gift to dead players.").color(NamedTextColor.GRAY)));
                return true;
            }

            // Ensure they have less than 29 hearts
            double recipientHealth = recipient.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
            if (recipientHealth > 58) {
                    p.sendMessage(FragileLife.pluginWarningPrefix
                            .append(Component.text("Player ").color(NamedTextColor.GRAY))
                            .append(Component.text(args[0]).color(rColor))
                            .append(Component.text(" already has 30").color(NamedTextColor.GRAY))
                            .append(Component.text(" ♥").color(NamedTextColor.RED)));
                    return true;
            }

            // Gift heart
            recipient.getAttribute(Attribute.MAX_HEALTH).setBaseValue(recipientHealth + 2);
            p.sendMessage(FragileLife.pluginPrefix
                    .append(Component.text("You've gifted one").color(NamedTextColor.GRAY))
                    .append(Component.text(" ♥ ").color(NamedTextColor.RED))
                    .append(Component.text("to ").color(NamedTextColor.GRAY))
                    .append(Component.text(args[0]).color(rColor)));

            recipient.sendMessage(FragileLife.pluginPrefix
                    .append(Component.text(p.getName()).color(pColor))
                    .append(Component.text(" gifted you one").color(NamedTextColor.GRAY))
                    .append(Component.text(" ♥").color(NamedTextColor.RED)));

            // Set Cooldown
            Instant cooldownEnd = Instant.now().plus(8, ChronoUnit.HOURS);
            profile.setGiftCooldown(cooldownEnd.toString());


            return true;
        }
        return false;
    }

    private static String formatHoursUntil(Date futureDate){
        long now = System.currentTimeMillis();
        long diffMilliseconds = futureDate.getTime() - now;

        if(diffMilliseconds <= 0){
            return "now";
        }

        double hours = diffMilliseconds / 3600000.0;
        return String.format("%.2f hours", hours);
    }
}
