package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import manuxs.eventutils.EventUtils;

@SuppressWarnings("UnstableApiUsage")
public class JoinMessageCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("hide_join_message")
                .then(Commands.argument("hide", BoolArgumentType.bool())
                        .executes(ctx -> {
                            EventUtils plugin = EventUtils.getInstance();
                            boolean enabled = BoolArgumentType.getBool(ctx,"hide");
                            plugin.getConfig().set("hide_join_message",enabled);
                            plugin.saveConfig();
                            EventUtils.sendMessage(ctx.getSource().getSender(),enabled ?
                                    "<green>Join message is now hidden" :
                                    "<red>Join message is now visible");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();
    }
}
