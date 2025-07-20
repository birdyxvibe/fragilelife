package com.birdy.fragileLife.store.items;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HammerTimeStoreItem extends StoreItem{

    public HammerTimeStoreItem() {
        super("hammer_time",
                generateName(),
                "Unlock a low durability mace that does",
                "a cuck-load of damage. +1 Wind Charge.",
                generateLore(),
                20,
                Material.MACE,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#FEA53B:#41220B:#36373F>Hammer Time</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("A savage mace that dishes out massive damage,", NamedTextColor.GRAY));
        lore.add(Component.text("but breaks faster than your hopes in hardcore", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.add(Component.text("Attributes", NamedTextColor.AQUA, TextDecoration.BOLD));
        lore.add(Component.text("* ", NamedTextColor.WHITE)
                .append(Component.text("One shot is all you get", NamedTextColor.DARK_RED)));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack hammerTime = new ItemStack(guiMaterial);
        ItemMeta hammerTimeMeta = hammerTime.getItemMeta();
        hammerTimeMeta.displayName(name);
        hammerTimeMeta.lore(lore);
        hammerTimeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        hammerTime.setItemMeta(hammerTimeMeta);

        if (hammerTime.getItemMeta() instanceof Damageable damageableMeta) {
            int maxDurability = hammerTime.getType().getMaxDurability();
            damageableMeta.setDamage(maxDurability - 1); // Leaves 1 durability
            hammerTime.setItemMeta(damageableMeta);
        }

        return hammerTime;
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
        p.give(new ItemStack(Material.WIND_CHARGE));
        p.sendMessage(FragileLife.pluginPrefix
                .append(Component.text("Purchased.", NamedTextColor.GRAY)));
    }
}
