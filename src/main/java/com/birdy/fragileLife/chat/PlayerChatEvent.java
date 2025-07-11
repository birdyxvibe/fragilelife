package com.birdy.fragileLife.chat;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatEvent implements Listener {

    private final TeamManager teamManager;
    private final ProfileManager profileManager;

    public PlayerChatEvent(TeamManager teamManager, ProfileManager profileManager){
        this.teamManager = teamManager;
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e){
        Player p = e.getPlayer();

        NamedTextColor chatColor = teamManager.getPlayerTeamColor(p);

        Profile profile = profileManager.getProfile(p.getUniqueId());
        String color = profile.getChatColor();

        Component chatMessage = e.message().color(TextColor.fromHexString(color))
                .decoration(TextDecoration.BOLD, profile.isChatBold())
                .decoration(TextDecoration.ITALIC, profile.isChatItalic());

        Component message = Component.text(p.getName())
                .color(chatColor)
                .append(Component.text(": ")
                .append(chatMessage));

        e.setCancelled(true);
        for (Audience recipient : e.viewers()) {
            recipient.sendMessage(message);
        }
    }
}
