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

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityBreakDoor(EntityBreakDoorEvent e) {
        if (ConfigHandler.getRegionConfig().isResPreventEnable()) {
            Entity entity = e.getEntity();
            if (entity instanceof Zombie) {
                if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                    return;
                }
                if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false)) {
                    ServerHandler.debugMessage("Residence", "Zombie", "isResPreventZombieDoor", "cancel", "destroy=false");
                    e.setCancelled(true);
                }
            }
        }
    }
}
