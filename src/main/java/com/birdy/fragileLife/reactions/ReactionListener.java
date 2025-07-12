package com.birdy.fragileLife.reactions;

import com.birdy.fragileLife.managers.ReactionManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ReactionListener {

    private final ReactionManager reactionManager;
    private final AsyncChatEvent e;

    public ReactionListener(AsyncChatEvent e, ReactionManager reactionManager){
        this.e = e;
        this.reactionManager = reactionManager;
    }

    public boolean reactionListener(){
        Reaction reaction = reactionManager.getReaction();
        if(reaction == null) return false;
        if(!reaction.isActive()) return false;

        String plaintextMessage = PlainTextComponentSerializer.plainText().serialize(e.message());

        if(plaintextMessage.equals(reaction.getAnswer())){
            e.setCancelled(true);
            reaction.stop(e.getPlayer());
            return true;
        }
        return false;
    }
}
