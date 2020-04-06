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

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityChangeBlock(EntityChangeBlockEvent e) {
        if (ConfigHandler.getRegionConfig().isResPreventEnable()) {
            if (ConfigHandler.getRegionConfig().isResPreventEndermanPickup()) {
                Entity entity = e.getEntity();
                if (entity instanceof Enderman) {
                    if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                        return;
                    }
                    if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false)) {
                        ServerHandler.debugMessage("Residence", "Enderman", "isResPreventEndermanPickup", "cancel", "destroy=false");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
