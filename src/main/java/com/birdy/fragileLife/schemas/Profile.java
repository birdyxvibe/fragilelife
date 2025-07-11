package com.birdy.fragileLife.schemas;

import net.kyori.adventure.text.format.TextColor;

import java.util.Date;
import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private String chatColor = "#FFFFFF";
    private String giftCooldown = new Date().toInstant().toString();

    // Add more fields here...

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getChatColor() {
        return chatColor;
    }

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
    }

    public String getGiftCooldown() { return giftCooldown; }

    public void setGiftCooldown(String giftCooldown) {this.giftCooldown = giftCooldown;}

    // Add more helper functions here...
}
