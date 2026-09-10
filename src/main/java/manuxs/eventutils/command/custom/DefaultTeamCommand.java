package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.registry.RegistryKey;
import manuxs.eventutils.EventUtils;
import manuxs.eventutils.manager.BlockedLootManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemType;
import org.bukkit.scoreboard.Team;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class DefaultTeamCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("default_team")
                .then(registerSet())
                .then(registerDisable())
                .build();
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerSet(){
        return Commands.literal("set")
                .then(Commands.argument("team", StringArgumentType.word()).suggests(DefaultTeamCommand::suggestTeams)
                        .executes(ctx -> {
                            String team_name = StringArgumentType.getString(ctx,"team");
                            Team team = ctx.getSource().getSender().getServer().getScoreboardManager().getMainScoreboard().getTeam(team_name);
                            if(team == null){
                                EventUtils.sendMessage(ctx.getSource().getSender(),"<red>This team does not exist");
                                return 0;
                            }

                            EventUtils.getInstance().getConfig().set("default_team",team_name);
                            EventUtils.getInstance().saveConfig();
                            EventUtils.sendMessage(ctx.getSource().getSender(),"<green>Team <aqua>"+team_name+"</aqua> set as default team</green>");
                            return Command.SINGLE_SUCCESS;
                        })
                );
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerDisable(){
        return Commands.literal("disable")
                .executes(ctx -> {
                    EventUtils.getInstance().getConfig().set("default_team","");
                    EventUtils.sendMessage(ctx.getSource().getSender(),"<red>Default Team disabled");
                    return Command.SINGLE_SUCCESS;
                });
    }

    private static CompletableFuture<Suggestions> suggestTeams(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder){
        ctx.getSource().getSender().getServer().getScoreboardManager().getMainScoreboard().getTeams().forEach(team -> {
            builder.suggest(team.getName());
        });
        return builder.buildFuture();
    }
}
