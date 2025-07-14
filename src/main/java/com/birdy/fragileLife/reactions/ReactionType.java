package com.birdy.fragileLife.reactions;

public enum ReactionType {
    WORD,
    UNSCRAMBLE,
    MATH;

    @Override
    public String toString() {
        return switch (this) {
            case WORD -> "Word";
            case UNSCRAMBLE -> "Unscramble";
            case MATH -> "Math";
        };
    }
}
