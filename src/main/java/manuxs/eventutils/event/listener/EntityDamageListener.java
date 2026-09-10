package manuxs.eventutils.event.listener;

import manuxs.eventutils.EventUtils;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("UnstableApiUsage")
public class EntityDamageListener {
    public static void manage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            if(event.getDamageSource().getCausingEntity() == null){
                if(!EventUtils.getInstance().getConfig().getBoolean("ambient_damage")
                        && event.getDamageSource().getDamageType() != DamageType.GENERIC_KILL) event.setCancelled(true);
            } else {
                if(event.getDamageSource().getCausingEntity() instanceof Player attacker) {
                    if (!EventUtils.getInstance().getConfig().getBoolean("pvp") && !attacker.isOp())
                        event.setCancelled(true);
                }else if(!EventUtils.getInstance().getConfig().getBoolean("mob_damage")) event.setCancelled(true);
            }
        }
    }
}
