package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.greetings.GreetingJoinEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

public class PlayerLeaveListener implements Listener {


    String[] quitMessages = {
            " parents told them to go to bed.",
            " went to go wank.",
            " is off to buy rope and a chair.",
            " left to go touch grass.",
            " left to moisturize... vigorously.",
            " went to cry in the shower.",
            " disappeared like my dad."
    };
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        Random random = new Random();
        int index = random.nextInt(quitMessages.length);
            e.quitMessage(Component.text("- ", NamedTextColor.RED)
                    .append(Component.text(p.getName(), NamedTextColor.WHITE))
                    .append(Component.text(quitMessages[index], NamedTextColor.GRAY)));

    }
}
