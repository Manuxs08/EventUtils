package manuxs.eventutils.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import manuxs.eventutils.command.custom.*;

@SuppressWarnings("UnstableApiUsage")
public class MainCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("eventutils").requires(source -> source.getSender().isOp())
                .then(ReloadCommand.register())
                .then(TimerCommand.register())
                .then(PvpCommand.register())
                .then(AmbientDamageCommand.register())
                .then(MobDamageCommand.register())
                .then(ChatCommand.register())
                .then(HungerCommand.register())
                .then(SpawnCommand.register())
                .then(DefaultTeamCommand.register())
                .then(JoinMessageCommand.register())
                .then(LeaveMessageCommand.register())
                .then(EliminateDeathCommand.register())
                .then(LootCommand.register())
                .then(RecipeCommand.register())
                .then(SendSerializedDataCommand.register())
                .build();
    }
}
