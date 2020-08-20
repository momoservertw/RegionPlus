package tw.momocraft.regionplus.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class EntityExplode implements Listener {

    /**
     * Residence-Prevent
     *
     * @param e EntityBreakDoorEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    private void onResPreventBlockDamage(EntityExplodeEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResPrevent()) {
            if (ConfigHandler.getConfigPath().isResPreventBlockDamage()) {
                Entity entity = e.getEntity();
                if (entity instanceof Zombie) {
                    if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false)) {
                        ServerHandler.sendFeatureMessage("Residence", "Zombie", "isResPreventZombieDoor", "cancel", "destroy=false");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
