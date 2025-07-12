package com.birdy.fragileLife.managers;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.reactions.Reaction;
import com.birdy.fragileLife.reactions.TypeReaction;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ReactionManager {

    private final FragileLife plugin;
    private Reaction reaction;

    public ReactionManager(FragileLife plugin){
        this.plugin = plugin;
    }

    public void scheduleNextReaction(){
        int delaySeconds = 240 + new Random().nextInt(121); // Generate Time From 4 to 6 Minutes (240 to 360 Seconds)
        new BukkitRunnable() {
            @Override
            public void run() {
                if(reaction == null || !reaction.isActive()) StartReactionEvent();
                scheduleNextReaction();
            }
        }.runTaskLater(plugin, delaySeconds*20);
    }

    public void StartReactionEvent(){
        int reactRandomizer = 0; //new Random().nextInt(5);
        if (reactRandomizer == 0){
            reaction = new TypeReaction("Birdy");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if(reaction == null || !reaction.isActive()) return;
                reaction.stop(null);
            }
        }.runTaskLater(plugin, 30*20);
    }

    public Reaction getReaction(){
        return reaction;
    }
}
