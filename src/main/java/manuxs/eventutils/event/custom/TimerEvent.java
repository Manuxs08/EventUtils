package manuxs.eventutils.event.custom;

import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TimerEvent extends Event {
    public enum State{START,FINISH}
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Server server;
    private final State state;

    public TimerEvent(Server server, State state){
        this.server = server;
        this.state = state;
    }

    public Server getServer(){
        return this.server;
    }

    public State getState(){
        return this.state;
    }

    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
