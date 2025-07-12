package com.birdy.fragileLife.greetings;

import com.birdy.fragileLife.managers.ProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GreetingCommand implements CommandExecutor {
    private final ProfileManager profileManager;

    public GreetingCommand(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        GreetingGUI.open(player, profileManager);
        return true;
    }
}
