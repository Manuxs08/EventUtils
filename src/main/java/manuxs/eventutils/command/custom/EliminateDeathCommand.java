package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import manuxs.eventutils.EventUtils;

@SuppressWarnings("UnstableApiUsage")
public class EliminateDeathCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("eliminate_on_death")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            EventUtils plugin = EventUtils.getInstance();
                            boolean enabled = BoolArgumentType.getBool(ctx,"enabled");
                            plugin.getConfig().set("eliminate_on_death",enabled);
                            plugin.saveConfig();
                            EventUtils.sendMessage(ctx.getSource().getSender(),enabled ?
                                    "<green>Eliminate On Death Enabled" :
                                    "<red>Eliminate On Death Disabled");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();
    }
}
