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

public class GoldenAppleStoreItem extends StoreItem{

    public GoldenAppleStoreItem() {
        super("golden_apple",
                generateName(),
                "Unlock 3 Enchanted Golden apples",
                "",
                generateLore(),
                10,
                Material.ENCHANTED_GOLDEN_APPLE,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#FC5B49:#B0FF73>God Apple</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Simply an enchanted golden apple,", NamedTextColor.GRAY));
        lore.add(Component.text("what more could you want?", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.add(Component.text("Attributes", NamedTextColor.AQUA, TextDecoration.BOLD));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("+8",NamedTextColor.WHITE))
                .append(Component.text("♥", NamedTextColor.RED))
                .append(Component.text(" for 2 min", NamedTextColor.WHITE)));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Fire Resistance I for 5 min", NamedTextColor.GOLD)));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Resistance I for 5 min", NamedTextColor.DARK_BLUE)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack goldenApple = new ItemStack(guiMaterial);
        goldenApple.setAmount(3);
        ItemMeta goldenAppleMeta = goldenApple.getItemMeta();
        goldenAppleMeta.displayName(name);
        goldenAppleMeta.lore(lore);

        goldenApple.setItemMeta(goldenAppleMeta);
        return goldenApple;
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
