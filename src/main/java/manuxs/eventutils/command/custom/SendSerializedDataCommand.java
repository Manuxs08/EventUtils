package manuxs.eventutils.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import manuxs.eventutils.EventUtils;
import manuxs.eventutils.util.serialized.SerializedData;
import manuxs.eventutils.util.serialized.SerializedTypes;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class SendSerializedDataCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("send")
                .then(Commands.argument("players", ArgumentTypes.players())
                        .then(registerSendInteger())
                        .then(registerSendLong())
                        .then(registerSendDouble())
                        .then(registerSendBool())
                        .then(registerSendString())
                )
                .build();
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerSendBool(){
        return Commands.literal("Boolean")
                .then(Commands.argument("bool", BoolArgumentType.bool())
                        .then(Commands.argument("category", StringArgumentType.word())
                                .executes(ctx -> {
                                    PlayerSelectorArgumentResolver resolver = ctx.getArgument("players", PlayerSelectorArgumentResolver.class);
                                    List<Player> players = resolver.resolve(ctx.getSource());
                                    String category = StringArgumentType.getString(ctx,"category");
                                    boolean bool = BoolArgumentType.getBool(ctx,"bool");
                                    SerializedData<Boolean> booleanSerializedData = SerializedData.create(category, SerializedTypes.BOOL,bool);
                                    EventUtils.sendSerializedData(booleanSerializedData,players);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerSendInteger(){
        return Commands.literal("Integer")
                .then(Commands.argument("int", IntegerArgumentType.integer())
                        .then(Commands.argument("category", StringArgumentType.word())
                                .executes(ctx -> {
                                    PlayerSelectorArgumentResolver resolver = ctx.getArgument("players", PlayerSelectorArgumentResolver.class);
                                    List<Player> players = resolver.resolve(ctx.getSource());
                                    String category = StringArgumentType.getString(ctx,"category");
                                    int integer = IntegerArgumentType.getInteger(ctx,"int");
                                    SerializedData<Integer> integerSerializedData = SerializedData.create(category, SerializedTypes.INT,integer);
                                    EventUtils.sendSerializedData(integerSerializedData,players);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerSendLong(){
        return Commands.literal("Long")
                .then(Commands.argument("long", LongArgumentType.longArg())
                        .then(Commands.argument("category", StringArgumentType.word())
                                .executes(ctx -> {
                                    PlayerSelectorArgumentResolver resolver = ctx.getArgument("players", PlayerSelectorArgumentResolver.class);
                                    List<Player> players = resolver.resolve(ctx.getSource());
                                    String category = StringArgumentType.getString(ctx,"category");
                                    long l = LongArgumentType.getLong(ctx,"long");
                                    SerializedData<Long> longSerializedData = SerializedData.create(category, SerializedTypes.LONG,l);
                                    EventUtils.sendSerializedData(longSerializedData,players);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerSendDouble(){
        return Commands.literal("Double")
                .then(Commands.argument("double", DoubleArgumentType.doubleArg())
                        .then(Commands.argument("category", StringArgumentType.word())
                                .executes(ctx -> {
                                    PlayerSelectorArgumentResolver resolver = ctx.getArgument("players", PlayerSelectorArgumentResolver.class);
                                    List<Player> players = resolver.resolve(ctx.getSource());
                                    String category = StringArgumentType.getString(ctx,"category");
                                    double d = DoubleArgumentType.getDouble(ctx,"double");
                                    SerializedData<Double> doubleSerializedData = SerializedData.create(category, SerializedTypes.DOUBLE,d);
                                    EventUtils.sendSerializedData(doubleSerializedData,players);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerSendString(){
        return Commands.literal("String")
                .then(Commands.argument("string", StringArgumentType.string())
                        .then(Commands.argument("category", StringArgumentType.word())
                                .executes(ctx -> {
                                    PlayerSelectorArgumentResolver resolver = ctx.getArgument("players", PlayerSelectorArgumentResolver.class);
                                    List<Player> players = resolver.resolve(ctx.getSource());
                                    String category = StringArgumentType.getString(ctx,"category");
                                    String string = StringArgumentType.getString(ctx,"string");
                                    SerializedData<String> stringSerializedData = SerializedData.create(category, SerializedTypes.STRING,string);
                                    EventUtils.sendSerializedData(stringSerializedData,players);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );
    }
}
