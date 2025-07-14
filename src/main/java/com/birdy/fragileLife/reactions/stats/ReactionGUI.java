package com.birdy.fragileLife.reactions.stats;

import com.birdy.fragileLife.reactions.ReactionManager;
import com.birdy.fragileLife.reactions.ReactionType;
import com.birdy.fragileLife.schemas.React;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReactionGUI {

    private static final String GUI_TITLE = "Reaction Statistics";
    private static final int GUI_SIZE = 36;

    public record ReactionGuiHolder(ReactionType type, String timeframe) implements InventoryHolder {
        @Override public Inventory getInventory() { return null; }
    }

    public static void open(Player p, ReactionManager reactionManager, ReactionType type, String timeframe){
        Inventory gui = Bukkit.createInventory(new ReactionGuiHolder(type, timeframe), GUI_SIZE, Component.text(GUI_TITLE));

        // Populate GUI with Fillers
        ItemStack fillerItem1 = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = fillerItem1.getItemMeta();
        meta.displayName(Component.empty());
        fillerItem1.setItemMeta(meta);

        for(int i = 0; i < GUI_SIZE; i++) gui.setItem(i, fillerItem1);

        ItemStack fillerItem2 = new ItemStack(Material.IRON_BARS);
        ItemMeta meta2 = fillerItem1.getItemMeta();
        meta2.displayName(Component.empty());
        fillerItem1.setItemMeta(meta2);
        gui.setItem(11, fillerItem2);
        gui.setItem(20, fillerItem2);

        TextColor bulletColor;
        Material winsItem;
        Material speedItem;

        switch (type) {
            case null -> {
                bulletColor = TextColor.fromHexString("#FFC845");
                winsItem = Material.GOLD_BLOCK;
                speedItem = Material.GOLD_INGOT;
            }
            case WORD -> {
                bulletColor = TextColor.fromHexString("#3AA76D");
                winsItem = Material.EMERALD_BLOCK;
                speedItem = Material.EMERALD;
            }
            case UNSCRAMBLE -> {
                bulletColor = TextColor.fromHexString("#F2604C");
                winsItem = Material.REDSTONE_BLOCK;
                speedItem = Material.REDSTONE;
            }
            case MATH -> {
                bulletColor = TextColor.fromHexString("#3778C2");
                winsItem = Material.DIAMOND_BLOCK;
                speedItem = Material.DIAMOND;
            }
        }
        ItemStack changeType = new ItemStack(Material.COMPASS);
        ItemMeta typeItemMeta = changeType.getItemMeta();
        typeItemMeta.displayName(Component.text("Change Reaction Type", TextColor.fromHexString("#6986CE"), TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        List<Component> typeLore = new ArrayList<>();
        typeLore.add(Component.text("▪ ", TextColor.fromHexString("#FFC845")).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Overall Reaction", type == null ? NamedTextColor.YELLOW : NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));
        typeLore.add(Component.text("▪ ", TextColor.fromHexString("#3AA76D")).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Word Reaction", type == ReactionType.WORD ? NamedTextColor.YELLOW : NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));
        typeLore.add(Component.text("▪ ", TextColor.fromHexString("#F2604C")).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unscramble Reaction", type == ReactionType.UNSCRAMBLE ? NamedTextColor.YELLOW : NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));
        typeLore.add(Component.text("▪ ", TextColor.fromHexString("#3778C2")).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Math Reaction", type == ReactionType.MATH ? NamedTextColor.YELLOW : NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));
        typeItemMeta.lore(typeLore);
        changeType.setItemMeta(typeItemMeta);
        gui.setItem(10, changeType);

        ItemStack changeTime = new ItemStack(Material.CLOCK);
        ItemMeta timeItemMeta = changeTime.getItemMeta();
        timeItemMeta.displayName(Component.text("Change Reaction Timeframe", TextColor.fromHexString("#e79f4f"), TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        List<Component> timeLore = new ArrayList<>();
        timeLore.add(Component.text("▪ ", TextColor.fromHexString("#E9C46A")).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("All-Time", timeframe.equals("none") ? NamedTextColor.YELLOW : NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));
        timeLore.add(Component.text("▪ ", TextColor.fromHexString("#9D4EDD")).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("This Month", timeframe.equals("month") ? NamedTextColor.YELLOW : NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));
        timeLore.add(Component.text("▪ ", TextColor.fromHexString("#EF476F")).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("This Week", timeframe.equals("week") ? NamedTextColor.YELLOW : NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));
        timeLore.add(Component.text("▪ ", TextColor.fromHexString("#06D6A0")).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Today", timeframe.equals("day") ? NamedTextColor.YELLOW : NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));
        timeItemMeta.lore(timeLore);
        changeTime.setItemMeta(timeItemMeta);
        gui.setItem(19, changeTime);

        ItemStack playerStats = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerStatsMeta = (SkullMeta) playerStats.getItemMeta();
        playerStatsMeta.setOwningPlayer(p);
        playerStatsMeta.displayName(MiniMessage.miniMessage().deserialize("<bold><underlined><gradient:#FF5F6D:#FFC371>Your Reaction Statistics</gradient></underlined></bold>").decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        double fastestTime = reactionManager.getUserStats(p.getUniqueId(), timeframe, type).fastestTime();
        lore.add(Component.text("* ", bulletColor).decoration(TextDecoration.ITALIC, false)
                .append(Component.text((type == null ? "Overall" : type) + " Reaction Win: ", NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(reactionManager.getUserStats(p.getUniqueId(), timeframe, type).totalWins(), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("* ", bulletColor).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Fastest "+ (type == null ? "Overall" : type) +" Reaction Win: ", NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(fastestTime == -1 ? "N/A" : String.format("%.3fs", fastestTime), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        playerStatsMeta.lore(lore);
        playerStats.setItemMeta(playerStatsMeta);
        gui.setItem(14, playerStats);

        ItemStack winsLeaderboard = new ItemStack(winsItem);
        ItemMeta winsMeta = winsLeaderboard.getItemMeta();
        winsMeta.displayName(MiniMessage.miniMessage().deserialize("<bold><gradient:#36D1DC:#5B86E5>Top " + (type == null ? "Overall" : type) + " Winners</gradient></bold>").decoration(TextDecoration.ITALIC, false));
        List<Component> winsLore = new ArrayList<>();
        List<Map.Entry<UUID, Long>> top10Wins = reactionManager.getTopWins(timeframe, type);
        if(top10Wins.isEmpty()) winsLore.add(Component.text("Nobody has won a reaction yet...", NamedTextColor.GRAY));
        int num = 1;
        for(Map.Entry<UUID, Long> entry : top10Wins) {
            TextColor placeColor;
            boolean bold = true;

            switch (num) {
                case 1 -> placeColor = TextColor.fromHexString("#FFD700");
                case 2 -> placeColor = TextColor.fromHexString("#C0C0C0");
                case 3 -> placeColor = TextColor.fromHexString("#CD7F32");
                default -> {
                    placeColor = NamedTextColor.GRAY;
                    bold = false;
                }
            }

            winsLore.add(Component.text("* ", bulletColor).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, false)
                    .append(Component.text("#"+num+" ", placeColor)).decoration(TextDecoration.BOLD, bold).decoration(TextDecoration.ITALIC, false)
                    .append(Component.text(Bukkit.getOfflinePlayer(entry.getKey()).getName() + ": ", placeColor).decoration(TextDecoration.BOLD, false).decoration(TextDecoration.ITALIC, false))
                    .append(Component.text(entry.getValue() + " Win"+(entry.getValue() == 1 ? "" : "s"), NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false).decoration(TextDecoration.ITALIC, false)));
            num++;
        }
        winsMeta.lore(winsLore);
        winsLeaderboard.setItemMeta(winsMeta);
        gui.setItem(22, winsLeaderboard);

        ItemStack speedLeaderboard = new ItemStack(speedItem);
        ItemMeta speedMeta = speedLeaderboard.getItemMeta();
        speedMeta.displayName(MiniMessage.miniMessage().deserialize("<bold><gradient:#F7971E:#FFD200>Fastest " + (type == null ? "Overall" : type) + " Winners</gradient></bold>").decoration(TextDecoration.ITALIC, false));
        List<Component> speedLore = new ArrayList<>();
        List<React> top10Speed = reactionManager.getTopSpeeds(timeframe, type);
        int num2 = 1;
        if(top10Speed.isEmpty()) speedLore.add(Component.text("Nobody has won a reaction yet...", NamedTextColor.GRAY));
        for(React react : top10Speed) {
            TextColor placeColor;
            boolean bold = true;

            switch (num2) {
                case 1 -> placeColor = TextColor.fromHexString("#FFD700");
                case 2 -> placeColor = TextColor.fromHexString("#C0C0C0");
                case 3 -> placeColor = TextColor.fromHexString("#CD7F32");
                default -> {
                    placeColor = NamedTextColor.GRAY;
                    bold = false;
                }
            }

            speedLore.add(Component.text("* ", bulletColor).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, false)
                    .append(Component.text("#"+num2+" ", placeColor)).decoration(TextDecoration.BOLD, bold).decoration(TextDecoration.ITALIC, false)
                    .append(Component.text(Bukkit.getOfflinePlayer(react.getUUID()).getName() + ": ", placeColor).decoration(TextDecoration.BOLD, false).decoration(TextDecoration.ITALIC, false))
                    .append(Component.text(String.format("%.3fs", react.getReactionTime()), NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false).decoration(TextDecoration.ITALIC, false)));
            num2++;
        }
        speedMeta.lore(speedLore);
        speedLeaderboard.setItemMeta(speedMeta);
        gui.setItem(24, speedLeaderboard);

        p.openInventory(gui);
    }
}
