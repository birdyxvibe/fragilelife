package com.birdy.fragileLife.chat;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NickCommand implements CommandExecutor {

    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    public NickCommand(ProfileManager profileManager, TeamManager teamManager) {
        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(args.length == 0) return false;

        Player p = (Player)sender;
        Profile profile = profileManager.getProfile(p.getUniqueId());

        String newNickname = args[0];

        if(newNickname.equals("off")) {
            profile.setNickname("off");
            p.sendMessage(FragileLife.pluginPrefix
                    .append(Component.text("You've disabled your nickname.", NamedTextColor.GRAY)));
            return true;
        }

        if(newNickname.length() > 16) {
            p.sendMessage(FragileLife.pluginWarningPrefix
                    .append(Component.text("You cannot have a nickname longer than 16 characters.", NamedTextColor.GRAY)));
           return true;
        }


       profile.setNickname(newNickname);
        p.sendMessage(FragileLife.pluginPrefix
                .append(Component.text("You've updated your nickname to: ", NamedTextColor.GRAY))
                .append(Component.text(newNickname, teamManager.getPlayerTeamColor(p))));

        return true;
    }
}
