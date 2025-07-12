package com.birdy.fragileLife;

import com.birdy.fragileLife.chat.ChatCommand;
import com.birdy.fragileLife.chat.ChatGUIListener;
import com.birdy.fragileLife.chat.PlayerChatEvent;
import com.birdy.fragileLife.commands.GiftCommand;
import com.birdy.fragileLife.greetings.GreetingCommand;
import com.birdy.fragileLife.greetings.GreetingGUIListener;
import com.birdy.fragileLife.listeners.EntityDeathListener;
import com.birdy.fragileLife.listeners.PlayerDamageListener;
import com.birdy.fragileLife.listeners.PlayerDeathListener;
import com.birdy.fragileLife.greetings.PlayerJoinListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.managers.ProfileManager;

public final class FragileLife extends JavaPlugin {

    private ProfileManager profileManager;

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

        this.profileManager = new ProfileManager(getDataFolder());

        TeamManager teamManager = new TeamManager();
        teamManager.initializeTeams();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, teamManager, profileManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(teamManager, profileManager), this);
        getServer().getPluginManager().registerEvents(new ChatGUIListener(profileManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(teamManager, profileManager), this);
        getServer().getPluginManager().registerEvents(new GreetingGUIListener(profileManager), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(profileManager), this);

        getCommand("chat").setExecutor(new ChatCommand(profileManager));
        getCommand("gift").setExecutor(new GiftCommand(profileManager, teamManager));
        getCommand("greetings").setExecutor(new GreetingCommand(profileManager));
    }

    @Override
    public void onDisable() {
        if(profileManager != null){
            profileManager.saveAll();
        }
        getLogger().info("FragileLife has stopped.");
    }
}
