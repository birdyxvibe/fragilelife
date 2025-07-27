package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.books.FragileLifeBook;
import com.birdy.fragileLife.greetings.GreetingJoinEvent;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.scoreboard.HealthScoreboard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class PlayerJoinListener implements Listener {

    private final FragileLife plugin;
    private final ProfileManager profileManager;
    private final TeamManager teamManager;
    private final HealthScoreboard healthBoard;

    public PlayerJoinListener(FragileLife plugin, TeamManager teamManager, ProfileManager profileManager, HealthScoreboard healthBoard){
        this.plugin = plugin;
        this.teamManager = teamManager;
        this.profileManager = profileManager;
        this.healthBoard = healthBoard;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        GreetingJoinEvent greetingJoinEvent = new GreetingJoinEvent(e, profileManager);
        if (!p.hasPlayedBefore()) {
            p.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30 * 2);
            p.setHealth(30 * 2);
            Title fullTitle = getTitle();
            p.showTitle(fullTitle);
            Bukkit.getScheduler().runTask(plugin, () -> teamManager.assignPlayerToTeam(p, "green"));

            FragileLifeBook.giveBook(p);

            e.joinMessage(Component.text("+ ", NamedTextColor.GREEN)
                    .append(Component.text(p.getName(), NamedTextColor.GOLD))
                    .append(Component.text(" has joined for the first time. Welcome!", NamedTextColor.LIGHT_PURPLE)));

            return;
        }
        greetingJoinEvent.displayGreeting();
        healthBoard.assignTo(p);

    }

    private static @NotNull Title getTitle() {
        MiniMessage mm = MiniMessage.miniMessage();
        Component fragileLife = mm.deserialize("<b><gradient:#4498DB:#FFFFFF:#E36F1E>FragileLife</gradient></b>");
        Component title = Component.text("Welcome", NamedTextColor.GOLD, TextDecoration.BOLD)
                .append(Component.text(" to ", NamedTextColor.WHITE, TextDecoration.BOLD))
                .append(fragileLife);
        Component subtitle = Component.text("Please read the book you received.", NamedTextColor.GRAY).decorate(TextDecoration.ITALIC);
        Title.Times times = Title.Times.times(
                Duration.ofMillis(500),     // fade in (0.5 seconds)
                Duration.ofSeconds(5),      // stay
                Duration.ofMillis(1000)     // fade out (1 second)
        );
        return Title.title(title, subtitle, times);
    }
}
