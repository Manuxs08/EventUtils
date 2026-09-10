package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import manuxs.eventutils.EventUtils;

@SuppressWarnings("UnstableApiUsage")
public class ReloadCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("reload")
                .executes(ctx -> {
                    EventUtils.getInstance().reloadConfig();
                    EventUtils.sendMessage(ctx.getSource().getSender(),"<green>Configuration Reloaded");
                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }
}
