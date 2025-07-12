package com.birdy.fragileLife.schemas;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private int soulFragments = 0;
    private String chatColor = "#FFFFFF";
    private String greeting = "PLAYER";
    private String giftCooldown = new Date().toInstant().toString();
    private boolean isChatBold = false;
    private boolean isChatItalic = false;

    private final Map<String, Integer> missionProgress = new HashMap<>();
    private final Map<String, String> missionCooldowns = new HashMap<>();

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

    public int getMissionProgress(String id) { return missionProgress.getOrDefault(id,0); }

    public void setMissionProgress(String id, int i) { this.missionProgress.put(id,i); }

    public String getMissionCooldowns(String id) { return missionCooldowns.getOrDefault(id, ""); }

    public void setMissionCooldowns(String id, String cd) { this.missionCooldowns.put(id,cd); }

    public int getSoulFragments() { return soulFragments; }

    public void setSoulFragments(int soulFragments) { this.soulFragments = soulFragments; }

    // Add more getter and setter functions here...
}
