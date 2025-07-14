package com.birdy.fragileLife.schemas;

import com.birdy.fragileLife.reactions.ReactionType;

import java.util.UUID;

public class React {

    private final UUID uuid;
    private final double reactionTime;
    private final String timestamp;
    private final ReactionType type;

    public React(UUID uuid, double reactionTime, String timestamp, ReactionType type) {
        this.uuid = uuid;
        this.reactionTime = reactionTime;
        this.timestamp = timestamp;
        this.type = type;
    }

    public UUID getUUID() { return uuid; }
    public double getReactionTime() { return reactionTime; }
    public String getTimestamp() { return timestamp; }
    public ReactionType getType() { return type; }
}
