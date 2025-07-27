package com.birdy.fragileLife.scoreboard;

import com.birdy.fragileLife.managers.TeamManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

public class HealthScoreboard {
    private final Plugin plugin;
    private final Scoreboard scoreboard;
    private final Objective objective;

    public HealthScoreboard(Plugin plugin, TeamManager teamManager) {
        this.plugin = plugin;

        scoreboard = teamManager.getScoreboard();

        if (scoreboard.getObjective("healthDisplay") == null) {
            objective = scoreboard.registerNewObjective("healthDisplay", Criteria.DUMMY,
                    Component.text("TOP ", NamedTextColor.GRAY, TextDecoration.BOLD)
                            .append(Component.text("♥", NamedTextColor.RED).decoration(TextDecoration.BOLD, false)));
        } else {
            objective = scoreboard.getObjective("healthDisplay");
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        startUpdating(teamManager);
    }

    public void assignTo(Player player) {
        player.setScoreboard(scoreboard);
    }

    private void startUpdating(TeamManager teamManager) {

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                NamedTextColor teamColor = teamManager.getPlayerTeamColor(player);
                int extraLives = 0;
                if (teamColor == NamedTextColor.GREEN) extraLives = 60;
                if (teamColor == NamedTextColor.YELLOW) extraLives = 30;

                int hp = (int) Math.ceil(extraLives + (player.getAttribute(Attribute.MAX_HEALTH).getBaseValue() / 2));
                Score score = objective.getScore(player.getName());
                score.setScore(hp);
            }
        }, 0L, 20L); // updates every second
    }
}
