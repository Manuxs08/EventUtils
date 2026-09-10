package manuxs.eventutils.manager;

import manuxs.eventutils.EventUtils;
import manuxs.eventutils.event.custom.TimerEvent;
import manuxs.eventutils.util.TimerTime;
import manuxs.eventutils.util.serialized.SerializedData;
import manuxs.eventutils.util.serialized.SerializedTypes;
import org.bukkit.Server;

public class TimerManager {
    private static String DESCRIPTION = "";
    private static long TIMER_DURATION_MS = -1L;

    public static boolean isActive(){
        return TIMER_DURATION_MS != -1L;
    }

    public static long getDuration(){
        return TIMER_DURATION_MS;
    }

    public static void start(Server server, TimerTime timerTime){
        TIMER_DURATION_MS = System.currentTimeMillis() + timerTime.getMillis();
        SerializedData<String> description_data = SerializedData.create("timer.description",SerializedTypes.STRING,DESCRIPTION);
        SerializedData<String> time_start = SerializedData.create("timer.time", SerializedTypes.STRING,timerTime.getFormattedText());
        EventUtils.sendSerializedData(server,description_data);
        EventUtils.sendSerializedData(server,time_start);

        TimerEvent timerEvent = new TimerEvent(server, TimerEvent.State.START);
        timerEvent.callEvent();
    }

    public static void stop(boolean hide_hud,Server server){
        TIMER_DURATION_MS = -1L;
        if(hide_hud) {
            SerializedData<String> time_stop = SerializedData.create("timer.time", SerializedTypes.STRING, "");
            EventUtils.sendSerializedData(server, time_stop);
        }

        TimerEvent timerEvent = new TimerEvent(server, TimerEvent.State.FINISH);
        timerEvent.callEvent();
    }

    public static void setDescription(Server server, String description){
        DESCRIPTION = description;
        SerializedData<String> description_data = SerializedData.create("timer.description",SerializedTypes.STRING,DESCRIPTION);
        EventUtils.sendSerializedData(server,description_data);
    }
}
