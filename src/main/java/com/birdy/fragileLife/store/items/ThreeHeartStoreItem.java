package com.birdy.fragileLife.store.items;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ThreeHeartStoreItem extends StoreItem{

    public ThreeHeartStoreItem() {
        super("triple_heart",
                generateName(),
                "Unlock to gain three +1♥ vouchers",
                "",
                generateLore(),
                3,
                Material.RED_DYE,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#E43A96:#FF0000>Heart</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Right click to add 1", NamedTextColor.GRAY)
                .append(Component.text("♥", NamedTextColor.RED)));
        lore.add(Component.empty());
        lore.add(Component.text("Attributes", NamedTextColor.AQUA, TextDecoration.BOLD));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("+1",NamedTextColor.WHITE))
                .append(Component.text("♥", NamedTextColor.RED))
                .append(Component.text(" permanently", NamedTextColor.WHITE)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack heart = new ItemStack(guiMaterial);
        heart.setAmount(3);
        ItemMeta heartMeta = heart.getItemMeta();
        heartMeta.displayName(name);
        heartMeta.lore(lore);
        heart.setItemMeta(heartMeta);
        return heart;
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
