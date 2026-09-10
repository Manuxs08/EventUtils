package manuxs.eventutils.manager;

import manuxs.eventutils.EventUtils;
import manuxs.eventutils.util.PlayerData;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.*;

public class PlayerManager {
    private static final Map<UUID, PlayerData> PLAYERS = new HashMap<>();

    public static Optional<PlayerData> getPlayer(UUID uuid){
        return Optional.ofNullable(PLAYERS.get(uuid));
    }

    public static void loadOrAddPlayer(Player player, Server server){
        File player_file = new File(EventUtils.getInstance().getDataFolder(), "players/"+player.getUniqueId()+".yml");
        if(player_file.exists()){
            YamlConfiguration player_config = YamlConfiguration.loadConfiguration(player_file);
            String uuid = player_config.getString("uuid");
            if(uuid != null){
                PLAYERS.put(UUID.fromString(uuid), new PlayerData(UUID.fromString(uuid),player_config.getString("name"),player_config.getString("team")));
                String team_name = player_config.getString("team");
                if(team_name != null && !team_name.equals("none")){
                    Team team = server.getScoreboardManager().getMainScoreboard().getTeam(team_name);
                    if(team != null) team.addPlayer(player);
                }
            }
        }else {
            String default_team = EventUtils.getInstance().getConfig().getString("default_team");
            Team team = default_team != null ? server.getScoreboardManager().getMainScoreboard().getTeam(default_team) : null;
            if(team != null) team.addPlayer(player);
            if(server.getPluginManager().isPluginEnabled("LuckPerms")){
                server.dispatchCommand(server.getConsoleSender(),"lp user "+player.getName()+" parent set participant");
            }

            PLAYERS.put(player.getUniqueId(), new PlayerData(player, server));
            Location location = EventUtils.getInstance().getConfig().getLocation("spawn.location");
            if(location != null) player.teleport(location);
        }
    }

    public static void load(){
        File data_directory = new File(EventUtils.getInstance().getDataFolder(),"players");
        if(data_directory.exists()){
            File[] player_files = data_directory.listFiles();
            if(player_files != null){
                for (File player_file : Arrays.stream(player_files).toList()){
                    YamlConfiguration player_config = YamlConfiguration.loadConfiguration(player_file);
                    String uuid = player_config.getString("uuid");
                    if(uuid != null) {
                        PLAYERS.put(UUID.fromString(uuid), new PlayerData(UUID.fromString(uuid),player_config.getString("name"),player_config.getString("team")));
                    }
                }
            }
        }else {
            data_directory.mkdirs();
        }
    }

    public static void saveAll(Server server){
        server.getOnlinePlayers().forEach(player -> {
            getPlayer(player.getUniqueId()).ifPresent(playerData -> {
                playerData.updateData(player, server);
            });
            Team team = player.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
            if(team != null) team.removePlayer(player);
        });
    }

    public static void saveDataForPlayer(Player player, Server server){
        getPlayer(player.getUniqueId()).ifPresent(playerData ->
            playerData.updateData(player,server)
        );
    }
}
