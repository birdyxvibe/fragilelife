package com.birdy.fragileLife.chat;

import com.birdy.fragileLife.managers.ProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatCommand implements CommandExecutor {

    private final ProfileManager profileManager;
    public ChatCommand(ProfileManager profileManager){
        this.profileManager = profileManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return false;

        ChatGUI.open(p, profileManager);
        return true;
    }
}
