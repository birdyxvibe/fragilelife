package com.birdy.fragileLife.greetings;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class PlayerJoinListener implements Listener {

    private final FragileLife plugin;
    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    public PlayerJoinListener(FragileLife plugin, TeamManager teamManager, ProfileManager profileManager){
        this.plugin = plugin;
        this.teamManager = teamManager;
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Profile profile = profileManager.getProfile(p.getUniqueId());
        String greeting = profile.getGreeting().replace("PLAYER", p.getName());
        TextColor greetingColor = Greetings.GREETINGS.get(greeting);
        e.joinMessage(Component.text("+ ").color(NamedTextColor.GREEN)
                .append(Component.text(greeting).color(greetingColor)));
        if (!p.hasPlayedBefore()) {
            p.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30 * 2);
            p.setHealth(30 * 2);

            Bukkit.getScheduler().runTask(plugin, () -> {
                teamManager.assignPlayerToTeam(p, "green");
            });
        }
    }
}
