package com.birdy.fragileLife.slots;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SlotGUIListener implements Listener {
    private static final String GUI_TITLE = "Slot Machine";
    private final Plugin plugin;
    private final ProfileManager profileManager;

    public SlotGUIListener(Plugin plugin, ProfileManager profileManager){
        this.plugin = plugin;
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().title().equals(Component.text(GUI_TITLE))) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null) return;

        Player p = (Player) e.getWhoClicked();
        Profile profile = profileManager.getProfile(p.getUniqueId());

        if (profile.isSpinning()) {
            p.sendMessage(FragileLife.pluginWarningPrefix
                    .append(Component.text("You must wait for your spin to finish.", NamedTextColor.GRAY)));
            return;
        }

        Inventory inv = e.getInventory();

        if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
            Component displayName = clickedItem.getItemMeta().displayName();
            if (displayName != null) {
                String plainText = PlainTextComponentSerializer.plainText().serialize(displayName);
                if (plainText.equals("CLICK TO SPIN")) {
                    int wager = profile.getWager();
                    if (!removeDiamonds(p,wager)) {
                        p.sendMessage(FragileLife.pluginWarningPrefix
                                .append(Component.text("You do NOT have ", NamedTextColor.GRAY))
                                .append(Component.text(wager, NamedTextColor.AQUA))
                                .append(Component.text(" diamonds!", NamedTextColor.GRAY)));
                        return;
                    }
                    profile.setSpinning(true);
                    SlotGUI.spin(p, inv, plugin, wager, payout -> {
                        if (payout > 0) {
                            p.getInventory().addItem(new ItemStack(Material.DIAMOND, payout));
                        }
                        profile.setSpinning(false);
                    });
                }
                if (plainText.equals("CLICK TO ADJUST WAGER")) {
                    int current = profile.getWager();
                    int next = Slot.getNextWager(current);
                    profile.setWager(next);
                    inv.setItem(23, SlotGUI.createWagerButton(next));
                }
            }
        }
    }

    private static boolean removeDiamonds(Player p, int amount) {
        int totalDiamonds = 0;

        // First, count total diamonds
        for (ItemStack item : p.getInventory()) {
            if (item != null && item.getType() == Material.DIAMOND) {
                totalDiamonds += item.getAmount();
                if (totalDiamonds >= amount) break;
            }
        }

        if (totalDiamonds < amount) {
            return false; // Not enough diamonds
        }

        // Then remove the required amount
        int remaining = amount;
        for (ItemStack item : p.getInventory()) {
            if (item == null || item.getType() != Material.DIAMOND) continue;

            int take = Math.min(item.getAmount(), remaining);
            item.setAmount(item.getAmount() - take);
            remaining -= take;

            if (remaining <= 0) break;
        }

        return true;
    }
}
