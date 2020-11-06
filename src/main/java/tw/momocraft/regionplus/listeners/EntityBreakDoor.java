package tw.momocraft.regionplus.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class EntityBreakDoor implements Listener {

    /**
     * Residence-Prevent
     *
     * @param e EntityBreakDoorEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    private void onResPreventZombieDoor(EntityBreakDoorEvent e) {
        if (ConfigHandler.getConfigPath().isResPrevent()) {
            if (ConfigHandler.getConfigPath().isResPreventZombieDoor()) {
                Entity entity = e.getEntity();
                if (entity instanceof Zombie) {
                    if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false)) {
                        ServerHandler.sendFeatureMessage("Residence", "Zombie", "isResPreventZombieDoor", "cancel", "destroy=false",
                                new Throwable().getStackTrace()[0]);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
