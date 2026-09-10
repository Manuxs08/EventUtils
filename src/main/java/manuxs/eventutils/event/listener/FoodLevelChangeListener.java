package manuxs.eventutils.event.listener;

import manuxs.eventutils.EventUtils;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener {
    public static void manage(FoodLevelChangeEvent event){
        if(event.getFoodLevel() < event.getEntity().getFoodLevel() && !EventUtils.getInstance().getConfig().getBoolean("hunger"))
            event.setCancelled(true);
    }
}
