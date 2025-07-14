package com.birdy.fragileLife.reactions.stats;

import com.birdy.fragileLife.reactions.ReactionManager;
import com.birdy.fragileLife.reactions.ReactionType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ReactionGUIListener implements Listener {

    private static final String GUI_TITLE = "Reaction Statistics";
    private final ReactionManager reactionManager;

    public ReactionGUIListener(ReactionManager reactionManager){
        this.reactionManager = reactionManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (!e.getView().title().equals(Component.text(GUI_TITLE))) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null) return;

        Player p = (Player)e.getWhoClicked();

        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof ReactionGUI.ReactionGuiHolder(ReactionType type, String timeframe)) {
            if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                Component displayName = clickedItem.getItemMeta().displayName();
                if (displayName != null) {
                    String plainText = PlainTextComponentSerializer.plainText().serialize(displayName);
                    if (plainText.equalsIgnoreCase("Change Reaction Type")) {
                        ReactionType newType = switch (type) {
                            case null -> ReactionType.WORD;
                            case WORD -> ReactionType.UNSCRAMBLE;
                            case UNSCRAMBLE -> ReactionType.MATH;
                            case MATH -> null;
                        };
                        ReactionGUI.open(p, reactionManager, newType, timeframe);
                    } else if (plainText.equalsIgnoreCase("Change Reaction Timeframe")) {
                        String newTimeframe = switch (timeframe) {
                            case "none" -> "month";
                            case "month" -> "week";
                            case "week" -> "day";
                            case "day" -> "none";
                            default -> "none";  // fallback if unexpected value
                        };
                        ReactionGUI.open(p, reactionManager, type, newTimeframe);
                    }
                }
            }
        }
    }
}
