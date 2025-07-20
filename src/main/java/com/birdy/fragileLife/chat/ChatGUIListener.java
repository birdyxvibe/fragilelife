package com.birdy.fragileLife.chat;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;

public class ChatGUIListener implements Listener {

    private static final String GUI_TITLE = "Select Chat Color";
    private final ProfileManager profileManager;

    public ChatGUIListener(ProfileManager profileManager){
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
                String plainText = PlainTextComponentSerializer.plainText().serialize(displayName);
                Profile profile = profileManager.getProfile(p.getUniqueId());

                if (plainText.equals("Bold") || plainText.equals("Italic")) {
                    boolean active;
                    if (plainText.equals("Bold")){
                        active = profile.isChatBold();
                        profile.setChatBold(!active);
                    } else {
                        active = profile.isChatItalic();
                        profile.setChatItalic(!active);
                }

                
                ChatGUI.open(p, profileManager);
                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text(plainText, NamedTextColor.WHITE))
                        .append(Component.text(" chat style has been ", NamedTextColor.GRAY))
                        .append(Component.text(active ? "Disabled" : "Enabled", NamedTextColor.WHITE)));
                return;
            }
                ChatColors.ColorOption colorData = ChatColors.COLORS.get(plainText);

                profile.setChatColor(colorData.color.asHexString());

                // Refresh inventory for to highlight newly selected color
                
                ChatGUI.open(p, profileManager);

                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("You've changed your chat color to ", NamedTextColor.GRAY))
                        .append(Component.text(plainText, colorData.color)));
            }
        }
    }
}
