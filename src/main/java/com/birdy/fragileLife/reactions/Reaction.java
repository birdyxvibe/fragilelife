package com.birdy.fragileLife.reactions;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.reactions.reactionData.ReactionPrizes;
import com.birdy.fragileLife.schemas.React;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public abstract class Reaction {
    private final FragileLife plugin;
    private final ReactionManager reactionManager;

    protected boolean active;
    protected final String answer;
    protected final String text;
    protected Instant sentTimestamp;
    protected final String action;
    protected final String actionPastTense;
    protected final ReactionType type;

    public Reaction(FragileLife plugin, ReactionManager reactionManager, String[] answerText, String action, String actionPastTense, ReactionType type) {
        this.plugin = plugin;
        this.reactionManager = reactionManager;
        this.answer = answerText[0];
        this.text = answerText[1];
        this.active = true;
        this.action = action;
        this.actionPastTense = actionPastTense;
        this.type = type;
        this.start();
    }

    public void start(){
        Component reactionMessage = Component.empty()
                .append(Component.text("⚡ ", TextColor.fromHexString("#F7AA58"), TextDecoration.BOLD))
                .append(Component.text("Reaction", TextColor.fromHexString("#F7F558"), TextDecoration.BOLD))
                .append(Component.text(" | ", NamedTextColor.GRAY))
                .append(Component.text(action + " ", TextColor.fromHexString("#00C5E9")))
                .append(Component.text(text, TextColor.fromHexString("#76FFE7")))
                .append(Component.text(" for a random prize!", TextColor.fromHexString("#00C5E9")));

        Bukkit.getServer().sendMessage(reactionMessage);
        this.sentTimestamp = Instant.now();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2.0f, 1.0f);
        }
    }

    public void stop(Player winner){
        this.active = false;
        Component reactionMessage;

        if (winner != null) {
            double timeSec = (Instant.now().toEpochMilli() - sentTimestamp.toEpochMilli()) / 1000.0;
            String formattedTime = String.format("%.3f", timeSec);

            ReactionPrizes.PrizeData prizeData = ReactionPrizes.getRandomPrize();
            String prize = prizeData.name;

            Bukkit.getScheduler().runTask(plugin, () -> {
                    HashMap<Integer, ItemStack> couldNotFit = winner.getInventory().addItem(prizeData.prize);
                    if (!couldNotFit.isEmpty()) {
                        for (Map.Entry<Integer, ItemStack> entry : couldNotFit.entrySet()) {
                            winner.getWorld().dropItemNaturally(winner.getLocation(), entry.getValue());
                        }
                    }
            });

            reactionMessage = Component.empty()
                    .append(Component.text("⚡ ", TextColor.fromHexString("#F7AA58"), TextDecoration.BOLD))
                    .append(Component.text("Reaction", TextColor.fromHexString("#F7F558"), TextDecoration.BOLD))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text(winner.getName(), TextColor.fromHexString("#86FF99")))
                    .append(Component.text(" "+actionPastTense+" ", TextColor.fromHexString("#36d736")))
                    .append(Component.text(answer, TextColor.fromHexString("#86FF99")))
                    .append(Component.text(" in ", TextColor.fromHexString("#36d736")))
                    .append(Component.text(formattedTime, TextColor.fromHexString("#86FF99")))
                    .append(Component.text(" seconds and won ", TextColor.fromHexString("#36d736")))
                    .append(Component.text(prize + "!", TextColor.fromHexString("#86FF99")));

            reactionManager.addReaction(new React(winner.getUniqueId(), Double.parseDouble(formattedTime), Instant.now().toString(), type));
        } else {
            reactionMessage = Component.empty()
                    .append(Component.text("⚡ ", TextColor.fromHexString("#F7AA58"), TextDecoration.BOLD))
                    .append(Component.text("Reaction", TextColor.fromHexString("#F7F558"), TextDecoration.BOLD))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Nobody "+actionPastTense+" ", TextColor.fromHexString("#E96565")))
                    .append(Component.text(answer, TextColor.fromHexString("#E91E1E")))
                    .append(Component.text(" in time!", TextColor.fromHexString("#E96565")));
        }

        Bukkit.getServer().sendMessage(reactionMessage);
    }

    public boolean isActive() {
        return active;
    }

    public String getAnswer() {
        return answer;
    }
}
