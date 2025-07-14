package com.birdy.fragileLife.reactions;

import com.birdy.fragileLife.FragileLife;
import com.birdy.fragileLife.managers.TeamManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ReactionListener {

    private final ReactionManager reactionManager;
    private final TeamManager teamManager;
    private final AsyncChatEvent e;

    public ReactionListener(AsyncChatEvent e, ReactionManager reactionManager, TeamManager teamManager){
        this.e = e;
        this.reactionManager = reactionManager;
        this.teamManager = teamManager;
    }

    public boolean reactionListener(){
        Reaction reaction = reactionManager.getReaction();
        if(reaction == null) return false;
        if(!reaction.isActive()) return false;

        String plaintextMessage = PlainTextComponentSerializer.plainText().serialize(e.message());

        if(plaintextMessage.equalsIgnoreCase(reaction.getAnswer())){
            if(teamManager.getPlayerTeamColor(e.getPlayer()) == NamedTextColor.GRAY){
                e.getPlayer().sendMessage(FragileLife.pluginWarningPrefix
                        .append(Component.text("Dead players cannot win reactions!", NamedTextColor.GRAY)));
                        e.setCancelled(true);
                        return true;
            }
            e.setCancelled(true);
            reaction.stop(e.getPlayer());
            return true;
        }
        return false;
    }
}
