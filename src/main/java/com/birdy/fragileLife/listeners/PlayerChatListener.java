package com.birdy.fragileLife.listeners;

import com.birdy.fragileLife.chat.PlayerChatEvent;
import com.birdy.fragileLife.managers.ProfileManager;
import com.birdy.fragileLife.managers.ReactionManager;
import com.birdy.fragileLife.managers.TeamManager;
import com.birdy.fragileLife.reactions.ReactionListener;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    private final TeamManager teamManager;
    private final ProfileManager profileManager;
    private final ReactionManager reactionManager;

    public PlayerChatListener(TeamManager teamManager, ProfileManager profileManager, ReactionManager reactionManager){
        this.teamManager = teamManager;
        this.profileManager = profileManager;
        this.reactionManager = reactionManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e){

        ReactionListener reactionListener = new ReactionListener(e, reactionManager);
        boolean reactionSuccess = reactionListener.reactionListener();

        if(reactionSuccess) return;

        PlayerChatEvent playerChatEvent = new PlayerChatEvent(e, teamManager, profileManager);
        playerChatEvent.playerChatEvent();
    }
}
