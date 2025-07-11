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
import org.bukkit.inventory.Inventory;
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
                String greetingPlainText = PlainTextComponentSerializer.plainText().serialize(displayName).substring(2).replace(p.getName(), "PLAYER");
                TextColor greetingColor = Greetings.GREETINGS.get(greetingPlainText);

                Profile profile = profileManager.getProfile(p.getUniqueId());
                profile.setGreeting(greetingPlainText);

                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("You've changed your greeting to ", NamedTextColor.GRAY))
                        .append(Component.text("+ ", NamedTextColor.GREEN)
                        .append(Component.text(greetingPlainText.replace("PLAYER",p.getName()), greetingColor))));
            }
        }
    }
}
