package manuxs.eventutils.util;

import manuxs.eventutils.EventUtils;
import manuxs.eventutils.util.serialized.SerializedType;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private String name;
    private String team;

    public PlayerData(Player player, Server server){
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        Team player_team = server.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
        this.team = player_team != null ? player_team.getName() : "none";

        File player_file = new File(EventUtils.getInstance().getDataFolder(), "players/"+this.uuid+".yml");
        try {
            player_file.createNewFile();
            YamlConfiguration player_config = YamlConfiguration.loadConfiguration(player_file);
            player_config.set("uuid",this.uuid.toString());
            player_config.set("name",this.name);
            player_config.set("team",this.team);

            try {
                player_config.save(player_file);
            }catch (IOException e){
                EventUtils.sendMessage(Bukkit.getServer().getConsoleSender(),"<red>Data file for player "+this.name+" could not be created: "+e.getMessage());
            }
        }catch (IOException ignored){}
    }

    public PlayerData(UUID uuid, String name, String team){
        this.uuid = uuid;
        this.name = name;
        this.team = team;
    }

    public void updateData(Player player, Server server){
        this.name = player.getName();
        Team player_team = server.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
        this.team = player_team != null ? player_team.getName() : "none";

        File player_file = new File(EventUtils.getInstance().getDataFolder(),"players/"+this.uuid()+".yml");
        try {
            player_file.createNewFile();
            YamlConfiguration player_config = YamlConfiguration.loadConfiguration(player_file);
            player_config.set("uuid",this.uuid.toString());
            player_config.set("name",this.name);
            player_config.set("team",this.team);

            try {
                player_config.save(player_file);
            }catch (IOException ignored){
                EventUtils.sendMessage(Bukkit.getServer().getConsoleSender(),"<red>Data for player "+this.name+" could not be saved");
            }
        }catch (IOException e){
            EventUtils.sendMessage(Bukkit.getServer().getConsoleSender(),"<red>Data file for player "+this.name+" could not be created: "+e.getMessage());
        }
    }

    public UUID uuid() {
        return this.uuid;
    }

    public String name(){
        return this.name;
    }

    public String team(){
        return this.team;
    }

    public void setTeam(@Nullable Team team){
        this.team = team != null ? team.getName() : "none";
    }

    public boolean hasTeam(){
        return !this.team.equals("none");
    }
}
