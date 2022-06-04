package me.vertonowsky.events;

import me.vertonowsky.enums.QuestState;
import me.vertonowsky.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerQuestCompleteEvent extends Event {

    User u;
    int id;
    QuestState questState;

    public PlayerQuestCompleteEvent(User u, int id, QuestState questState) {
        this.u = u;
        this.id = id;
        this.questState = questState;
    }


    public User getUser() {
        return u;
    }

    public int getQuestId() {
        return id;
    }

    public QuestState getQuestState() {
        return questState;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}