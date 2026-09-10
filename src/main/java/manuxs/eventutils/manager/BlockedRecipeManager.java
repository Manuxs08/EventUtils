package manuxs.eventutils.manager;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import manuxs.eventutils.EventUtils;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockedRecipeManager {
    private static final List<Material> BLOCKED_RECIPE_MATERIALS = new ArrayList<>();

    public static void load(){
        EventUtils plugin = EventUtils.getInstance();
        plugin.saveResource("blocked_recipes.yml",false);

        File recipes_file = new File(plugin.getDataFolder(),"blocked_recipes.yml");
        YamlConfiguration recipes_config = YamlConfiguration.loadConfiguration(recipes_file);

        List<String> blocked_recipes = recipes_config.getStringList("items");
        BLOCKED_RECIPE_MATERIALS.clear();

        if(!blocked_recipes.isEmpty()){
            plugin.getServer().recipeIterator().forEachRemaining(recipe -> {
                if(blocked_recipes.contains(recipe.getResult().getType().toString())){
                    plugin.getServer().removeRecipe(((Keyed) recipe).getKey());
                }
            });

            blocked_recipes.forEach(item -> {
                try {
                    BLOCKED_RECIPE_MATERIALS.add(Material.valueOf(item));
                }catch (IllegalArgumentException ignored){}
            });
        }

        EventUtils.sendMessage(Bukkit.getConsoleSender(),"<green>Recipes Removed for items: "+blocked_recipes);
    }

    public static void save(){
        File recipes_file = new File(EventUtils.getInstance().getDataFolder(),"blocked_recipes.yml");
        YamlConfiguration recipes_config = YamlConfiguration.loadConfiguration(recipes_file);

        List<String> blocked_loot = new ArrayList<>();
        BLOCKED_RECIPE_MATERIALS.forEach(material -> {
            blocked_loot.add(material.toString());
        });

        recipes_config.set("items",blocked_loot);
        try {
            recipes_config.save(recipes_file);
        }catch (IOException e){
            EventUtils.sendMessage(Bukkit.getConsoleSender(),"<red>Error while trying to save blocked_loot.yml: "+e.getMessage());
        }
    }

    public static List<String> getBlockedRecipeItems(){
        List<String> items = new ArrayList<>();
        BLOCKED_RECIPE_MATERIALS.forEach(material -> items.add(material.toString()));

        return items;
    }

    public static CompletableFuture<Suggestions> suggest(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder){
        BLOCKED_RECIPE_MATERIALS.forEach(material -> builder.suggest(material.toString()));
        return builder.buildFuture();
    }

    public static boolean addRecipeBlockedItem(Material material){
        if(BLOCKED_RECIPE_MATERIALS.contains(material)) return false;

        BLOCKED_RECIPE_MATERIALS.add(material);
        return true;
    }

    public static boolean removeRecipeBlockedItem(Material removed_material){
        return BLOCKED_RECIPE_MATERIALS.removeIf(material -> material == removed_material);
    }
}
