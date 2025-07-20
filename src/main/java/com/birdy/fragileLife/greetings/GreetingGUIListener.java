package com.birdy.fragileLife.greetings;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GreetingGUIListener implements Listener {

    private static final String GUI_TITLE = "Select Greeting Message";
    private final ProfileManager profileManager;

    public GreetingGUIListener(ProfileManager profileManager){
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (!e.getView().title().equals(Component.text(GUI_TITLE))) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null) return;

        Player p = (Player)e.getWhoClicked();

        if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()){
            Component displayName = clickedItem.getItemMeta().displayName();
            if(displayName != null) {
                String greetingPlainText = PlainTextComponentSerializer.plainText().serialize(displayName);
                Profile profile = profileManager.getProfile(p.getUniqueId());

                // Random greeting functionality
                if (greetingPlainText.equals("Random Greeting")) {
                    if (profile.getGreeting().equals("RAND")) {
                        p.sendMessage(FragileLife.pluginWarningPrefix
                                .append(Component.text("Randomised greetings is already enabled", NamedTextColor.GRAY)));
                        return;
                    }
                    profile.setGreeting("RAND");
                    GreetingGUI.open(p, profileManager);

                    p.sendMessage(FragileLife.pluginPrefix
                            .append(Component.text("You have enabled randomised greetings.", NamedTextColor.GRAY)));
                    return;
                }

                greetingPlainText = greetingPlainText.substring(2).replace(p.getName(), "PLAYER");

                TextColor greetingColor = Greetings.GREETINGS.get(greetingPlainText);
                profile.setGreeting(greetingPlainText);

                // Refresh inventory for to highlight newly selected greeting
                
                GreetingGUI.open(p, profileManager);

                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("You've changed your greeting to ", NamedTextColor.GRAY))
                        .append(Component.text("+ ", NamedTextColor.GREEN)
                        .append(Component.text(greetingPlainText.replace("PLAYER",p.getName()), greetingColor))));
            }
        }
    }
}
