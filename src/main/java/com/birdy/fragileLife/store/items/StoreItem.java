package com.birdy.fragileLife.store.items;

import com.birdy.fragileLife.schemas.Profile;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public abstract class StoreItem {

    protected final String id;
    protected final Component name;
    protected final String desc;
    protected final String descTwo;
    protected final List<Component> lore;
    protected final int cost;
    protected final Material guiMaterial;
    protected final boolean isRedOnly;

    public StoreItem (String id, Component name, String desc, String descTwo, List<Component> lore, int cost, Material guiMaterial, boolean isRedOnly) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.descTwo = descTwo;
        this.lore = lore;
        this.cost = cost;
        this.guiMaterial = guiMaterial;
        this.isRedOnly = isRedOnly;
    }


    public int getCost() { return cost; }
    public String getDesc() { return desc; }
    public String getDescTwo() { return descTwo; }
    public boolean canAfford(Profile profile) {
        return profile.getSoulFragments() >= cost;
    }
    public void withdrawFragments(Profile profile) {
        profile.setSoulFragments(profile.getSoulFragments() - cost);
    }

    public abstract ItemStack generateItem();
    public abstract void giveItem(Profile profile, Player p);
}
