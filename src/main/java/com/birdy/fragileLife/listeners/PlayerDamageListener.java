package com.birdy.fragileLife.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player p){

            double damageTaken = e.getDamage();
            double currentHealth = p.getAttribute(Attribute.MAX_HEALTH).getBaseValue();

            double newHealth = Math.max(0, currentHealth - damageTaken);
            p.getAttribute(Attribute.MAX_HEALTH).setBaseValue((newHealth));
        }
    }
}
