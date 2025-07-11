package com.birdy.fragileLife.chat;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

import java.util.Map;
import java.util.LinkedHashMap;

public class ChatColors {

    public static class ColorOption {
        public final TextColor color;
        public final Material material;

        public ColorOption(TextColor color, Material material) {
            this.color = color;
            this.material = material;
        }
    }

    public static final Map<String, ColorOption> COLORS = new LinkedHashMap<>();

    static {
        COLORS.put("White", new ColorOption(TextColor.fromHexString("#FFFFFF"), Material.WHITE_CONCRETE));
        COLORS.put("Charcoal", new ColorOption(TextColor.fromHexString("#575757"), Material.DEEPSLATE_TILES));
        COLORS.put("Taupe", new ColorOption(TextColor.fromHexString("#CCAC93"), Material.PACKED_MUD));
        COLORS.put("Tomato", new ColorOption(TextColor.fromHexString("#d95d4e"), Material.NETHER_WART_BLOCK));
        COLORS.put("Citrus", new ColorOption(TextColor.fromHexString("#E86C28"), Material.ORANGE_TERRACOTTA));
        COLORS.put("Gold", new ColorOption(TextColor.fromHexString("#CFAB1F"), Material.GOLD_BLOCK));
        COLORS.put("Olive", new ColorOption(TextColor.fromHexString("#AFB83B"), Material.MOSS_BLOCK));
        COLORS.put("Pine", new ColorOption(TextColor.fromHexString("#279228"), Material.DARK_OAK_LEAVES));
        COLORS.put("Mint", new ColorOption(TextColor.fromHexString("#98FF98"), Material.SLIME_BLOCK));
        COLORS.put("Teal", new ColorOption(TextColor.fromHexString("#17c7ea"), Material.LIGHT_BLUE_CONCRETE_POWDER));
        COLORS.put("Ocean", new ColorOption(TextColor.fromHexString("#89acff"), Material.STRIPPED_WARPED_HYPHAE));
        COLORS.put("Lilac", new ColorOption(TextColor.fromHexString("#C8A2C8"), Material.PINK_CONCRETE_POWDER));
        COLORS.put("Berry", new ColorOption(TextColor.fromHexString("#B8255F"), Material.MAGENTA_CONCRETE));
        COLORS.put("Grape", new ColorOption(TextColor.fromHexString("#884DFF"), Material.AMETHYST_BLOCK));
    }
}
