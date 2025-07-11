package com.birdy.fragileLife.greetings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class GreetingGUI {

    private static final String GUI_TITLE = "Select Greeting Message";
    private static final int GUI_SIZE = 36;

    public static void open(Player p){
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

        // Populate GUI with Colors
        //TODO: Check greeting against players greeting in database and enchant it
        int slot = 10;
        for(Map.Entry<String, TextColor> entry : Greetings.GREETINGS.entrySet()) {
            if(slot == 17) slot += 2;

            TextColor greetingColor = entry.getValue();
            ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text("+ ", NamedTextColor.GREEN)
                    .append(Component.text(entry.getKey().replace("PLAYER", p.getName()), greetingColor)));
            item.setItemMeta(meta);


            gui.setItem(slot++, item);
        }

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
