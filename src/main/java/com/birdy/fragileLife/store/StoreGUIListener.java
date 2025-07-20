package com.birdy.fragileLife.store;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import com.birdy.fragileLife.store.items.StoreItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class StoreGUIListener implements Listener {
    private static final String GUI_TITLE = "Soul Fragment Store";
    private final ProfileManager profileManager;

    public StoreGUIListener(ProfileManager profileManager){
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (!e.getView().title().equals(Component.text(GUI_TITLE))) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null) return;

        Player p = (Player)e.getWhoClicked();
        Profile profile = profileManager.getProfile(p.getUniqueId());

        String plainText;
        if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()){
            for (StoreItem s : StoreGUI.storeItems) {
                List<Component> lore = clickedItem.getItemMeta().lore();
                if(lore != null) {
                    plainText = PlainTextComponentSerializer.plainText().serialize(lore.getFirst());
                    if (plainText.equals(s.getDesc())) {
                        s.giveItem(profile,p);
                        
                        StoreGUI.open(p, profileManager);
                        return;
                    }
                }
            }
        }
    }
}
