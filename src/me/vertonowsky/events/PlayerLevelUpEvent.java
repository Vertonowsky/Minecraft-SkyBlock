package me.vertonowsky.events;

import me.vertonowsky.enums.JobType;
import me.vertonowsky.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelUpEvent extends Event {

    User u;
    JobType jobType;
    int level;

    public PlayerLevelUpEvent(User u, JobType jobType, int level) {
        this.u = u;
        this.jobType = jobType;
        this.level = level;
    }

    public User getUser() {
        return u;
    }

    public JobType getJobType() {
        return jobType;
    }

    public int getLevel() {
        return level;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}