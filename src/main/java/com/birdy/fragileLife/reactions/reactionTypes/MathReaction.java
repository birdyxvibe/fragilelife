package com.birdy.fragileLife.reactions.reactionTypes;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.reactions.ReactionManager;
import com.birdy.fragileLife.reactions.ReactionType;

import java.util.Random;

public class MathReaction extends Reaction {

    public MathReaction(FragileLife plugin, ProfileManager profileManager, TeamManager teamManager, ReactionManager reactionManager) {
        super(plugin, profileManager, teamManager, reactionManager, generateAnswer(), "Solve the expression", "got the correct answer", ReactionType.MATH);
    }

    private static String[] generateAnswer() {
        Random rand = new Random();

        int numberONE = rand.nextInt(100)+1;
        int numberTWO = rand.nextInt(100)+1;

        String answer, expression;
        if(rand.nextInt(2) == 0){
            expression = numberONE + " + " + numberTWO;
            answer = ""+(numberONE + numberTWO);
        } else {
            expression = numberONE + " x " + numberTWO;
            answer = ""+(numberONE*numberTWO);
        }

        return new String[] {answer, expression};
    }
}