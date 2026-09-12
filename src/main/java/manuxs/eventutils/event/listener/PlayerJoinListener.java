package manuxs.eventutils.event.listener;

import manuxs.eventutils.EventUtils;
import manuxs.eventutils.manager.PlayerManager;
import manuxs.eventutils.manager.TimerManager;
import manuxs.eventutils.util.TimerTime;
import manuxs.eventutils.util.serialized.SerializedData;
import manuxs.eventutils.util.serialized.SerializedTypes;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinListener {
    public static void manage(PlayerJoinEvent event){
        PlayerManager.getPlayer(event.getPlayer().getUniqueId()).ifPresentOrElse(playerData -> {
            if(!playerData.team().equals("none")){
                Team team = event.getPlayer().getServer().getScoreboardManager().getMainScoreboard().getTeam(playerData.team());
                if(team != null) team.addPlayer(event.getPlayer());
            }
        },()-> PlayerManager.loadOrAddPlayer(event.getPlayer(), event.getPlayer().getServer()));

        if(TimerManager.isActive()){
            TimerTime timerTime = new TimerTime("0s").setMillis(TimerManager.remainingDuration());
            SerializedData<String> description_data = SerializedData.create("timer.description",SerializedTypes.STRING,TimerManager.description());
            SerializedData<String> time_string = SerializedData.create("timer.time", SerializedTypes.STRING,timerTime.getFormattedText());
            EventUtils.sendSerializedData(description_data,new ArrayList<>(List.of(event.getPlayer())));
            EventUtils.sendSerializedData(time_string,new ArrayList<>(List.of(event.getPlayer())));
        }else {
            SerializedData<String> time_string = SerializedData.create("timer.time", SerializedTypes.STRING,"");
            EventUtils.sendSerializedData(time_string,new ArrayList<>(List.of(event.getPlayer())));
        }

        if(EventUtils.getInstance().getConfig().getBoolean("hide_join_message")) event.joinMessage(null);
    }
}
