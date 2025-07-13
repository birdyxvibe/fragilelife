package com.birdy.fragileLife.tags;

import com.birdy.fragileLife.greetings.Greetings;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class TagGUI {
    private static final String GUI_TITLE = "Select TAG";
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
        String selectedTag = profile.getTag();

        // Populate GUI with Colors
        int slot = 10;
        for(Map.Entry<String, String> entry : Tags.TAGS.entrySet()) {
            if(slot == 17) slot += 2;

            MiniMessage mm = MiniMessage.miniMessage();
            Component tag = mm.deserialize(entry.getValue());
            ItemStack item = new ItemStack(Material.NAME_TAG);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text("[", NamedTextColor.GRAY)
                    .append(tag)
                    .append(Component.text("]", NamedTextColor.GRAY)));

            if (selectedTag != null && selectedTag.equals(entry.getKey())) {
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

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
