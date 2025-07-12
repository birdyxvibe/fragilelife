package com.birdy.fragileLife.missions;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.greetings.GreetingGUI;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MissionCommand implements CommandExecutor {

    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    public MissionCommand(ProfileManager profileManager, TeamManager teamManager) {
        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) return false;
        if (teamManager.getPlayerTeamColor(player) == NamedTextColor.GRAY) {
            player.sendMessage(FragileLife.pluginWarningPrefix
                    .append(Component.text("Ghost's are not allowed to use this command.", NamedTextColor.GRAY)));
            return true;
        }
        MissionGUI.open(player, profileManager, teamManager);
        return true;
    }
}
