package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import manuxs.eventutils.EventUtils;

@SuppressWarnings("UnstableApiUsage")
public class MobDamageCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("mob_damage")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(ctx -> {
                            EventUtils plugin = EventUtils.getInstance();
                            boolean enabled = BoolArgumentType.getBool(ctx,"enabled");
                            plugin.getConfig().set("mob_damage",enabled);
                            plugin.saveConfig();
                            EventUtils.sendMessage(ctx.getSource().getSender(),enabled ?
                                    "<green>Mob Damage Enabled" :
                                    "<red>Mob Damage Disabled");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();
    }
}
