package com.birdy.fragileLife.tags;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TagGUIListener implements Listener {
    private static final String GUI_TITLE = "Select TAG";
    private final ProfileManager profileManager;

    public TagGUIListener(ProfileManager profileManager){
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
                String tagPlainText = PlainTextComponentSerializer.plainText().serialize(displayName).toLowerCase();
                Profile profile = profileManager.getProfile(p.getUniqueId());

                if(tagPlainText.equalsIgnoreCase("Remove Tag")){
                    profile.setTag("NONE");
                    p.sendMessage(FragileLife.pluginPrefix
                            .append(Component.text("You've removed your tag.", NamedTextColor.GRAY)));
                } else {
                    tagPlainText = tagPlainText.substring(1, tagPlainText.length() - 1);

                    String serializedTag = Tags.TAGS.get(tagPlainText);
                    MiniMessage mm = MiniMessage.miniMessage();
                    Component tag = mm.deserialize(serializedTag);
                    profile.setTag(tagPlainText);

                    p.sendMessage(FragileLife.pluginPrefix
                            .append(Component.text("You've changed your tag to ", NamedTextColor.GRAY))
                            .append(Component.text("[", NamedTextColor.GRAY))
                            .append(tag)
                            .append(Component.text("]", NamedTextColor.GRAY)));
                }

                // Refresh inventory for to highlight newly selected tag
                p.closeInventory();
                TagGUI.open(p, profileManager);

            }
        }
    }
}
