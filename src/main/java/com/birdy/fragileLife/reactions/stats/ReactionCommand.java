package com.birdy.fragileLife.reactions.stats;

import com.birdy.fragileLife.reactions.ReactionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReactionCommand implements CommandExecutor {
    private final ReactionManager reactionManager;

    public ReactionCommand(ReactionManager reactionManager) {
        this.reactionManager = reactionManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) return false;

        ReactionGUI.open(player, reactionManager, null, "none");
        return true;
    }
}
