package com.birdy.fragileLife.reactions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.time.Instant;

public class TypeReaction implements Reaction {

    private boolean active;
    private final String answer;
    private final Instant sentTimestamp;

    public TypeReaction(String reactionWord){
        answer = reactionWord;
        this.start();
        this.active = true;
        this.sentTimestamp = Instant.now();
    }

    @Override
    public void start() {
        Component reactionMessage = Component.empty()
                .append(Component.text("⚡ ", TextColor.fromHexString("#F7AA58"), TextDecoration.BOLD))
                .append(Component.text("Reaction", TextColor.fromHexString("#F7F558"), TextDecoration.BOLD))
                .append(Component.text(" | ", NamedTextColor.GRAY))
                .append(Component.text("Type the word ", TextColor.fromHexString("#00C5E9")))
                .append(Component.text(answer, TextColor.fromHexString("#76FFE7")))
                .append(Component.text(" for a random prize!", TextColor.fromHexString("#00C5E9")));
        Bukkit.getServer().sendMessage(reactionMessage);
        for (Player player : Bukkit.getOnlinePlayers()) { player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2.0f, 1.0f); }
    }

    @Override
    public void stop(Player winner) {
        this.active = false;

        Component reactionMessage;
        if(winner != null) {
            double timeSec = (Instant.now().toEpochMilli() - sentTimestamp.toEpochMilli()) / 1000.0;
            String formattedTime = String.format("%.3f", timeSec);
            String prize = "x16 Iron Ingots";
            reactionMessage = Component.empty()
                    .append(Component.text("⚡ ", TextColor.fromHexString("#F7AA58"), TextDecoration.BOLD))
                    .append(Component.text("Reaction", TextColor.fromHexString("#F7F558"), TextDecoration.BOLD))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text(winner.getName(), TextColor.fromHexString("#76FFE7")))
                    .append(Component.text(" typed the word ", TextColor.fromHexString("#00C5E9")))
                    .append(Component.text(answer, TextColor.fromHexString("#76FFE7")))
                    .append(Component.text(" in ", TextColor.fromHexString("#00C5E9")))
                    .append(Component.text(formattedTime, TextColor.fromHexString("#FFFFFF")))
                    .append(Component.text(" seconds and won ", TextColor.fromHexString("#00C5E9")))
                    .append(Component.text(prize + "!", TextColor.fromHexString("#76FFE7")));
        } else {
            reactionMessage = Component.empty()
                    .append(Component.text("⚡ ", TextColor.fromHexString("#F7AA58"), TextDecoration.BOLD))
                    .append(Component.text("Reaction", TextColor.fromHexString("#F7F558"), TextDecoration.BOLD))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Nobody typed the word ", TextColor.fromHexString("#E96565")))
                    .append(Component.text(this.getAnswer(), TextColor.fromHexString("#E91E1E")))
                    .append(Component.text(" in time!", TextColor.fromHexString("#E96565")));
        }
        Bukkit.getServer().sendMessage(reactionMessage);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getAnswer() {
        return answer;
    }
}