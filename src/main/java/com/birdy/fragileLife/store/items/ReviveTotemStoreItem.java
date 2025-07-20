package com.birdy.fragileLife.store.items;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ReviveTotemStoreItem extends StoreItem{

    public ReviveTotemStoreItem() {
        super("revive_totem",
                generateName(),
                "Unlock a Totem that when kept in inventory,",
                "revives you on death with 0.5♥",
                generateLore(),
                10,
                Material.TOTEM_OF_UNDYING,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#FCD05C:#E43A96>Revive Totem</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("A mysterious totem that when kept in your inventory,", NamedTextColor.GRAY));
        lore.add(Component.text("revives you on death with 0.5", NamedTextColor.GRAY)
                .append(Component.text("♥", NamedTextColor.RED)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack reviveTotem = new ItemStack(guiMaterial);
        ItemMeta reviveTotemMeta = reviveTotem.getItemMeta();
        reviveTotemMeta.displayName(name);
        reviveTotemMeta.lore(lore);

        reviveTotem.setItemMeta(reviveTotemMeta);
        return reviveTotem;
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
