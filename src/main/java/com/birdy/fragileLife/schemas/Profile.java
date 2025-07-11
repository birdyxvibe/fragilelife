package com.birdy.fragileLife.schemas;

import net.kyori.adventure.text.format.TextColor;

import java.util.Date;
import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private String chatColor = "#FFFFFF";
    private String greeting = "PLAYER";
    private String giftCooldown = new Date().toInstant().toString();
    private boolean isChatBold = false;
    private boolean isChatItalic = false;

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

    public String getGreeting() { return greeting; }
    public void setGreeting(String greeting) { this.greeting = greeting; }

    public String getGiftCooldown() { return giftCooldown; }
    public void setGiftCooldown(String giftCooldown) { this.giftCooldown = giftCooldown; }

    public boolean isChatItalic() { return isChatItalic; }

    public void setChatItalic(boolean chatItalic) { isChatItalic = chatItalic; }

    public boolean isChatBold() { return isChatBold; }

    public void setChatBold(boolean chatBold) { isChatBold = chatBold; }

    // Add more getter and setter functions here...
}
