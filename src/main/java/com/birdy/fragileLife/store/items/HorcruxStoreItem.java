package com.birdy.fragileLife.store.items;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HorcruxStoreItem extends StoreItem{

    public HorcruxStoreItem() {
        super("horcrux",
                generateName(),
                "Disable PvP only for yourself",
                "for 15 minutes",
                generateLore(),
                50,
                Material.WITHER_ROSE,
                false);
    }

    private static Component generateName(){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<b><gradient:#1B0042:#272525:#000000>Horcrux</gradient></b>");
    }

    private static List<Component> generateLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Once shattered in desperation, the Horcrux seals its bearer from ", NamedTextColor.GRAY));
        lore.add(Component.text("bloodshed, cloaking them in the binding calm of a forgotten pact.", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.add(Component.text("Right click to activate", NamedTextColor.GRAY));
        return lore;
    }

    @Override
    public ItemStack generateItem() {
        ItemStack horcrux = new ItemStack(guiMaterial);
        ItemMeta horcruxItemMeta = horcrux.getItemMeta();
        horcruxItemMeta.displayName(name);
        horcruxItemMeta.lore(lore);

        horcrux.setItemMeta(horcruxItemMeta);
        return horcrux;
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

    public void beginHorcruxRitual(PlayerInteractEvent event, Plugin plugin, ProfileManager profileManager){
        if (event.getHand() != EquipmentSlot.HAND) return; // Only handle main hand

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        if (!item.getItemMeta().equals(generateItem().getItemMeta())) return;
        Player p = event.getPlayer();
        Profile profile = profileManager.getProfile(p.getUniqueId());

        if (profile.isPvpDisabled()) {
            p.sendMessage(FragileLife.pluginWarningPrefix
                    .append(Component.text("You already have a Horcrux active.", NamedTextColor.GRAY)));
            return;
        }

        item.setAmount(item.getAmount() - 1);
        profile.setPvpDisabled(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                // Remove player from no-PvP list or reverse immunity effect here
                profile.setPvpDisabled(false);
                p.sendMessage(FragileLife.pluginPrefix
                        .append(Component.text("Your Horcrux has expired. PvP is now enabled.", NamedTextColor.GRAY)));
            }
        }.runTaskLater(plugin, 20L * 60 * 15); // 20 ticks * 60 seconds * 15 minutes

        // Give heart
        p.sendMessage(FragileLife.pluginPrefix
                .append(Component.text("You have activated your Horcrux. PvP disabled for 15m", NamedTextColor.GRAY)));
    }
}
