package com.birdy.fragileLife;

import com.birdy.fragileLife.chat.ChatCommand;
import com.birdy.fragileLife.chat.ChatGUIListener;
import com.birdy.fragileLife.chat.NickCommand;
import com.birdy.fragileLife.listeners.*;
import com.birdy.fragileLife.commands.GiftCommand;
import com.birdy.fragileLife.greetings.GreetingCommand;
import com.birdy.fragileLife.greetings.GreetingGUIListener;
import com.birdy.fragileLife.listeners.PlayerJoinListener;
import com.birdy.fragileLife.reactions.ReactionManager;
import com.birdy.fragileLife.missions.MissionCommand;
import com.birdy.fragileLife.missions.MissionGUIListener;
import com.birdy.fragileLife.reactions.stats.ReactionCommand;
import com.birdy.fragileLife.reactions.stats.ReactionGUIListener;
import com.birdy.fragileLife.tags.TagCommand;
import com.birdy.fragileLife.tags.TagGUIListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.managers.ProfileManager;

import java.util.*;

public final class FragileLife extends JavaPlugin {

    public static final List<Location> placedMissionBlockLocations = new ArrayList<>();

    ProfileManager profileManager;
    TeamManager teamManager;
    ReactionManager reactionManager;

    public static final Component pluginPrefix =
            Component.text("[")
                    .color(NamedTextColor.WHITE)
            .append(Component.text("☁")
                    .color(TextColor.fromHexString("#4CCCE9")))
            .append(Component.text("] ")
                    .color(NamedTextColor.WHITE));

    public static final Component pluginWarningPrefix =
            Component.text("[")
                    .color(NamedTextColor.WHITE)
                    .append(Component.text("✖")
                            .color(TextColor.fromHexString("#c0193c")))
                    .append(Component.text("] ")
                            .color(NamedTextColor.WHITE));

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("FragileLife has started!");

        profileManager = new ProfileManager(getDataFolder());

        teamManager =  new TeamManager();
        teamManager.initializeTeams();

        reactionManager = new ReactionManager(this, profileManager, teamManager, getDataFolder());
        reactionManager.scheduleNextReaction();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, teamManager, profileManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(teamManager, profileManager), this);
        getServer().getPluginManager().registerEvents(new ChatGUIListener(profileManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(teamManager, profileManager, reactionManager), this);
        getServer().getPluginManager().registerEvents(new GreetingGUIListener(profileManager), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(profileManager, teamManager), this);
        getServer().getPluginManager().registerEvents(new MissionGUIListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(profileManager,teamManager), this);
        getServer().getPluginManager().registerEvents(new TagGUIListener(profileManager), this);
        getServer().getPluginManager().registerEvents(new ReactionGUIListener(reactionManager), this);
        getServer().getPluginManager().registerEvents(new FishEventListener(profileManager, teamManager), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(profileManager, teamManager), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(profileManager, teamManager), this);
        getServer().getPluginManager().registerEvents(new PickupExperienceListener(profileManager, teamManager), this);

        getCommand("chat").setExecutor(new ChatCommand(profileManager));
        getCommand("gift").setExecutor(new GiftCommand(profileManager, teamManager));
        getCommand("greetings").setExecutor(new GreetingCommand(profileManager));
        getCommand("missions").setExecutor(new MissionCommand(profileManager,teamManager));
        getCommand("tags").setExecutor(new TagCommand(profileManager));
        getCommand("reaction").setExecutor(new ReactionCommand(reactionManager));
        getCommand("nick").setExecutor(new NickCommand(profileManager, teamManager));
    }

    @Override
    public void onDisable() {
        if(profileManager != null){
            profileManager.saveAll();
        }
        if(reactionManager != null){
            reactionManager.saveAll();
        }
        getLogger().info("FragileLife has stopped.");
    }
}
