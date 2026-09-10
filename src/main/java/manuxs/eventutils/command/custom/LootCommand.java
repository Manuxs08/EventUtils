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
import org.bukkit.Material;
import org.bukkit.inventory.ItemType;

@SuppressWarnings("UnstableApiUsage")
public class LootCommand {
    public static LiteralCommandNode<CommandSourceStack> register(){
        return Commands.literal("loot")
                .then(registerBlock())
                .then(registerUnblock())
                .build();
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerBlock(){
        return Commands.literal("block")
                .then(Commands.argument("item", ArgumentTypes.resource(RegistryKey.ITEM))
                        .executes(ctx -> {
                            ItemType item = ctx.getArgument("item", ItemType.class);
                            return blockLootItem(ctx,item.createItemStack().getType());
                        })
                );
    }

    private static ArgumentBuilder<CommandSourceStack,?> registerUnblock(){
        return Commands.literal("unblock")
                .then(Commands.argument("item", StringArgumentType.string()).suggests(BlockedLootManager::suggest)
                        .executes(ctx -> {
                            String item = StringArgumentType.getString(ctx,"item");
                            return unblockLootItem(ctx,item);
                        })
                );
    }

    private static int blockLootItem(CommandContext<CommandSourceStack> ctx, Material material){

        if(BlockedLootManager.addLootBlockedItem(material)){
            EventUtils.sendMessage(ctx.getSource().getSender(),"<green>Item "+material+" removed from every loot table");
        }else {
            EventUtils.sendMessage(ctx.getSource().getSender(),"<red>This item is already blocked from loot tables");
            return 0;
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int unblockLootItem(CommandContext<CommandSourceStack> ctx, String item){

        try {
            Material material = Material.valueOf(item.toUpperCase());
            if(BlockedLootManager.removeLootBlockedItem(material)){
                EventUtils.sendMessage(ctx.getSource().getSender(),"<green>Item "+material+" added to every loot table");
            }else {
                EventUtils.sendMessage(ctx.getSource().getSender(),"<red>This item is not blocked from loot tables");
                return 0;
            }
        }catch (IllegalArgumentException ignored){
            EventUtils.sendMessage(ctx.getSource().getSender(),"<red>This item does not exist");
            return 0;
        }

        return Command.SINGLE_SUCCESS;
    }
}
