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
            pSender.sendMessage(name + " does not exist.");
            return true;
        }

        Profile profile = profileManager.getProfile(pTarget.getUniqueId());

        switch (type) {
            case "get" -> pSender.sendMessage(name + " has " + profile.getSoulFragments() + " soul fragments.");
            case "add", "remove", "set" -> {
                if (args.length < 3) {
                    return false;
                }

                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    pSender.sendMessage("Please enter a valid number.");
                    return true;
                }

                if (amount < 0) {
                    pSender.sendMessage("Please enter a positive number");
                    return true;
                }

                switch (type) {
                    case "add" -> profile.setSoulFragments(profile.getSoulFragments() + amount);
                    case "remove" -> profile.setSoulFragments(profile.getSoulFragments() - amount);
                    case "set" -> profile.setSoulFragments(amount);
                }
            }
            default -> {
                return false;
            }
        }

        return true;
    }
}
