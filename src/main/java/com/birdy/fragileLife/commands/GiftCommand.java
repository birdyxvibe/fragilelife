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

            NamedTextColor pColor = teamManager.getPlayerTeamColor(p);
            NamedTextColor rColor = teamManager.getPlayerTeamColor(recipient);

            if (pColor == NamedTextColor.GRAY) {
                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("Dead players cannot gift.").color(NamedTextColor.GRAY)));
                return true;
            }

            if (rColor == NamedTextColor.GRAY) {
                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("You cannot gift to dead players.").color(NamedTextColor.GRAY)));
                return true;
            }

            Profile profile = profileManager.getProfile(p.getUniqueId());
            Date now = new Date();

            // Ensure command not on cooldown
            if (now.before(profile.getGiftCooldown())) {
                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("This command is on cooldown until").color(NamedTextColor.GRAY))
                        .append(Component.text(profile.getGiftCooldown().toString()).color(NamedTextColor.YELLOW)));
                return true;
            }

            if (recipient == null) {
                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("Player ").color(NamedTextColor.GRAY))
                        .append(Component.text(args[0]).color(NamedTextColor.YELLOW))
                        .append(Component.text(" is not online.").color(NamedTextColor.GRAY)));
                return true;
            }

            // Ensure they have less than 29 hearts
            double recipientHealth = recipient.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
            if (recipientHealth > 58) {
                    p.sendMessage(FragileLife.pluginPrefix
                            .append(Component.text("Player ").color(NamedTextColor.GRAY))
                            .append(Component.text(args[0]).color(NamedTextColor.YELLOW))
                            .append(Component.text(" already has 30").color(NamedTextColor.GRAY))
                            .append(Component.text(" ♥.").color(NamedTextColor.RED)));
                    return true;
            }

            // Gift heart
            recipient.getAttribute(Attribute.MAX_HEALTH).setBaseValue(recipientHealth + 2);
            p.sendMessage(FragileLife.pluginPrefix
                    .append(Component.text("You've gifted one").color(NamedTextColor.GRAY))
                    .append(Component.text(" ♥ ").color(NamedTextColor.RED))
                    .append(Component.text("to ").color(NamedTextColor.GRAY))
                    .append(Component.text(args[0]).color(NamedTextColor.YELLOW)));

            // Set Cooldown
            Instant cooldownEnd = Instant.now().plus(8, ChronoUnit.HOURS);
            profile.setGiftCooldown(Date.from(cooldownEnd));


            return true;
        }
        return false;
    }
}
