package com.birdy.fragileLife.store;

import com.birdy.fragileLife.managers.ProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StoreCommand implements CommandExecutor {
    private final ProfileManager profileManager;

    public StoreCommand(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(sender instanceof Player player)) return false;
        StoreGUI.open(player, profileManager);
        return true;
    }
}
