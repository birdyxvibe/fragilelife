package com.birdy.fragileLife.chat;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class PlayerChatEvent {

    private final AsyncChatEvent e;
    private final TeamManager teamManager;
    private final ProfileManager profileManager;

    public PlayerChatEvent(AsyncChatEvent e, TeamManager teamManager, ProfileManager profileManager) {
        this.e = e;
        this.teamManager = teamManager;
        this.profileManager = profileManager;
    }

    public void playerChatEvent(){
        Player p = e.getPlayer();

        NamedTextColor chatColor = teamManager.getPlayerTeamColor(p);

        Profile profile = profileManager.getProfile(p.getUniqueId());
        String color = profile.getChatColor();

        Component message = Component.text(p.getName())
                .color(chatColor)
                .append(Component.text(": "))
                .append(e.message().color(TextColor.fromHexString(color)));

        e.setCancelled(true);
        for (Audience recipient : e.viewers()) {
            recipient.sendMessage(message);
        }
    }
}
