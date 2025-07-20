package com.birdy.fragileLife.slots;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SlotGUI {
    private static final String GUI_TITLE = "Slot Machine";
    private static final int GUI_SIZE = 27; // 3 rows
    private static final int[] WIN_SLOTS = { 11, 13, 15 }; // center row, 3 reels

    // Just these 3 slots are left empty for animation
    private static final Set<Integer> SLOT_INDICES = Set.of(11, 13, 15);

    // Decorative yellow line across center
    private static final Set<Integer> WINNING_LINE = Set.of(9, 10, 11, 12, 13, 14, 15, 16, 17);

    public static Inventory open(Player p, Profile profile) {
        Inventory gui = Bukkit.createInventory(null, GUI_SIZE, Component.text(GUI_TITLE));

        final ItemStack fillerItem = createFiller(Material.BLACK_STAINED_GLASS_PANE);
        final ItemStack winningLineItem = createFiller(Material.LIME_STAINED_GLASS_PANE);

        for (int i = 0; i < GUI_SIZE; i++) {
            if (SLOT_INDICES.contains(i)) {
                continue; // leave empty for spinning
            }
            if (WINNING_LINE.contains(i)) {
                gui.setItem(i, winningLineItem);
            } else {
                gui.setItem(i, fillerItem);
            }
        }
        gui.setItem(4, createPayoutGuideBook());
        gui.setItem(21, createButton());
        gui.setItem(23, createWagerButton(profile.getWager()));
        p.openInventory(gui);
        return gui;
    }

    public static void spin(Player player, Inventory gui, Plugin plugin, int wager, Consumer<Integer> payoutCallback) {
        Material winningMaterial = Slot.getWeightedPrize();
        List<Material> pool = new ArrayList<>(Slot.PRIZE_WEIGHTS.keySet());
        Random random = new Random();

        // Determine number of matching reels
        int matchType = random.nextInt(100);
        int matches;
        if (matchType < 25) { // 25% chance for 3-match
            matches = 3;
        } else if (matchType < 50) { // 25% chance for 2-match
            matches = 2;
        } else { // 50% chance for no match
            matches = 1;
        }

        // Randomize which wheels match
        List<Integer> wheelOrder = Arrays.asList(0, 1, 2);
        Collections.shuffle(wheelOrder);

        ItemStack[] finalItems = new ItemStack[3];
        for (int i = 0; i < 3; i++) {
            if (i < matches) {
                finalItems[wheelOrder.get(i)] = new ItemStack(winningMaterial);
            }
        }

        // Fill in non-matching reels with different symbols
        for (int i = 0; i < 3; i++) {
            if (finalItems[i] == null) {
                Material filler;
                do {
                    filler = pool.get(random.nextInt(pool.size()));
                } while (filler == winningMaterial); // avoid accidental triple match
                finalItems[i] = new ItemStack(filler);
            }
        }

        int spinTicks = 20; // how long each reel spins
        int delayPerWheel = 10; // delay between reel stops
        int tickRate = 2; // animation update rate

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1.0f);
                // Reel 1 animation
                if (ticks < spinTicks) {
                    gui.setItem(WIN_SLOTS[0], new ItemStack(getRandomSymbol(pool, random)));
                } else if (ticks == spinTicks) {
                    gui.setItem(WIN_SLOTS[0], finalItems[0]);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.7f, 0.8f);
                }

                // Reel 2 animation
                if (ticks < spinTicks + delayPerWheel) {
                    gui.setItem(WIN_SLOTS[1], new ItemStack(getRandomSymbol(pool, random)));
                } else if (ticks == spinTicks + delayPerWheel) {
                    gui.setItem(WIN_SLOTS[1], finalItems[1]);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.7f, 0.8f);
                }

                // Reel 3 animation
                if (ticks < spinTicks + delayPerWheel * 2) {
                    gui.setItem(WIN_SLOTS[2], new ItemStack(getRandomSymbol(pool, random)));
                } else if (ticks == spinTicks + delayPerWheel * 2) {
                    gui.setItem(WIN_SLOTS[2], finalItems[2]);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.7f, 0.8f);

                    // All wheels done spinning — check result
                    Material m1 = finalItems[0].getType();
                    Material m2 = finalItems[1].getType();
                    Material m3 = finalItems[2].getType();

                    int payout = 0;
                    if (m1 == m2 && m2 == m3) {
                        int multiplier = Slot.PAYOUT_MULTIPLIER.getOrDefault(m1, 0);
                        payout = wager * multiplier;
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.5f);
                        player.sendMessage(FragileLife.pluginPrefix
                                .append(Component.text("You hit x", NamedTextColor.GRAY))
                                .append(Component.text(multiplier, NamedTextColor.GREEN))
                                .append(Component.text(" multiplier. +", NamedTextColor.GRAY))
                                .append(Component.text(payout, NamedTextColor.AQUA))
                                .append(Component.text(" diamonds", NamedTextColor.GRAY)));
                        if (m1 == Material.DIAMOND) {
                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(FragileLife.pluginPrefix
                                    .append(Component.text(player.getName(), NamedTextColor.WHITE))
                                    .append(Component.text(" just won the ", NamedTextColor.GRAY))
                                    .append(Component.text("JACKPOT!", NamedTextColor.GOLD))));
                        } else if (m1 == Material.NETHER_STAR) {
                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(FragileLife.pluginPrefix
                                    .append(Component.text(player.getName(), NamedTextColor.WHITE))
                                    .append(Component.text(" just won the ", NamedTextColor.GRAY))
                                    .append(Component.text("MEGA JACKPOT!", NamedTextColor.GOLD))));
                        }
                    } else if (m1 == m2 || m1 == m3 || m2 == m3) {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.7f, 0.8f);
                        player.sendMessage(FragileLife.pluginPrefix
                                .append(Component.text("Did you know? ", NamedTextColor.GRAY))
                                .append(Component.text("Most gamblers quit right before their big win.", NamedTextColor.GRAY, TextDecoration.ITALIC)));
                    } else {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.7f, 0.8f);
                        player.sendMessage(FragileLife.pluginPrefix
                                .append(Component.text("Better luck next time!", NamedTextColor.GRAY)));
                    }

                    payoutCallback.accept(payout);
                    this.cancel();
                    return;
                }

                ticks++;
            }
        }.runTaskTimer(plugin, 0L, tickRate);
    }

    private static Material getRandomSymbol(List<Material> pool, Random rand) {
        return pool.get(rand.nextInt(pool.size()));
    }


    public static ItemStack createFiller(Material material){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.empty());

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createButton() {
        ItemStack item = new ItemStack(Material.CRIMSON_BUTTON);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("CLICK TO SPIN", NamedTextColor.GOLD));

        item.setItemMeta(meta);
        return item;

    }

    public static ItemStack createWagerButton(int amount) {
        ItemStack item = new ItemStack(Material.WARPED_BUTTON);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("CLICK TO ADJUST WAGER", NamedTextColor.GREEN));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Current Wager: ", NamedTextColor.GRAY)
                .append(Component.text(amount, NamedTextColor.AQUA))
                .append(Component.text(" diamonds", NamedTextColor.GRAY)));

        meta.lore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createPayoutGuideBook() {
        ItemStack book = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta meta = book.getItemMeta();

        meta.displayName(Component.text("Slot Machine Payouts", NamedTextColor.DARK_GREEN, TextDecoration.BOLD));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Match 3 items to win!", NamedTextColor.GRAY));

        // Sort the entries by descending multiplier
        Slot.PAYOUT_MULTIPLIER.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(entry -> {
                    String materialName = formatMaterial(entry.getKey());
                    int multiplier = entry.getValue();

                    lore.add(Component.text("• ", NamedTextColor.DARK_GREEN)
                            .append(Component.text(materialName + " ", NamedTextColor.GREEN))
                            .append(Component.text("x" + multiplier, NamedTextColor.GOLD)));
                });

        meta.lore(lore);
        book.setItemMeta(meta);
        return book;
    }

    private static String formatMaterial(Material material) {
        return Arrays.stream(material.name().toLowerCase().split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
