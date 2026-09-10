package manuxs.eventutils;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import manuxs.eventutils.command.MainCommand;
import manuxs.eventutils.event.EventManager;
import manuxs.eventutils.manager.BlockedLootManager;
import manuxs.eventutils.manager.BlockedRecipeManager;
import manuxs.eventutils.placeholder.CustomPlaceholders;
import manuxs.eventutils.manager.PlayerManager;
import manuxs.eventutils.util.serialized.SerializedData;
import manuxs.eventutils.util.serialized.SerializedTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public final class EventUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        BlockedRecipeManager.load();
        BlockedLootManager.load();

        if(!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            sendMessage(Bukkit.getConsoleSender(),"<red>PlaceHolder API is not installed. You will not be able to use the placeholders this plugin provides.");
        }else{
            CustomPlaceholders.registerPlaceholders();
        }

        getServer().getPluginManager().registerEvents(new EventManager(),this);
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,event -> {
            event.registrar().register(MainCommand.register(), List.of("utils"));
        });

        PlayerManager.load();

        sendMessage(Bukkit.getConsoleSender(),"<white>"+"V"+this.getPluginMeta().getVersion()+" Enabled");
    }
    @Override
    public void onDisable() {
        BlockedRecipeManager.save();
        BlockedLootManager.save();
        PlayerManager.saveAll(getServer());
    }

    public static void sendMessage(CommandSender sender, String message){
        sender.sendRichMessage("<gold><bold>[EventUtils]</bold></gold> "+message);
    }

    public static void sendSerializedData(SerializedData<?> data){
        EventUtils.getInstance().getServer().broadcast(Component.text(data.serialize()));
    }

    public static void sendSerializedData(Server server, SerializedData<?> data){
        server.broadcast(Component.text(data.serialize()));
    }

    public static void sendSerializedData(SerializedData<?> data, List<Player> players){
        players.forEach(player -> player.sendRichMessage(data.serialize()));
    }

    public static EventUtils getInstance(){
        return getPlugin(EventUtils.class);
    }
}
