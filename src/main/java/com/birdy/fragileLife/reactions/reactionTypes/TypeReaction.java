package com.birdy.fragileLife.reactions.reactionTypes;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.reactions.ReactionManager;
import com.birdy.fragileLife.reactions.ReactionType;
import com.birdy.fragileLife.reactions.reactionData.ReactionWords;

import java.util.List;
import java.util.Random;

public class TypeReaction extends Reaction {

    public TypeReaction(FragileLife plugin, ProfileManager profileManager, TeamManager teamManager, ReactionManager reactionManager) {
        super(plugin, profileManager, teamManager, reactionManager, generateAnswer(), "Type the word", "typed the word", ReactionType.WORD);
    }

    private static String[] generateAnswer() {
        List<String> words = ReactionWords.REACTION_WORDS;
        String answer = words.get(new Random().nextInt(words.size()));
        return new String[] {answer, answer};
    }
}