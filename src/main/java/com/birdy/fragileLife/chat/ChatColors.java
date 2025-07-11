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
        COLORS.put("Rose", new ColorOption(TextColor.fromHexString("#E3242B"), Material.NETHER_WART_BLOCK));
        COLORS.put("Tangerine", new ColorOption(TextColor.fromHexString("#FA8128"), Material.ORANGE_TERRACOTTA));
        COLORS.put("Honey", new ColorOption(TextColor.fromHexString("#ecc734"), Material.HONEY_BLOCK));
        COLORS.put("Olive", new ColorOption(TextColor.fromHexString("#AFB83B"), Material.MOSS_BLOCK));
        COLORS.put("Shamrock", new ColorOption(TextColor.fromHexString("#03AC13"), Material.JUNGLE_LEAVES));
        COLORS.put("Mint", new ColorOption(TextColor.fromHexString("#98FF98"), Material.SLIME_BLOCK));
        COLORS.put("Sapphire", new ColorOption(TextColor.fromHexString("#0492C2"), Material.CYAN_GLAZED_TERRACOTTA));
        COLORS.put("Teal", new ColorOption(TextColor.fromHexString("#48AAAD"), Material.LIGHT_BLUE_CONCRETE_POWDER));
        COLORS.put("Flamingo", new ColorOption(TextColor.fromHexString("#FDA4BA"), Material.PINK_CONCRETE_POWDER));
        COLORS.put("Taffy", new ColorOption(TextColor.fromHexString("#FC46AA"), Material.MAGENTA_CONCRETE));
        COLORS.put("Grape", new ColorOption(TextColor.fromHexString("#884DFF"), Material.AMETHYST_BLOCK));
    }
}
