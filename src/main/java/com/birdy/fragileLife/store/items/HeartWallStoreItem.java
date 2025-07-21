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

public class HeartWallStoreItem extends StoreItem{

    public HeartWallStoreItem() {
        super("heart_wall",
                generateName(),
                "Unlock an Unbreaking III shield.",
                "",
                generateLore(),
                15,
                Material.SHIELD,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#E43A3A:#D39548>Heart Wall</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("A shield that wears with time but refuses to fall, its", NamedTextColor.GRAY));
        lore.add(Component.text("strength drawn from the quiet resilience of its bearer.", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.add(Component.text("Attributes", NamedTextColor.AQUA, TextDecoration.BOLD));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Unbreaking 3 ", NamedTextColor.GOLD)));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Mending", NamedTextColor.LIGHT_PURPLE)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack heartWall = new ItemStack(guiMaterial);
        ItemMeta heartWallItemMeta = heartWall.getItemMeta();
        heartWallItemMeta.displayName(name);
        heartWallItemMeta.lore(lore);

        heartWallItemMeta.addEnchant(Enchantment.UNBREAKING, 3, true);
        heartWallItemMeta.addEnchant(Enchantment.MENDING, 1, true);
        heartWallItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        heartWallItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        heartWallItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        heartWall.setItemMeta(heartWallItemMeta);
        return heartWall;
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
