package manuxs.eventutils.manager;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import manuxs.eventutils.EventUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockedLootManager {
    private static final List<Material> BLOCKED_LOOT_MATERIALS = new ArrayList<>();

    public static void load(){
        EventUtils plugin = EventUtils.getInstance();
        plugin.saveResource("blocked_loot.yml",false);

        File loot_file = new File(EventUtils.getInstance().getDataFolder(),"blocked_loot.yml");
        YamlConfiguration loot_config = YamlConfiguration.loadConfiguration(loot_file);

        List<String> blocked_loot = loot_config.getStringList("items");
        BLOCKED_LOOT_MATERIALS.clear();

        blocked_loot.forEach(item -> {
            try {
                BLOCKED_LOOT_MATERIALS.add(Material.valueOf(item));
            }catch (IllegalArgumentException ignored){}
        });

        EventUtils.sendMessage(Bukkit.getConsoleSender(),"<green>Items Removed from Loot Tables: "+blocked_loot);
    }

    public static void save(){
        File loot_file = new File(EventUtils.getInstance().getDataFolder(),"blocked_loot.yml");
        YamlConfiguration loot_config = YamlConfiguration.loadConfiguration(loot_file);

        List<String> blocked_loot = new ArrayList<>();
        BLOCKED_LOOT_MATERIALS.forEach(material -> {
            blocked_loot.add(material.toString());
        });

        loot_config.set("items",blocked_loot);
        try {
            loot_config.save(loot_file);
        }catch (IOException e){
            EventUtils.sendMessage(Bukkit.getConsoleSender(),"<red>Error while trying to save blocked_loot.yml: "+e.getMessage());
        }
    }

    public static List<String> getBlockedLootItems(){
        List<String> items = new ArrayList<>();
        BLOCKED_LOOT_MATERIALS.forEach(material -> items.add(material.toString()));

        return items;
    }

    public static CompletableFuture<Suggestions> suggest(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder){
        BLOCKED_LOOT_MATERIALS.forEach(material -> builder.suggest(material.toString()));
        return builder.buildFuture();
    }

    public static boolean isItemBlockedFromLoot(Material material){
        return BLOCKED_LOOT_MATERIALS.contains(material);
    }

    public static boolean addLootBlockedItem(Material material){
        if(BLOCKED_LOOT_MATERIALS.contains(material)) return false;

        BLOCKED_LOOT_MATERIALS.add(material);
        return true;
    }

    public static boolean removeLootBlockedItem(Material removed_material){
        return BLOCKED_LOOT_MATERIALS.removeIf(material -> material == removed_material);
    }
}
