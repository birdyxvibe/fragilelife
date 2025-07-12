package com.birdy.fragileLife.greetings;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class GreetingGUI {

    private static final String GUI_TITLE = "Select Greeting Message";
    private static final int GUI_SIZE = 36;

    public static void open(Player p, ProfileManager profileManager){
        Inventory gui = Bukkit.createInventory(null, GUI_SIZE, Component.text(GUI_TITLE));

        // Populate GUI with Fillers
        final ItemStack fillerItem1 = createFiller(Material.GRAY_STAINED_GLASS_PANE);
        final ItemStack fillerItem2 = createFiller(Material.CHAIN);

        for(int i = 0; i < GUI_SIZE; i++) {
            if (i <= 8 || i >= 27) {
                gui.setItem(i, fillerItem1);
            } else if (i % 9 == 0 || i % 9 == 8) {
                gui.setItem(i, fillerItem2);
            }
        }

        Profile profile = profileManager.getProfile(p.getUniqueId());
        String selectedGreeting = profile.getGreeting();

        // Populate GUI with Colors
        int slot = 10;
        for(Map.Entry<String, TextColor> entry : Greetings.GREETINGS.entrySet()) {
            if(slot == 17) slot += 2;

            TextColor greetingColor = entry.getValue();
            ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text("+ ", NamedTextColor.GREEN)
                    .append(Component.text(entry.getKey().replace("PLAYER", p.getName()), greetingColor)));

            if (selectedGreeting != null && selectedGreeting.equals(entry.getKey())) {
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            item.setItemMeta(meta);
            gui.setItem(slot++, item);
        }

        // Add random greeting button
        final ItemStack randomGreetingItem = new ItemStack(Material.STONE_BUTTON);
        ItemMeta randomGreetingMeta = randomGreetingItem.getItemMeta();
        randomGreetingMeta.displayName(Component.text("Random Greeting", NamedTextColor.DARK_GRAY));

        if (selectedGreeting != null && selectedGreeting.equals("RAND")) {
            randomGreetingMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
            randomGreetingMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        randomGreetingItem.setItemMeta(randomGreetingMeta);
        gui.setItem(4, randomGreetingItem);

        p.openInventory(gui);
    }


    public static ItemStack createFiller(Material material){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.empty());

        item.setItemMeta(meta);
        return item;
    }

}
