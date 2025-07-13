package com.birdy.fragileLife.chat;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChatGUI {

    private static final String GUI_TITLE = "Select Chat Color";
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

        // Populate GUI with Colors
        Profile profile = profileManager.getProfile(p.getUniqueId());
        TextColor selectedColor = TextColor.fromHexString(profile.getChatColor());

        int slot = 10;
        for (Map.Entry<String, ChatColors.ColorOption> entry : ChatColors.COLORS.entrySet()) {
            if (slot == 17) slot += 2;

            ChatColors.ColorOption colorOption = entry.getValue();
            ItemStack item = new ItemStack(colorOption.material);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(entry.getKey(), colorOption.color).decorate(TextDecoration.BOLD));
            List<Component> lore = Arrays.stream(colorOption.lore)
                    .map(line -> Component.text(line, NamedTextColor.WHITE))
                    .collect(Collectors.toList());
            meta.lore(lore);

            if (selectedColor != null && Objects.equals(selectedColor, colorOption.color)) {
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            item.setItemMeta(meta);
            gui.setItem(slot++, item);
        }

        // Add bold and italic GUI items
        final ItemStack toggleBoldItem = new ItemStack(Material.ANVIL);
        final ItemStack toggleItalicItem = new ItemStack(Material.FEATHER);
        ItemMeta boldItemMeta = toggleBoldItem.getItemMeta();
        ItemMeta italicItemMeta = toggleItalicItem.getItemMeta();

        boldItemMeta.displayName(Component.text("Bold", NamedTextColor.DARK_GRAY, TextDecoration.BOLD));
        italicItemMeta.displayName(Component.text("Italic", NamedTextColor.GRAY, TextDecoration.ITALIC));

        if (profile.isChatBold()) {
            boldItemMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
            boldItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (profile.isChatItalic()) {
            italicItemMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
            italicItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        toggleBoldItem.setItemMeta(boldItemMeta);
        toggleItalicItem.setItemMeta(italicItemMeta);

        gui.setItem(30, toggleBoldItem);
        gui.setItem(32, toggleItalicItem);

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
