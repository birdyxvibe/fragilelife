package com.birdy.fragileLife;

import com.birdy.fragileLife.chat.ChatCommand;
import com.birdy.fragileLife.chat.ChatGUIListener;
import com.birdy.fragileLife.chat.PlayerChatEvent;
import com.birdy.fragileLife.commands.GiftCommand;
import com.birdy.fragileLife.listeners.PlayerDamageListener;
import com.birdy.fragileLife.listeners.PlayerDeathListener;
import com.birdy.fragileLife.listeners.PlayerJoinListener;
import com.birdy.fragileLife.schemas.Profile;
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
            .append(Component.text("Fragile")
                    .color(TextColor.fromHexString("#4CCCE9")))
            .append(Component.text("Utilities")
                    .color(TextColor.fromHexString("#7abbc0")))
            .append(Component.text("] ")
                    .color(NamedTextColor.WHITE));

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("FragileLife has started!");

        this.profileManager = new ProfileManager(getDataFolder());

        TeamManager teamManager = new TeamManager();
        teamManager.initializeTeams();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, teamManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(teamManager), this);
        getServer().getPluginManager().registerEvents(new ChatGUIListener(profileManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(teamManager, profileManager), this);

        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("gift").setExecutor(new GiftCommand(profileManager, teamManager));
    }

    @Override
    public void onDisable() {
        if(profileManager != null){
            profileManager.saveAll();
        }
        getLogger().info("FragileLife has stopped.");
    }
}
