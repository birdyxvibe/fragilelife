package com.birdy.fragileLife.slots;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SlotCommand implements CommandExecutor {

    private final ProfileManager profileManager;

    public SlotCommand(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return false;
        Profile profile = profileManager.getProfile(p.getUniqueId());
        SlotGUI.open(p, profile);
        return true;
    }
}
