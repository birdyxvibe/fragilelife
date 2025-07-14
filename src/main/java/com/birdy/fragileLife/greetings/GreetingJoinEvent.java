package com.birdy.fragileLife.greetings;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.schemas.Profile;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GreetingJoinEvent {

    private final PlayerJoinEvent e;
    private final ProfileManager profileManager;

    public GreetingJoinEvent(PlayerJoinEvent e, ProfileManager profileManager) {
        this.e = e;
        this.profileManager = profileManager;
    }

    public void displayGreeting() {
        Player p = e.getPlayer();
        Profile profile = profileManager.getProfile(p.getUniqueId());
        String greeting = profile.getGreeting();

        // Choose random greeting logic
        if (greeting.equals("RAND")) {
            List<Map.Entry<String, TextColor>> greetingsList = new ArrayList<>(Greetings.GREETINGS.entrySet());

            Random rand = new Random();
            Map.Entry<String, TextColor> randomEntry = greetingsList.get(rand.nextInt(greetingsList.size()));

            String randomGreeting = randomEntry.getKey().replace("PLAYER", p.getName());
            TextColor greetingColor = randomEntry.getValue();

            e.joinMessage(Component.text("+ ", NamedTextColor.GREEN)
                    .append(Component.text(randomGreeting, greetingColor)));

            return;
        }

        TextColor greetingColor = Greetings.GREETINGS.get(greeting);
        e.joinMessage(Component.text("+ ", NamedTextColor.GREEN)
                .append(Component.text(greeting.replace("PLAYER", p.getName()), greetingColor)));
    }
}
