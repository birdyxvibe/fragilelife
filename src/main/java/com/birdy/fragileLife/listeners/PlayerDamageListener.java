package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamageListener implements Listener {

    private final ProfileManager profileManager;

    public PlayerDamageListener(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player target)) return;
        if (e.isCancelled()) return;

        Player damager = null;
        boolean isExplosion = e.getCause() == DamageCause.BLOCK_EXPLOSION;

        // If this is a PvP-related entity damage, extract the damager
        if (e instanceof EntityDamageByEntityEvent edbe) {
            Entity damagerEntity = edbe.getDamager();

            if (damagerEntity instanceof Player playerDamager) {
                damager = playerDamager;
            } else if (damagerEntity instanceof Projectile projectile && projectile.getShooter() instanceof Player shooter) {
                damager = shooter;
            }
        }

        // If damage is explosion-based or comes from a player (or their projectile)
        if (damager != null || isExplosion) {
            Profile targetProfile = profileManager.getProfile(target.getUniqueId());

            // If the target is protected from PvP
            if (targetProfile.isPvpDisabled()) {
                e.setCancelled(true);
                target.sendMessage(FragileLife.pluginPrefix.append(Component.text("You are protected by dark magic. No player can harm you.", NamedTextColor.GRAY)));

                if (damager != null) {
                    damager.sendMessage(FragileLife.pluginPrefix
                            .append(Component.text(target.getName(), NamedTextColor.WHITE))
                            .append(Component.text(" is protected by dark magic. No player can harm them.", NamedTextColor.GRAY)));
                }

                return;
            }

            // If the attacker (when available) has PvP disabled
            if (damager != null) {
                Profile damagerProfile = profileManager.getProfile(damager.getUniqueId());
                if (damagerProfile.isPvpDisabled()) {
                    e.setCancelled(true);
                    damager.sendMessage(FragileLife.pluginPrefix.append(Component.text("Your horcrux prevents you from harming other players.", NamedTextColor.GRAY)));
                    return;
                }
            }
        }

        // Modify health
        double damageTaken = e.getFinalDamage();
        double currentHealth = target.getAttribute(Attribute.MAX_HEALTH).getBaseValue();

        double newHealth = Math.max(0, currentHealth - damageTaken);
        target.getAttribute(Attribute.MAX_HEALTH).setBaseValue(newHealth);
    }
}
