package manuxs.eventutils.event.listener;

import manuxs.eventutils.EventUtils;
import manuxs.eventutils.manager.PlayerManager;
import org.bukkit.Server;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

public class PlayerQuitListener {
    public static void manage(PlayerQuitEvent event){
        Server server = event.getPlayer().getServer();

        PlayerManager.getPlayer(event.getPlayer().getUniqueId()).ifPresent(playerData ->
                playerData.updateData(event.getPlayer(), server));

        Team team = server.getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer());
        if(team != null){
            team.removePlayer(event.getPlayer());
        }

        if(EventUtils.getInstance().getConfig().getBoolean("hide_leave_message")) event.quitMessage(null);
    }
}
