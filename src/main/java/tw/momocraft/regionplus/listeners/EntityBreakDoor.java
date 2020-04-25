package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleCrawlEvent;
import me.DeeCaaD.SurvivalMechanics.PlayerToggleCrawl;
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
        if (ConfigHandler.getRegionConfig().isRPEnable()) {
            if (ConfigHandler.getRegionConfig().isRPZombieDoor()) {
                if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                    return;
                }
                Entity entity = e.getEntity();
                if (entity instanceof Zombie) {
                    if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false)) {
                        ServerHandler.debugMessage("Residence", "Zombie", "isRPZombieDoor", "cancel", "destroy=false");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
