package com.birdy.fragileLife.reactions;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.reactions.reactionTypes.MathReaction;
import com.birdy.fragileLife.reactions.reactionTypes.Reaction;
import com.birdy.fragileLife.reactions.reactionTypes.TypeReaction;
import com.birdy.fragileLife.reactions.reactionTypes.UnscrambleReaction;
import com.birdy.fragileLife.reactions.stats.UserReactionStats;
import com.birdy.fragileLife.schemas.React;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getLogger;

public class ReactionManager {

    private final Map<UUID, React> reactionCache = new HashMap<>();

    private final Random random = new Random();

    private final FragileLife plugin;
    private final ProfileManager profileManager;
    private final TeamManager teamManager;

    private final File file;
    private final FileConfiguration config;

    private Reaction reaction;

    //Constructor
    public ReactionManager(FragileLife plugin, ProfileManager profileManager, TeamManager teamManager, File dataFolder){
        this.plugin = plugin;
        this.profileManager = profileManager;
        this.teamManager = teamManager;

        this.file = new File(dataFolder, "reactions.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException err){
                getLogger().severe("Could not create reactions.yml!");
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        loadAllReactions();
    }

    public void scheduleNextReaction(){
        int delaySeconds = 420 + new Random().nextInt(360); // Generate Time From 7 to 11 Minutes (420 to 780 Seconds)
        new BukkitRunnable() {
            @Override
            public void run() {
                if(reaction == null || !reaction.isActive()) StartReactionEvent();
                scheduleNextReaction();
            }
        }.runTaskLater(plugin, delaySeconds*20);
    }

    public void StartReactionEvent(){
        int reactRandomizer = random.nextInt(3);
        switch(reactRandomizer){
            case 0 -> reaction = new TypeReaction(plugin, profileManager, teamManager, this);
            case 1 -> reaction = new UnscrambleReaction(plugin, profileManager, teamManager, this);
            case 2 -> reaction = new MathReaction(plugin, profileManager, teamManager, this);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if(reaction == null || !reaction.isActive()) return;
                reaction.stop(null);
            }
        }.runTaskLater(plugin, 30*20);
    }

    public Reaction getReaction(){
        return reaction;
    }


    // === Data Logic (don't ask me how this works) ===
    public void addReaction(React reaction) {
        reactionCache.put(UUID.randomUUID(), reaction);
    }

    public void saveReaction(React react, UUID id) {
        ConfigurationSection section = config.getConfigurationSection("reactions." + id);
        if (section == null) {
            section = config.createSection("reactions." + id);
        }

        section.set("uuid", react.getUUID().toString());
        section.set("reactionTime", react.getReactionTime());
        section.set("timestamp", react.getTimestamp());
        section.set("type", react.getType().name());

        try {
            config.save(file);
        } catch (IOException e) {
            getLogger().severe("Failed to save reactions.yml!");
        }
    }

    public void saveAll() {
        for (Map.Entry<UUID, React> entry : reactionCache.entrySet()) {
            saveReaction(entry.getValue(), entry.getKey());
        }
    }

    private void loadAllReactions() {
        ConfigurationSection root = config.getConfigurationSection("reactions");
        if (root == null) return;

        for (String idStr : root.getKeys(false)) {
            try {
                UUID id = UUID.fromString(idStr);
                ConfigurationSection section = root.getConfigurationSection(idStr);
                if (section == null) continue;

                UUID uuid = UUID.fromString(section.getString("uuid"));
                double reactionTime = section.getDouble("reactionTime");
                String timestamp = section.getString("timestamp");
                ReactionType type = ReactionType.valueOf(section.getString("type"));

                React reaction = new React(uuid, reactionTime, timestamp, type);
                reactionCache.put(id, reaction);

            } catch (Exception e) {
                getLogger().warning("Invalid reaction entry in reactions.yml: " + idStr);
            }
        }
    }

    // === Leaderboard Generator Methods ===
    public Instant getCutoff(String timeframe) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Chicago"));

        return switch (timeframe.toLowerCase()) {
            case "day" -> now.truncatedTo(ChronoUnit.DAYS).toInstant();
            case "week" -> now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                    .truncatedTo(ChronoUnit.DAYS).toInstant();
            case "month" -> now.withDayOfMonth(1)
                    .truncatedTo(ChronoUnit.DAYS).toInstant();
            default -> Instant.EPOCH;
        };
    }

    public UserReactionStats getUserStats(UUID uuid, String timeframe, ReactionType type){
        List<React> userWins = reactionCache.values().stream()
            .filter(r -> r.getUUID().equals(uuid))
            .filter(r -> Instant.parse(r.getTimestamp()).isAfter(getCutoff(timeframe)))
            .filter(r -> type == null || r.getType() == type)
            .toList();

        long wins = userWins.size();
        double fastest = userWins.stream().mapToDouble(React::getReactionTime).min().orElse(-1);

        return new UserReactionStats(fastest, wins);
    }

    public List<React> getTopSpeeds(String timeframe, ReactionType type){
        return reactionCache.values().stream()
                .filter(r -> Instant.parse(r.getTimestamp()).isAfter(getCutoff(timeframe)))
                .filter(r -> type == null || r.getType() == type)
                .sorted(Comparator.comparingDouble(React::getReactionTime))
                .limit(10)
                .toList();
    }

    public List<Map.Entry<UUID, Long>> getTopWins(String timeframe, ReactionType type){
        return reactionCache.values().stream()
                .filter(r -> type == null || r.getType() == type)
                .filter(r -> Instant.parse(r.getTimestamp()).isAfter(getCutoff(timeframe)))
                .collect(Collectors.groupingBy(React::getUUID, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<UUID, Long>comparingByValue().reversed())
                .limit(10)
                .toList();
    }
}
