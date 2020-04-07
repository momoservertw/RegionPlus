package tw.momocraft.regionplus.listeners;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class EntityChangeBlock implements Listener {

    /**
     * Residence-Prevent
     *
     * @param e EntityChangeBlockEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    private void onResPreventEndermanPickup(EntityChangeBlockEvent e) {
        if (ConfigHandler.getRegionConfig().isRPEnable()) {
            if (ConfigHandler.getRegionConfig().isRPEndermanPickup()) {
                if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                    return;
                }
                Entity entity = e.getEntity();
                if (entity instanceof Enderman) {
                    if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false)) {
                        ServerHandler.debugMessage("Residence", "Enderman", "isRPEndermanPickup", "cancel", "destroy=false");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
