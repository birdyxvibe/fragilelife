package com.birdy.fragileLife.reactions;

import org.bukkit.entity.Player;

public interface Reaction {
    void start();
    void stop(Player winner);

    boolean isActive();
    String getAnswer();

}
