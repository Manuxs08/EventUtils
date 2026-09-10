package manuxs.eventutils.event.listener;

import manuxs.eventutils.manager.BlockedLootManager;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LootGenerateListener {
    public static void manage(LootGenerateEvent event){
        if(!BlockedLootManager.getBlockedLootItems().isEmpty()) {
            List<ItemStack> new_loot = new ArrayList<>(event.getLoot());
            new_loot.removeIf(itemStack -> BlockedLootManager.isItemBlockedFromLoot(itemStack.getType()));

            event.setLoot(new_loot);
        }
    }
}
