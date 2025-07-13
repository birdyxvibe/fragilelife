package com.birdy.fragileLife.reactions.reactionData;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ReactionPrizes {

    public static class PrizeData {
        public final String name;
        public final ItemStack prize;
        public final Integer weight;

        public PrizeData(String name, ItemStack prize, Integer weight) {
            this.name = name;
            this.prize = prize;
            this.weight = weight;
        }
    }

    public static final Map<Integer, PrizeData> REACTION_PRIZES = new HashMap<>();

    static {
        REACTION_PRIZES.put(1, new PrizeData("x16 Iron Ingots", new ItemStack(Material.IRON_INGOT, 16), 2));
        REACTION_PRIZES.put(2, new PrizeData("x3 Golden Apples", new ItemStack(Material.GOLDEN_APPLE, 3), 3));
        REACTION_PRIZES.put(3, new PrizeData("x64 Oak Logs", new ItemStack(Material.OAK_LOG, 64), 3));
        REACTION_PRIZES.put(4, new PrizeData("x16 Steak", new ItemStack(Material.COOKED_BEEF, 16), 4));
        REACTION_PRIZES.put(5, new PrizeData("x1 Creeper Spawn Egg", new ItemStack(Material.CREEPER_SPAWN_EGG, 1), 1));
        REACTION_PRIZES.put(6, new PrizeData("x32 Arrows", new ItemStack(Material.ARROW, 32), 2));
        ItemStack harmingPotion = new ItemStack(Material.SPLASH_POTION, 1);
        PotionMeta meta = (PotionMeta) harmingPotion.getItemMeta();
        meta.setBasePotionType(PotionType.HARMING);
        harmingPotion.setItemMeta(meta);
        REACTION_PRIZES.put(7, new PrizeData("Splash Potion of Harming", harmingPotion, 1));
    }

    public static PrizeData getRandomPrize() {
        int totalWeight = 0;
        for (PrizeData prize : REACTION_PRIZES.values()) {
            totalWeight += prize.weight;
        }

        int randomIndex = new Random().nextInt(totalWeight);
        int currentWeight = 0;

        for (PrizeData prize : REACTION_PRIZES.values()) {
            currentWeight += prize.weight;
            if (randomIndex < currentWeight) {
                return prize;
            }
        }
        //fallback
        return new PrizeData("x16 Steak", new ItemStack(Material.COOKED_BEEF, 16), 4);
    }
}
