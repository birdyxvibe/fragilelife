package com.birdy.fragileLife.chat;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
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
        Inventory inv = e.getInventory();

        if (!e.getView().title().equals(Component.text(GUI_TITLE))) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null) return;

        Player p = (Player)e.getWhoClicked();

        if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()){
            Component displayName = clickedItem.getItemMeta().displayName();
            if(displayName != null) {
                String plainText = PlainTextComponentSerializer.plainText().serialize(displayName);
                ChatColors.ColorOption colorData = ChatColors.COLORS.get(plainText);

                Profile profile = profileManager.getProfile(p.getUniqueId());
                profile.setChatColor(colorData.color.asHexString());

                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("You've changed your chat color to ").color(NamedTextColor.GRAY))
                        .append(Component.text(plainText).color(colorData.color)));
            }
        }
    }
}
