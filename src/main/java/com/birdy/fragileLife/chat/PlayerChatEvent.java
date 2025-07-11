package com.birdy.fragileLife.chat;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {

    private final TeamManager teamManager;
    private final ProfileManager profileManager;

    public PlayerChatEvent(TeamManager teamManager, ProfileManager profileManager){
        this.teamManager = teamManager;
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        NamedTextColor chatColor = teamManager.getPlayerTeamColor(p);

        Profile profile = profileManager.getProfile(p.getUniqueId());
        String color = profile.getChatColor();

        Component message = Component.text(p.getName())
                .color(chatColor)
                .append(Component.text(": ")
                .append(Component.text(e.getMessage()).color(TextColor.fromHexString(color))));

        e.setCancelled(true);
        for (Player recipient : e.getRecipients()) {
            recipient.sendMessage(message);
        }
    }
}
