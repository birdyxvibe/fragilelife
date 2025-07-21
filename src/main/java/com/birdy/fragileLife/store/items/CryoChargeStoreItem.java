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

public class CryoChargeStoreItem extends StoreItem{

    public CryoChargeStoreItem() {
        super("cryo_charge",
                generateName(),
                "A prototype grenade that slows and damages",
                "on impact",
                generateLore(),
                17,
                Material.SNOWBALL,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#FFFFFF:#72B8F6:#0D5BE7:#140091>Cryo Charge</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("A prototype cold-based explosive designed to damage and ", NamedTextColor.GRAY));
        lore.add(Component.text("immobilize targets with a blast of weaponized permafrost.", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.add(Component.text("Attributes", NamedTextColor.AQUA, TextDecoration.BOLD));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Deals up to 4♥ of damage ", NamedTextColor.RED)));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("Deals slowness 1 for 30s", NamedTextColor.BLUE)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack cryoCharge = new ItemStack(guiMaterial);
        ItemMeta cryoChargeItemMeta = cryoCharge.getItemMeta();
        cryoChargeItemMeta.displayName(name);
        cryoChargeItemMeta.lore(lore);

        cryoCharge.setItemMeta(cryoChargeItemMeta);
        return cryoCharge;
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
