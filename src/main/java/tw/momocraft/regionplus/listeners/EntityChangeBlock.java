package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class EntityChangeBlock implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityChangeBlock(EntityChangeBlockEvent e) {
        Entity en = e.getEntity();
        if (en instanceof Enderman) {
            if (ConfigHandler.getRegionConfig().isResBlockEnderman()) {
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
