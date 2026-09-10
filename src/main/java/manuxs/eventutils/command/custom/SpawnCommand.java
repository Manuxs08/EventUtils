package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.FinePositionResolver;
import io.papermc.paper.math.FinePosition;
import manuxs.eventutils.EventUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@SuppressWarnings("UnstableApiUsage")
public class SpawnCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("spawn")
                .then(registerSet())
                .then(registerTp())
                .then(registerRadius())
                .build();
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerSet(){
        return Commands.literal("set")
                .then(Commands.argument("pos", ArgumentTypes.finePosition())
                        .executes(ctx -> {
                            FinePositionResolver resolver = ctx.getArgument("pos", FinePositionResolver.class);
                            FinePosition pos = resolver.resolve(ctx.getSource());
                            return setSpawn(ctx,pos);
                        })
                );
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerTp(){
        return Commands.literal("tp")
                .executes(SpawnCommand::teleport);
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerRadius(){
        return Commands.literal("radius")
                .then(Commands.argument("radius", IntegerArgumentType.integer(0))
                        .executes(ctx -> {
                            int radius = IntegerArgumentType.getInteger(ctx,"radius");
                            return setRadius(ctx,radius);
                        })
                );
    }

    private static int teleport(CommandContext<CommandSourceStack> ctx){

        Location location = EventUtils.getInstance().getConfig().getLocation("spawn.location");

        if(location == null || ctx.getSource().getExecutor() == null) {
            if(location == null) EventUtils.sendMessage(ctx.getSource().getSender(),"<red>No spawn defined. Use /eventutils spawn set");
            return 0;
        }

        ctx.getSource().getExecutor().teleport(location);

        return Command.SINGLE_SUCCESS;
    }

    private static int setSpawn(CommandContext<CommandSourceStack> ctx, FinePosition position){
        if(ctx.getSource().getExecutor() instanceof Player player){
            Location location = new Location(player.getWorld(),position.x(),position.y(),position.z(),player.getYaw(),1);
            EventUtils.getInstance().getConfig().set("spawn.location",location);
            EventUtils.getInstance().saveConfig();
            EventUtils.sendMessage(player,"<green>Spawn Position set to <aqua><"+position.x()+", "+position.y()+", "+position.z()+"></aqua></green>");
        }else return 0;

        return Command.SINGLE_SUCCESS;
    }

    private static int setRadius(CommandContext<CommandSourceStack> ctx, int radius){
        EventUtils.getInstance().getConfig().set("spawn.radius",radius);
        EventUtils.getInstance().saveConfig();
        EventUtils.sendMessage(ctx.getSource().getSender(),"<green>Radius set to <aqua>"+radius+"</aqua></green>");

        return Command.SINGLE_SUCCESS;
    }
}
