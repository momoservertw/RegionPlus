package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import me.NoChance.PvPManager.PvPManager;
import me.NoChance.PvPManager.PvPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;

public class EntityDamage implements Listener {

    /**
     * Residence-Prevent
     *
     * @param e EntityDamageEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onResPreventPotionDamage(EntityDamageEvent e) {
        if (ConfigHandler.getConfigPath().isResPrevent()) {
            if (ConfigHandler.getConfigPath().isResPreventPotion()) {
                Entity entity = e.getEntity();
                String entityType = entity.getType().name();
                String damageCause = e.getCause().name();
                if (damageCause.equals("POISON") || damageCause.equals("MAGIC")) {
                    ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(entity.getLocation());
                    if (res != null) {
                        if (entity instanceof Monster) {
                            if (res.getPermissions().has(Flags.mobkilling, false)) {
                                ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPotion", "return", "mobkilling=true",
                                        new Throwable().getStackTrace()[0]);
                                return;
                            }
                        } else if (entity instanceof Animals) {
                            if (res.getPermissions().has(Flags.animalkilling, false)) {
                                ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPotion", "return", "animalkilling=true",
                                        new Throwable().getStackTrace()[0]);
                                return;
                            }
                        } else if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (ConfigHandler.getDepends().PvPManagerEnabled()) {
                                if (PvPlayer.get(player).hasPvPEnabled()) {
                                    ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPotion", "return", "PvPManager=true",
                                            new Throwable().getStackTrace()[0]);
                                    return;
                                }
                            } else {
                                if (res.getPermissions().has(Flags.pvp, false)) {
                                    ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPotion", "return", "pvp=true",
                                    new Throwable().getStackTrace()[0]);
                                    return;
                                }
                            }
                        } else {
                            ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPotion", "return", "not contains",
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                        ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPotion", "cancel", "final",
                                new Throwable().getStackTrace()[0]);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}


