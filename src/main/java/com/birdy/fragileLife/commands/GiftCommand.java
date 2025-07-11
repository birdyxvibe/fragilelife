package com.birdy.fragileLife.commands;

import com.birdy.fragileLife.FragileLife;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiftCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;



            p.sendMessage(FragileLife.pluginPrefix
                    .append(Component.text("You've gifted one").color(NamedTextColor.GRAY))
                    .append(Component.text(" ♥ ").color(NamedTextColor.RED))
                    .append(Component.text("to ").color(NamedTextColor.GRAY))
                    .append(Component.text(args[0]).color(NamedTextColor.YELLOW)));


            return true;
        }
        return false;
    }
}
