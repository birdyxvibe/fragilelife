package com.birdy.fragileLife.chat;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public class ChatColors {

    public static class ColorOption {
        public final TextColor color;
        public final Material material;
        public final String[] lore;

        public ColorOption(TextColor color, Material material, String[] lore) {
            this.color = color;
            this.material = material;
            this.lore = lore;
        }
    }

    public static final Map<String, ColorOption> COLORS = new LinkedHashMap<>();

    static {
        COLORS.put("White", new ColorOption(TextColor.fromHexString("#FFFFFF"), Material.WHITE_CONCRETE,
                new String[] {"Basic bitch!"}));
        COLORS.put("Charcoal", new ColorOption(TextColor.fromHexString("#575757"), Material.DEEPSLATE_TILES,
                new String[] {"Dark as my browser history"}));
        COLORS.put("Taupe", new ColorOption(TextColor.fromHexString("#CCAC93"), Material.PACKED_MUD,
                new String[] {"Like beige but with a", "choking kink"}));
        COLORS.put("Rose", new ColorOption(TextColor.fromHexString("#E3242B"), Material.NETHER_WART_BLOCK,
                new String[] {"Red, like the blood of my ex"}));
        COLORS.put("Tangerine", new ColorOption(TextColor.fromHexString("#FA8128"), Material.ORANGE_TERRACOTTA,
                new String[] {"Orange and juicy, just", "how I like my president"}));
        COLORS.put("Honey", new ColorOption(TextColor.fromHexString("#ecc734"), Material.HONEY_BLOCK,
                new String[] {"You know what else is","sticky like honey?"}));
        COLORS.put("Olive", new ColorOption(TextColor.fromHexString("#AFB83B"), Material.MOSS_BLOCK,
                new String[] {"Like the olive in your", "abusive mother's 12th", "Martini"}));
        COLORS.put("Shamrock", new ColorOption(TextColor.fromHexString("#03AC13"), Material.JUNGLE_LEAVES,
                new String[] {"The children yearn for the", "shamrock shakes"}));
        COLORS.put("Mint", new ColorOption(TextColor.fromHexString("#98FF98"), Material.SLIME_BLOCK,
                new String[] {"Just mint.", "Were you expecting", "something funny?"}));
        COLORS.put("Sapphire", new ColorOption(TextColor.fromHexString("#0492C2"), Material.CYAN_GLAZED_TERRACOTTA,
                new String[] {"Blue, like my balls on a", "Friday night"}));
        COLORS.put("Teal", new ColorOption(TextColor.fromHexString("#48AAAD"), Material.LIGHT_BLUE_CONCRETE_POWDER,
                new String[] {"Deep, wet and underwhelming"}));
        COLORS.put("Flamingo", new ColorOption(TextColor.fromHexString("#FDA4BA"), Material.PINK_CONCRETE_POWDER,
                new String[] {"Pink like a newborn baby's ass"}));
        COLORS.put("Taffy", new ColorOption(TextColor.fromHexString("#FC46AA"), Material.MAGENTA_CONCRETE,
                new String[] {"I met a stripper","named Taffy once.", "Nice ass."}));
        COLORS.put("Grape", new ColorOption(TextColor.fromHexString("#884DFF"), Material.AMETHYST_BLOCK,
                new String[] {"Purple, like lingerie", "at a granny rave"}));
    }
}
