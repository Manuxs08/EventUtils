package manuxs.eventutils.event.listener;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import manuxs.eventutils.EventUtils;
import manuxs.eventutils.manager.TimerManager;

public class ServerTickListener {
    public static void manage(ServerTickEndEvent event){
        if(TimerManager.isActive() && System.currentTimeMillis() > TimerManager.getDuration()){
            TimerManager.stop(false,EventUtils.getInstance().getServer());
        }
    }
}
