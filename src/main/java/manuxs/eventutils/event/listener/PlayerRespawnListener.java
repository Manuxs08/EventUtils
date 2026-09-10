package manuxs.eventutils.event.listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import manuxs.eventutils.EventUtils;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.time.Duration;
import java.util.Date;

public class PlayerRespawnListener {
    public static void respawn(PlayerRespawnEvent event){
        if(EventUtils.getInstance().getConfig().getBoolean("eliminate_on_death") && !event.getPlayer().isOp())
            event.setRespawnLocation(event.getPlayer().getLocation());
    }

    public static void postRespawn(PlayerPostRespawnEvent event){
        if(EventUtils.getInstance().getConfig().getBoolean("eliminate_on_death") && !event.getPlayer().isOp()) {
            Player player = event.getPlayer();
            player.getScheduler().runDelayed(EventUtils.getInstance(),scheduledTask -> {
                if(player.isValid()) {
                    player.ban("Has sido eliminado. Gracias por participar :)", (Date) null, null, true);
                    scheduledTask.cancel();
                }
            },() -> player.ban("Has sido eliminado. Gracias por participar :)", (Date) null, null, true),200);
            player.playSound(Sound.sound().type(org.bukkit.Sound.BLOCK_END_PORTAL_SPAWN).build(), Sound.Emitter.self());
            player.showTitle(Title.title(Component.text("Has sido eliminado").decorate(TextDecoration.BOLD).color(NamedTextColor.DARK_RED),Component.empty(),
                    Title.Times.times(Duration.ofSeconds(1),Duration.ofSeconds(8),Duration.ofSeconds(1))));
            player.setGameMode(GameMode.SPECTATOR);
        }
    }
}
