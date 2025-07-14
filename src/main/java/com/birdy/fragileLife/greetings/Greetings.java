package com.birdy.fragileLife.greetings;

import net.kyori.adventure.text.format.TextColor;

import java.util.LinkedHashMap;
import java.util.Map;

public class Greetings {

    public static final Map<String, TextColor> GREETINGS = new LinkedHashMap<>();

    static {
        GREETINGS.put("PLAYER", TextColor.fromHexString("#A8A8A8"));
        GREETINGS.put("PLAYER has joined us",TextColor.fromHexString("#55e8a0"));
        GREETINGS.put("PLAYER rolled up",TextColor.fromHexString("#DDE169"));
        GREETINGS.put("PLAYER has used join! It's super EFFECTIVE",TextColor.fromHexString("#E1605B"));
        GREETINGS.put("PLAYER has returned from the Diddy party!",TextColor.fromHexString("#50D8E1"));
        GREETINGS.put("PLAYER is coming in hot hitting the griddy!",TextColor.fromHexString("#CF7231"));
        GREETINGS.put("Bow down, it’s Jerkmate Rank 1 PLAYER.",TextColor.fromHexString("#e1d9ab"));
        GREETINGS.put("PLAYER is back. No one asked, but here we are.",TextColor.fromHexString("#9636e1"));
        GREETINGS.put("PLAYER pulled in. Expect lag and poor decisions.",TextColor.fromHexString("#e10f61"));
        GREETINGS.put("PLAYER...Heart loss imminent.",TextColor.fromHexString("#ff0000"));
        GREETINGS.put("PLAYER arrived. The heart economy is crashing.",TextColor.fromHexString("#e15d8d"));
        GREETINGS.put("PLAYER the heart tax collector has arrived.",TextColor.fromHexString("#5f50ff"));
        GREETINGS.put("Cover your assholes, PLAYER is here",TextColor.fromHexString("#e1a2ae"));
        GREETINGS.put("PLAYER is here to steal your reactions.",TextColor.fromHexString("#e19826"));
    }
}
