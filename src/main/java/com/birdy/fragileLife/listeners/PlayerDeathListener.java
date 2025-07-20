package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.KillPlayerMission;
import com.birdy.fragileLife.schemas.Profile;
import com.birdy.fragileLife.store.items.ReviveTotemStoreItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerDeathListener implements Listener {

    private final TeamManager teamManager;
    private final ProfileManager profileManager;

    public PlayerDeathListener(TeamManager teamManager, ProfileManager profileManager){

        this.teamManager = teamManager;
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        String currentTeam = teamManager.getPlayerTeam(p).getName();

        ReviveTotemStoreItem reviveTotemStoreItem = new ReviveTotemStoreItem();
        ItemStack totem = reviveTotemStoreItem.generateItem();
        if (p.getInventory().contains(totem) || p.getInventory().getItemInOffHand().equals(totem)){
            removeOne(p, totem);

            p.getAttribute(Attribute.MAX_HEALTH).setBaseValue(1);
            p.setHealth(1);
            p.sendMessage(FragileLife.pluginPrefix
                    .append(Component.text("Your ", NamedTextColor.GRAY))
                    .append(totem.displayName())
                    .append(Component.text(" saved your life!")));
            e.setCancelled(true);
            return;
        }

        switch (currentTeam.toLowerCase()) {
            case "green" -> {
                teamManager.assignPlayerToTeam(p, "yellow");
                p.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30 * 2);
            }
            case "yellow" -> {
                teamManager.assignPlayerToTeam(p, "red");
                p.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30 * 2);
            }
            case "red" -> {
                teamManager.assignPlayerToTeam(p, "ghost");
                p.setGameMode(GameMode.SPECTATOR);
                e.setCancelled(true);

                World w = e.getPlayer().getWorld();
                w.strikeLightningEffect(e.getPlayer().getLocation());
            }
        }

        if (e.getEntity().getKiller() == null) return;

        Player killer = e.getEntity().getKiller();
        Profile profile = profileManager.getProfile(killer.getUniqueId());
        KillPlayerMission killPlayersMission = new KillPlayerMission();
        killPlayersMission.trigger(profile,teamManager, killer, 1);
    }

    private void removeOne(Player player, ItemStack totem) {
        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.hasItemMeta() && item.getItemMeta().lore().equals(totem.getItemMeta().lore())){
                item.setAmount(item.getAmount() - 1);
                return; // Stop after removing one
            }
        }
    }
}
