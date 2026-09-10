package manuxs.eventutils.placeholder;

import manuxs.eventutils.EventUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class CustomPlaceholders extends PlaceholderExpansion {
    private final EventUtils plugin;

    public static void registerPlaceholders(){
        new CustomPlaceholders(EventUtils.getInstance()).register();
    }

    private CustomPlaceholders(EventUtils plugin){
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "util";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getPluginMeta().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        return List.of(getIdentifier().concat("_online_no_op"),
                getIdentifier().concat("_team_size_<team>"),
                getIdentifier().concat("_team_online_<team>"),
                getIdentifier().concat("_is_timer_active"),
                getIdentifier().concat("_timer_minutes"),
                getIdentifier().concat("_timer_seconds"),
                getIdentifier().concat("_team_color"));
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("online_no_op")){
            return String.valueOf(Bukkit.getOnlinePlayers().stream().filter(player1 -> !player1.isOp()).count());
        } else if (params.startsWith("team_size_")) {
            return getTeamSize(params.replace("team_size_", ""));
        } else if (params.startsWith("team_online_")) {
            return getTeamOnlinePlayers(params.replace("team_online_", ""));
        } else if (params.equalsIgnoreCase("team_color")) {
            if(player != null && player.isOnline()){
                Team team = player.getPlayer().getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
                if(team != null){
                    return "§"+team.getColor().getChar();
                }
            }
            return "§"+ChatColor.WHITE.getChar();
        }
        return super.onRequest(player, params);
    }

    private String getTeamSize(String teamName) {
        if (teamName.isEmpty()) return "0";

        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);
        if (team != null && !team.getEntries().isEmpty()) {
            return String.valueOf(team.getSize());
        }
        return "0";
    }
    private String getTeamOnlinePlayers(String teamName) {
        if (teamName.isEmpty()) return "0";

        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);
        if (team != null && !team.getEntries().isEmpty()) {
            return String.valueOf(Bukkit.getOnlinePlayers().stream().filter(
                    player -> Objects.equals(team,Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player))
            ).count());
        }

        return "0";
    }

    @Override
    public boolean persist() {
        return true;
    }
}
