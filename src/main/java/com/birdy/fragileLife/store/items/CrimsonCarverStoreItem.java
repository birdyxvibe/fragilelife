package com.birdy.fragileLife.store.items;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CrimsonCarverStoreItem extends StoreItem{

    public CrimsonCarverStoreItem() {
        super("crimson_carver",
                generateName(),
                "Unlock an Efficiency IV diamond pickaxe.",
                "",
                generateLore(),
                25,
                Material.DIAMOND_PICKAXE,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#E10808:#D85454:#F38E8E>Crimson Carver</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("A pickaxe forged in deep crimson fire, breaking through", NamedTextColor.GRAY));
        lore.add(Component.text("the toughest stone with unmatched speed and power.", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.add(Component.text("Attributes", NamedTextColor.AQUA, TextDecoration.BOLD));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Efficiency 4 ", NamedTextColor.DARK_GREEN)));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Unbreakable", NamedTextColor.GOLD)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack crimsonCarver = new ItemStack(guiMaterial);
        ItemMeta crimsonCarverMeta = crimsonCarver.getItemMeta();
        crimsonCarverMeta.displayName(name);
        crimsonCarverMeta.lore(lore);

        crimsonCarverMeta.addEnchant(Enchantment.EFFICIENCY, 4, true);
        crimsonCarverMeta.setUnbreakable(true);
        crimsonCarverMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        crimsonCarverMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        crimsonCarverMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        crimsonCarver.setItemMeta(crimsonCarverMeta);
        return crimsonCarver;
    }

    @Override
    public void giveItem(Profile profile, Player p) {
        if (!canAfford(profile)){
            p.sendMessage(FragileLife.pluginWarningPrefix
                        .append(Component.text("You cannot afford this item.", NamedTextColor.GRAY)));
            return;
        }
        withdrawFragments(profile);
        p.give(generateItem());
        p.sendMessage(FragileLife.pluginPrefix
                .append(Component.text("Purchased.", NamedTextColor.GRAY)));
    }
}
