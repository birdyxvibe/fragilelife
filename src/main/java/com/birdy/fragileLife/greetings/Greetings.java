package com.birdy.fragileLife.greetings;

import net.kyori.adventure.text.format.TextColor;

import java.util.LinkedHashMap;
import java.util.Map;

public class Greetings {


    public static final Map<String, TextColor> GREETINGS = new LinkedHashMap<>();

    static {
        GREETINGS.put("PLAYER has returned from wanking", TextColor.fromHexString("#FFFFFF"));
        GREETINGS.put("",TextColor.fromHexString("#FFFFFF"));

    }
}
