package com.birdy.fragileLife.commands;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SoulFragmentCommand implements CommandExecutor {

    private final ProfileManager profileManager;

    public SoulFragmentCommand(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        Player pSender;
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        pSender = (Player) sender;

        if (!pSender.isOp()) {
            pSender.sendMessage(FragileLife.pluginWarningPrefix
                    .append(Component.text("This command is for operators only.", NamedTextColor.GRAY)));
            return true;
        }

        if (args.length < 2) {
            return false;
        }

        String type = args[0];
        String name = args[1];

        Player pTarget = Bukkit.getPlayer(name);
        if (pTarget == null) {
            pSender.sendMessage(FragileLife.pluginWarningPrefix
                    .append(Component.text(name, NamedTextColor.WHITE))
                    .append(Component.text(" does not exist.", NamedTextColor.RED)));
            return true;
        }

        Profile profile = profileManager.getProfile(pTarget.getUniqueId());
        int soulFragments = profile.getSoulFragments();
        switch (type) {
            case "get" -> {
                pSender.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text(name, NamedTextColor.WHITE))
                        .append(Component.text(" now has ", NamedTextColor.GRAY))
                        .append(Component.text(soulFragments, NamedTextColor.GOLD))
                        .append(Component.text(" soul fragment(s).", NamedTextColor.GRAY)));
                return true;
            }
            case "add", "remove", "set" -> {
                if (args.length < 3) {
                    return false;
                }

                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    pSender.sendMessage(FragileLife.pluginWarningPrefix
                            .append(Component.text("Please enter a valid number.", NamedTextColor.RED)));
                    return true;
                }

                if (amount < 0) {
                    pSender.sendMessage(FragileLife.pluginWarningPrefix
                            .append(Component.text("Please enter a positive number.", NamedTextColor.RED)));;
                    return true;
                }

                switch (type) {
                    case "add" -> {
                        profile.setSoulFragments(soulFragments + amount);
                    }
                    case "remove" -> {
                        profile.setSoulFragments(soulFragments - amount);
                    }
                    case "set" -> {
                        profile.setSoulFragments(amount);
                    }
                }
            }
            default -> {
                return false;
            }
        }
        pSender.sendMessage(FragileLife.pluginPrefix
                .append(Component.text(name, NamedTextColor.WHITE))
                .append(Component.text(" now has ", NamedTextColor.GRAY))
                .append(Component.text(soulFragments, NamedTextColor.GOLD))
                .append(Component.text(" soul fragment(s).", NamedTextColor.GRAY)));
        return true;
    }
}
