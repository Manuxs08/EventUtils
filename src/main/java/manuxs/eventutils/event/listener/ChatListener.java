package manuxs.eventutils.event.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import manuxs.eventutils.EventUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener {
    public static void asyncChat(AsyncChatEvent event){
        if(!EventUtils.getInstance().getConfig().getBoolean("chat") && !event.getPlayer().isOp()) {
            event.getPlayer().sendActionBar(Component.text("Chat Bloqueado").color(NamedTextColor.RED));
            event.setCancelled(true);
        }
    }

    public static void chatCommand(PlayerCommandPreprocessEvent event){
        if((event.getMessage().startsWith("/me") || event.getMessage().startsWith("/minecraft:me")
                || event.getMessage().startsWith("/msg") || event.getMessage().startsWith("/minecraft:msg"))
                && !EventUtils.getInstance().getConfig().getBoolean("chat") && !event.getPlayer().isOp()) {
            event.getPlayer().sendActionBar(Component.text("Chat Bloqueado").color(NamedTextColor.RED));
            event.setCancelled(true);
        }
    }
}
