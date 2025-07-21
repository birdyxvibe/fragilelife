package com.birdy.fragileLife.slots;

import org.bukkit.Material;

import java.util.*;

public class Slot {

    public static final List<Integer> WAGER_AMOUNTS = List.of(1, 4, 8, 16, 32, 64);

    public static final Map<Material, Integer> PAYOUT_MULTIPLIER = Map.of(
            Material.ROTTEN_FLESH, 1,
            Material.COAL, 2,
            Material.IRON_INGOT, 3,
            Material.GOLD_INGOT, 4,
            Material.BLAZE_ROD, 5,
            Material.EMERALD, 7,
            Material.SLIME_BALL, 10,
            Material.DIAMOND, 25,
            Material.NETHER_STAR, 50
    );

    public static final Map<Material, Integer> PRIZE_WEIGHTS = Map.of(
            Material.ROTTEN_FLESH, 60,
            Material.COAL, 45,
            Material.IRON_INGOT, 30,
            Material.GOLD_INGOT, 25,
            Material.BLAZE_ROD, 15,
            Material.EMERALD, 10,
            Material.SLIME_BALL, 8,
            Material.DIAMOND, 4,
            Material.NETHER_STAR, 1
    );

    public static int getNextWager(int currentWager) {
        int index = Slot.WAGER_AMOUNTS.indexOf(currentWager);
        if (index == -1 || index == Slot.WAGER_AMOUNTS.size() - 1) {
            return Slot.WAGER_AMOUNTS.getFirst(); // wrap around to start
        } else {
            return Slot.WAGER_AMOUNTS.get(index + 1);
        }
    }

    public static Material getWeightedPrize() {
        int totalWeight = PRIZE_WEIGHTS.values().stream().mapToInt(i -> i).sum();
        int r = new Random().nextInt(totalWeight);
        int runningWeight = 0;

        for (Map.Entry<Material, Integer> entry : PRIZE_WEIGHTS.entrySet()) {
            runningWeight += entry.getValue();
            if (r < runningWeight) {
                return entry.getKey();
            }
        }
        return Material.ROTTEN_FLESH; // fallback (should never hit)
    }


}
