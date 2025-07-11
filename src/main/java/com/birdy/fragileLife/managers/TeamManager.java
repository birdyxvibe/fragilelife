package com.birdy.fragileLife.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.entity.Player;

public class TeamManager {

    private final Scoreboard scoreboard;

    private Team greenTeam;
    private Team yellowTeam;
    private Team redTeam;
    private Team ghostTeam;

    public TeamManager(){
        this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    public void initializeTeams() {
        greenTeam = getOrCreateTeam("Green", NamedTextColor.GREEN);
        yellowTeam = getOrCreateTeam("Yellow", NamedTextColor.YELLOW);
        redTeam = getOrCreateTeam("Red", NamedTextColor.RED);
        ghostTeam = getOrCreateTeam("Ghost", NamedTextColor.GRAY);
    }

    private Team getOrCreateTeam(String name, NamedTextColor color) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
            team.color(color);
        }

        return team;
    }

    public Team getPlayerTeam(Player p){
        return scoreboard.getEntryTeam(p.getName());
    }

    public NamedTextColor getPlayerTeamColor(Player p){
        String teamName = getPlayerTeam(p).getName();
        return switch (teamName.toLowerCase()) {
            case "green" -> NamedTextColor.GREEN;
            case "yellow" -> NamedTextColor.YELLOW;
            case "red" -> NamedTextColor.RED;
            case "ghost" -> NamedTextColor.GRAY;
            default -> NamedTextColor.WHITE;
        };
    }

    public void assignPlayerToTeam(Player p, String teamName) {
        for(Team team : scoreboard.getTeams()){
            team.removeEntry(p.getName());
        }

        Team team = switch (teamName.toLowerCase()) {
            case "green" -> greenTeam;
            case "yellow" -> yellowTeam;
            case "red" -> redTeam;
            case "ghost" -> ghostTeam;
            default -> null;
        };

        if(team != null){
            team.addEntry(p.getName());
            p.setScoreboard(scoreboard);
        }
    }
}
