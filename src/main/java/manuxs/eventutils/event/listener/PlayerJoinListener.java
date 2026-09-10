package manuxs.eventutils.event.listener;

import manuxs.eventutils.EventUtils;
import manuxs.eventutils.manager.PlayerManager;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

public class PlayerJoinListener {
    public static void manage(PlayerJoinEvent event){
        PlayerManager.getPlayer(event.getPlayer().getUniqueId()).ifPresentOrElse(playerData -> {
            if(!playerData.team().equals("none")){
                Team team = event.getPlayer().getServer().getScoreboardManager().getMainScoreboard().getTeam(playerData.team());
                if(team != null) team.addPlayer(event.getPlayer());
            }
        },()-> PlayerManager.loadOrAddPlayer(event.getPlayer(), event.getPlayer().getServer()));

        if(EventUtils.getInstance().getConfig().getBoolean("hide_join_message")) event.joinMessage(null);
    }
}
