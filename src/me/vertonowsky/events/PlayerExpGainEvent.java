package me.vertonowsky.events;

import me.vertonowsky.enums.JobType;
import me.vertonowsky.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerExpGainEvent extends Event {

    User u;
    JobType jobType;
    long exp;

    public PlayerExpGainEvent(User u, JobType jobType, long exp) {
        this.u = u;
        this.jobType = jobType;
        this.exp = exp;
    }

    public User getUser() {
        return u;
    }

    public JobType getJobType() {
        return jobType;
    }

    public long getExp() {
        return exp;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
