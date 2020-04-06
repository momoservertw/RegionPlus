package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import me.NoChance.PvPManager.PvPManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;

public class EntityDamage implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onResPreventPotionDamage(EntityDamageEvent e) {
        if (ConfigHandler.getRegionConfig().isResPreventEnable()) {
            if (ConfigHandler.getRegionConfig().isResPreventPotionDamage()) {
                if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                    return;
                }
                Entity entity = e.getEntity();
                String entityType = entity.getType().name();
                String damageCause = e.getCause().name();
                if (damageCause.equals("POISON") || damageCause.equals("MAGIC")) {
                    ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(e.getEntity().getLocation());
                    if (res != null) {
                        if (entity instanceof Monster) {
                            if (res.getPermissions().has(Flags.mobkilling, false)) {
                                ServerHandler.debugMessage("Residence", entityType, "isResPreventPotionDamage", "return", "mobkilling=true");
                                return;
                            }
                        } else if (entity instanceof Animals) {
                            if (res.getPermissions().has(Flags.animalkilling, false)) {
                                ServerHandler.debugMessage("Residence", entityType, "isResPreventPotionDamage", "return", "animalkilling=true");
                                return;
                            }
                        } else if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (ConfigHandler.getDepends().PvPManagerEnabled()) {
                                if (PvPManager.getInstance().getPlayerHandler().get(player).hasPvPEnabled()) {
                                    ServerHandler.debugMessage("Residence", entityType, "isResPreventPotionDamage", "return", "PvPManager=true");
                                    return;
                                }
                            } else {
                                if (res.getPermissions().has(Flags.pvp, false)) {
                                    ServerHandler.debugMessage("Residence", entityType, "isResPreventPotionDamage", "return", "pvp=true");
                                    return;
                                }
                            }
                        } else {
                            ServerHandler.debugMessage("Residence", entityType, "isResPreventPotionDamage", "return", "not contains");
                            return;
                        }
                        ServerHandler.debugMessage("Residence", entityType, "isResPreventPotionDamage", "cancel", "final");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}


