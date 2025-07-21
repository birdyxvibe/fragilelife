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

public class HappyDustStoreItem extends StoreItem{

    public HappyDustStoreItem() {
        super("happy_dust",
                generateName(),
                "When held in main hand gain a",
                "significant speed boost.",
                generateLore(),
                100,
                Material.SUGAR,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#FFFFFF:#AAAAAA>Happy Dust</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Gotta go freakin' fast bud!", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.add(Component.text("Attributes", NamedTextColor.AQUA, TextDecoration.BOLD));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("+25% Speed when in Main Hand", NamedTextColor.YELLOW)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack cocaine = new ItemStack(guiMaterial);
        ItemMeta cocaineItemMeta = cocaine.getItemMeta();
        cocaineItemMeta.displayName(name);
        cocaineItemMeta.lore(lore);

        NamespacedKey key = new NamespacedKey("minecraft", "cocaine");
        AttributeModifier speedModifier = new AttributeModifier(
                key,
                0.25,
                AttributeModifier.Operation.ADD_SCALAR,
                EquipmentSlotGroup.MAINHAND
        );
        cocaineItemMeta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedModifier);
        cocaineItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        cocaine.setItemMeta(cocaineItemMeta);
        return cocaine;
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
