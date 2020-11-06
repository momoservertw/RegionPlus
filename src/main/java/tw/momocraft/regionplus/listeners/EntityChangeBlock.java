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
        if (ConfigHandler.getConfigPath().isResPrevent()) {
            if (ConfigHandler.getConfigPath().isResPreventEndermanPick()) {
                Entity entity = e.getEntity();
                if (entity instanceof Enderman) {
                    if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false)) {
                        ServerHandler.sendFeatureMessage("Residence", "Enderman", "isResPreventEndermanPick", "cancel", "destroy=false",
                                new Throwable().getStackTrace()[0]);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
