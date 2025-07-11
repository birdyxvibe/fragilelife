package com.birdy.fragileLife.schemas;

import net.kyori.adventure.text.format.TextColor;

import java.util.Date;
import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private String chatColor = "#FFFFFF";
    private Date giftCooldown = new Date();

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

    public Date getGiftCooldown() { return giftCooldown; }

    public void setGiftCooldown(Date giftCooldown) {this.giftCooldown = giftCooldown;}

    // Add more helper functions here...
}
