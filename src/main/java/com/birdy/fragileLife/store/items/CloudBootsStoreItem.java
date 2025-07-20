package com.birdy.fragileLife.store.items;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CloudBootsStoreItem extends StoreItem{

    public CloudBootsStoreItem() {
        super("cloud_boots",
                generateName(),
                "Unlock chainmail boots that let you fall up to",
                "5 blocks without taking damage.",
                generateLore(),
                25,
                Material.CHAINMAIL_BOOTS,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#4498DB:#ADAAA4>Cloud Boots</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Forged from drifting wisps of sky,", NamedTextColor.GRAY));
        lore.add(Component.text("Cloud Boots cushion your every step", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.add(Component.text("Attributes", NamedTextColor.AQUA, TextDecoration.BOLD));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("5 block safe fall distance", NamedTextColor.DARK_GREEN)));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Unbreaking", NamedTextColor.GOLD)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack cloudBoots = new ItemStack(guiMaterial);
        ItemMeta cloudBootsMeta = cloudBoots.getItemMeta();
        cloudBootsMeta.displayName(name);
        cloudBootsMeta.lore(lore);

        NamespacedKey key = new NamespacedKey("minecraft", "cloud_boots");
        AttributeModifier fallModifier = new AttributeModifier(
                key,
                2,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.FEET
        );

        AttributeModifier armorModifier = new AttributeModifier(
                key,
                1,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.FEET
        );

        cloudBootsMeta.addAttributeModifier(Attribute.SAFE_FALL_DISTANCE, fallModifier);
        cloudBootsMeta.addAttributeModifier(Attribute.ARMOR, armorModifier);

        cloudBootsMeta.setUnbreakable(true);
        cloudBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        cloudBootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        cloudBoots.setItemMeta(cloudBootsMeta);
        return cloudBoots;
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
