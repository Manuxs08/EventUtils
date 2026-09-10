package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import manuxs.eventutils.EventUtils;

@SuppressWarnings("UnstableApiUsage")
public class ChatCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("chat")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            EventUtils plugin = EventUtils.getInstance();
                            boolean enabled = BoolArgumentType.getBool(ctx,"enabled");
                            plugin.getConfig().set("chat",enabled);
                            plugin.saveConfig();
                            EventUtils.sendMessage(ctx.getSource().getSender(),enabled ?
                                    "<green>Chat Enabled" :
                                    "<red>Chat Disabled");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();
    }
}
