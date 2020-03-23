package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class EntityBreakDoor implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityBreakDoor(EntityBreakDoorEvent e) {
        Entity en = e.getEntity();
        if (en instanceof Zombie) {
            if (ConfigHandler.getRegionConfig().isResBlockDoor()) {
                ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(e.getEntity().getLocation());
                if (res != null) {
                    ResidencePermissions perms = res.getPermissions();
                    if (perms.has(Flags.build, false)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
