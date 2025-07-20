package com.birdy.fragileLife.store;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import com.birdy.fragileLife.store.items.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreGUI {
    private static final String GUI_TITLE = "Soul Fragment Store";
    private static final int GUI_SIZE = 36;

    // Add new missions here
    public static final List<StoreItem> storeItems = new ArrayList<>(Arrays.asList(
            new CloudBootsStoreItem(),
            new ReviveTotemStoreItem(),
            new GoldenAppleStoreItem(),
            new HeartStoreItem(),
            new ThreeHeartStoreItem(),
            new CrimsonCarverStoreItem(),
            new HammerTimeStoreItem()));

    public static void open(Player p, ProfileManager profileManager){
        Inventory gui = Bukkit.createInventory(null, GUI_SIZE, Component.text(GUI_TITLE));

        // Populate GUI with Fillers
        final ItemStack fillerItem1 = createFiller(Material.GRAY_STAINED_GLASS_PANE);

        for(int i = 0; i < GUI_SIZE; i++) {
            gui.setItem(i, fillerItem1);
        }

        Profile profile = profileManager.getProfile(p.getUniqueId());
        int slot = 11;

        for(StoreItem s : storeItems) {
            if(slot == 16) slot += 3;
            ItemStack storeItem = s.generateItem();
            ItemMeta storeItemMeta = storeItem.getItemMeta();

            List<Component> lore = new ArrayList<>();
            lore.add(Component.text(s.getDesc(), NamedTextColor.GRAY));
            if (!s.getDescTwo().isEmpty()) {
                lore.add(Component.text(s.getDescTwo(), NamedTextColor.GRAY));
            }
            lore.add(Component.empty());
            lore.add(Component.text("Cost: ", NamedTextColor.WHITE)
                    .append(Component.text(s.getCost(), NamedTextColor.GOLD))
                    .append(Component.text(" Soul Fragment(s)", NamedTextColor.WHITE)));
            lore.add(Component.empty());
            lore.add(Component.text("→ Left click to buy", NamedTextColor.DARK_GRAY));
            storeItemMeta.lore(lore);
            storeItem.setItemMeta(storeItemMeta);
            gui.setItem(slot++, storeItem);
        }

        // Display Soul Fragments
        gui.setItem(4, getHead(p, profile));

        p.openInventory(gui);
    }


    private static ItemStack createFiller(Material material){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.empty());

        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getHead(Player p, Profile profile) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();

        Component fragmentCount = Component.text("Balance: ", NamedTextColor.WHITE, TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(profile.getSoulFragments(), NamedTextColor.GOLD))
                .append(Component.text(" Soul Fragment(s)", NamedTextColor.GRAY));

        skull.displayName(fragmentCount);
        skull.setOwningPlayer(p);
        item.setItemMeta(skull);
        return item;
    }
}
