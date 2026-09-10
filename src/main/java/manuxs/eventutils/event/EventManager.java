package manuxs.eventutils.event;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import manuxs.eventutils.event.listener.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.LootGenerateEvent;

public class EventManager implements Listener {

    @EventHandler
    private void entityDamageEvent(EntityDamageEvent event){
        EntityDamageListener.manage(event);
    }

    @EventHandler
    private void generateLootEvent(LootGenerateEvent event){
        LootGenerateListener.manage(event);
    }

    @EventHandler
    private void asyncChatEvent(AsyncChatEvent event){
        ChatListener.asyncChat(event);
    }

    @EventHandler
    private void playerCommandEvent(PlayerCommandPreprocessEvent event){
        ChatListener.chatCommand(event);
    }

    @EventHandler
    private void foodLevelChangeEvent(FoodLevelChangeEvent event){
        FoodLevelChangeListener.manage(event);
    }

    @EventHandler
    private void playerJoinEvent(PlayerJoinEvent event){
        PlayerJoinListener.manage(event);
    }

    @EventHandler
    private void playerQuitEvent(PlayerQuitEvent event){
        PlayerQuitListener.manage(event);
    }

    @EventHandler
    private void playerRespawnListener(PlayerRespawnEvent event){
        PlayerRespawnListener.respawn(event);
    }

    @EventHandler
    private void playerPostRespawnListener(PlayerPostRespawnEvent event){
        PlayerRespawnListener.postRespawn(event);
    }

    @EventHandler
    private void villagerAcquireTrade(VillagerAcquireTradeEvent event){
        VillagerAcquireTradeListener.manage(event);
    }

    @EventHandler
    private void serverEndTick(ServerTickEndEvent event){
        ServerTickListener.manage(event);
    }
}
