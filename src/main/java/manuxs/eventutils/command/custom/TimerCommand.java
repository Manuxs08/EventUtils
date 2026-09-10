package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.registry.RegistryKey;
import manuxs.eventutils.EventUtils;
import manuxs.eventutils.manager.BlockedLootManager;
import manuxs.eventutils.manager.TimerManager;
import manuxs.eventutils.util.TimerTime;
import manuxs.eventutils.util.serialized.SerializedData;
import manuxs.eventutils.util.serialized.SerializedTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemType;

@SuppressWarnings("UnstableApiUsage")
public class TimerCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("timer")
                .then(registerStart())
                .then(registerStop())
                .then(registerDescription())
                .build();
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerStart(){
        return Commands.literal("start")
                .then(Commands.argument("time", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String time = StringArgumentType.getString(ctx,"time");
                            return start(ctx,time);
                        })
                );
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerStop(){
        return Commands.literal("stop")
                .executes(TimerCommand::stop);
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerDescription(){
        return Commands.literal("description")
                .then(Commands.argument("text", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String description = StringArgumentType.getString(ctx,"text");
                            return setDescription(ctx,description);
                        })
                );
    }

    private static int start(CommandContext<CommandSourceStack> ctx, String time){
        try {
            TimerTime timerTime = new TimerTime(time);
            TimerManager.start(ctx.getSource().getSender().getServer(), timerTime);
        }catch (NumberFormatException ignored){
            EventUtils.sendMessage(ctx.getSource().getSender(),"<red>Timer format is invalid, use the correct timer format (00h 00m 00s)");
            return 0;
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int stop(CommandContext<CommandSourceStack> ctx){

        TimerManager.stop(true,ctx.getSource().getSender().getServer());

        return Command.SINGLE_SUCCESS;
    }

    private static int setDescription(CommandContext<CommandSourceStack> ctx, String description){
        TimerManager.setDescription(ctx.getSource().getSender().getServer(), description);
        return Command.SINGLE_SUCCESS;
    }
}
