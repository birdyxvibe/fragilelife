package com.birdy.fragileLife.tags;

import com.birdy.fragileLife.greetings.GreetingGUI;
import com.birdy.fragileLife.managers.ProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TagCommand implements CommandExecutor {

    private final ProfileManager profileManager;

    public TagCommand(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) return false;

        TagGUI.open(player, profileManager);
        return true;
    }
}
