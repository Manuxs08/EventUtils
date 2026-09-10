package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import manuxs.eventutils.EventUtils;

@SuppressWarnings("UnstableApiUsage")
public class HungerCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("hunger")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            EventUtils plugin = EventUtils.getInstance();
                            boolean enabled = BoolArgumentType.getBool(ctx,"enabled");
                            plugin.getConfig().set("hunger",enabled);
                            plugin.saveConfig();
                            EventUtils.sendMessage(ctx.getSource().getSender(),enabled ?
                                    "<green>Hunger Enabled" :
                                    "<red>Hunger Disabled");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();
    }
}
