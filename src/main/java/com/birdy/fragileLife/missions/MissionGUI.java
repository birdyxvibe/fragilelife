package com.birdy.fragileLife.missions;

import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.missions.types.*;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MissionGUI {

    private static final String GUI_TITLE = "Missions";
    private static final int GUI_SIZE = 36;

    public static void open(Player p, ProfileManager profileManager, TeamManager teamManager){
        Inventory gui = Bukkit.createInventory(null, GUI_SIZE, Component.text(GUI_TITLE));

        // Populate GUI with Fillers
        final ItemStack fillerItem1 = createFiller(Material.GRAY_STAINED_GLASS_PANE);

        for(int i = 0; i < GUI_SIZE; i++) {
            gui.setItem(i, fillerItem1);
        }

        Profile profile = profileManager.getProfile(p.getUniqueId());
        int slot = 11;

        // Add new missions here
        List<Mission> missions = new ArrayList<>();
        missions.add(new KillMonstersMission());
        missions.add(new KillWitchMission());

        missions.add(new KillAnimalsMission());

        missions.add(new PlaceBlocksMission());

        missions.add(new ChopLogsMission());

        missions.add(new MineOreMission());
        missions.add(new MineDiamondsMission());

        missions.add(new CatchFishMission());

        missions.add(new CollectXPMission());

        missions.add(new WalkDistanceMission());

        missions.add(new WinReactionMission());

        //Red Only Missions Below
        missions.add(new KillPlayersMission());

        for(Mission m : missions) {
            if(slot == 16) slot += 3;

            // Lock red team missions from non-red team players
            if (teamManager.getPlayerTeamColor(p) != NamedTextColor.RED && m.isRedOnly()) {
                ItemStack item = new ItemStack(Material.BARRIER);
                ItemMeta meta = item.getItemMeta();
                meta.displayName(Component.text("Locked", NamedTextColor.RED, TextDecoration.BOLD));
                List<Component> lore = new ArrayList<>();
                lore.add(Component.text("Only available for players on their ", NamedTextColor.GRAY)
                        .append(Component.text("3rd life", NamedTextColor.RED)));
                meta.lore(lore);
                item.setItemMeta(meta);
                gui.setItem(slot++, item);
                continue;
            }

            ItemStack item = new ItemStack(m.getGuiMaterial());
            ItemMeta meta = item.getItemMeta();

            // Display name gradient
            MiniMessage mm = MiniMessage.miniMessage();
            Component gradientText = mm.deserialize("<u><b><gradient:#33BC49:#27F96D:#5FE291>" + m.getName() + "</gradient></b></u>").decoration(TextDecoration.ITALIC, false);
            meta.displayName(gradientText);

            // Build Lore
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Complete missions to gain soul fragments", NamedTextColor.GRAY));
            lore.add(Component.text("that can be spent in /shop", NamedTextColor.GRAY));
            lore.add(Component.empty());
            lore.add(Component.text("Information:", NamedTextColor.AQUA, TextDecoration.BOLD));
            lore.add(Component.text("* ", NamedTextColor.AQUA)
                    .append(Component.text("Objective: ", NamedTextColor.GRAY))
                    .append(Component.text(m.getDesc(), NamedTextColor.WHITE)));
            lore.add(Component.text("* ", NamedTextColor.AQUA)
                    .append(Component.text("Progress: ", NamedTextColor.GRAY))
                    .append(m.getProgressComponent(profile)));
            lore.add(Component.text("* ", NamedTextColor.AQUA)
                    .append(Component.text("Reward: ", NamedTextColor.GRAY))
                    .append(Component.text(m.getReward(), NamedTextColor.WHITE))
                    .append(Component.text(" Soul Fragment(s)", NamedTextColor.WHITE)));


            String cooldown = m.getCooldown(profile);
            if (!cooldown.isEmpty() && !cooldown.equals("now")) {
                lore.add(Component.empty());
                lore.add(Component.text("✖", NamedTextColor.RED)
                        .append(Component.text(" Cooldown: ", NamedTextColor.GRAY))
                        .append(Component.text(cooldown, NamedTextColor.WHITE)));
            }

            lore.replaceAll(component -> component.decoration(TextDecoration.ITALIC, false));
            meta.lore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            if (! m.isOnCooldown(profile)){
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                gradientText = mm.deserialize("<u><b><gradient:#FF0000:#C47272>" + m.getName() + "</gradient></b></u>");
                meta.displayName(gradientText);
            }
            item.setItemMeta(meta);
            gui.setItem(slot++, item);
        }
        p.openInventory(gui);
    }


    public static ItemStack createFiller(Material material){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.empty());

        item.setItemMeta(meta);
        return item;
    }

}
