package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.*;
import com.birdy.fragileLife.schemas.Profile;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {
    private final ProfileManager profileManager;
    private final TeamManager teamManager;
    public BlockBreakListener(ProfileManager profileManager, TeamManager teamManager) {

        this.profileManager = profileManager;
        this.teamManager = teamManager;
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Profile profile = profileManager.getProfile(p.getUniqueId());
        if (p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))
        {
            p.sendMessage(FragileLife.pluginWarningPrefix
                    .append(Component.text("Silk touch is not allowed.", NamedTextColor.GRAY)));
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            e.setCancelled(true);
        }
        if (e.getBlock().getType() == Material.DIAMOND_ORE || e.getBlock().getType() == Material.DEEPSLATE_DIAMOND_ORE) {
            MineDiamondsMission mineDiamondsMission = new MineDiamondsMission();
            mineDiamondsMission.trigger(profile, teamManager, p, e);
        } else if (e.getBlock().getType() == Material.IRON_ORE || e.getBlock().getType() == Material.DEEPSLATE_IRON_ORE) {
            MineIronMission mineIronMission = new MineIronMission();
            mineIronMission.trigger(profile, teamManager, p, e);
        } else if (e.getBlock().getType() == Material.EMERALD_ORE || e.getBlock().getType() == Material.DEEPSLATE_EMERALD_ORE) {
            MineEmeraldMission mineEmeraldMission = new MineEmeraldMission();
            mineEmeraldMission.trigger(profile, teamManager, p, e);
        }
    }
}
