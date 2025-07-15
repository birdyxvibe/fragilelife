package com.birdy.fragileLife.reactions.reactionTypes;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.reactions.ReactionManager;
import com.birdy.fragileLife.reactions.ReactionType;
import com.birdy.fragileLife.reactions.reactionData.ReactionWords;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UnscrambleReaction extends Reaction {

    public UnscrambleReaction(FragileLife plugin, ProfileManager profileManager, TeamManager teamManager, ReactionManager reactionManager) {
        super(plugin, profileManager, teamManager, reactionManager, generateAnswer(), "Unscramble the word", "unscrambled the word", ReactionType.UNSCRAMBLE);
    }

    private static String[] generateAnswer() {
        List<String> words = ReactionWords.REACTION_WORDS;
        String answer = words.get(new Random().nextInt(words.size()));

        List<String> letters = Arrays.asList(answer.split(""));
        Collections.shuffle(letters);

        return new String[] {answer, String.join("", letters)};
    }
}