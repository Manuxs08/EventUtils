package manuxs.eventutils.event.listener;

import manuxs.eventutils.manager.BlockedLootManager;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;

public class VillagerAcquireTradeListener {
    public static void manage(VillagerAcquireTradeEvent event){
        if(BlockedLootManager.isItemBlockedFromLoot(event.getRecipe().getResult().getType())){
            event.setCancelled(true);
        }
    }
}
