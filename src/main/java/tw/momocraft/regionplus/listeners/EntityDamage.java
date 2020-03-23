package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class EntityDamage implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityDamage(EntityDamageByEntityEvent e) {
        EntityType entityType = e.getEntityType();
        if (!(e.getDamager() instanceof Player)) {
            switch (entityType) {
                case ARMOR_STAND:
                    if (ConfigHandler.getRegionConfig().isResBlockArmorStand()) {
                        return;
                    }
                    break;
                case ITEM_FRAME:
                    if (!ConfigHandler.getRegionConfig().isResBlockItemFrame()) {
                        return;
                    }
                    break;
                case PAINTING:
                    if (!ConfigHandler.getRegionConfig().isResBlockPainting()) {
                        return;
                    }
                    break;
                default:
                    return;
            }
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
