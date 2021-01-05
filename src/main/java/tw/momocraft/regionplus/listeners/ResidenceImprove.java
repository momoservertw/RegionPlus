package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class ResidenceImprove implements Listener {

    // Potion-Damage
    @EventHandler(priority = EventPriority.HIGH)
    public void onResPotionDamage(EntityDamageEvent e) {
        if (!ConfigHandler.getConfigPath().isResPrevent()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResPreventPotion()) {
            return;
        }
        Entity entity = e.getEntity();
        String entityType = entity.getType().name();
        String damageCause = e.getCause().name();
        if (!damageCause.equals("POISON") && !damageCause.equals("MAGIC")) {
            return;
        }
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(entity.getLocation());
        if (res == null) {
            return;
        }
        if (entity instanceof Monster) {
            if (res.getPermissions().has(Flags.mobkilling, false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", entityType, "Potion-Damage", "return", "mobkilling=true",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        } else if (entity instanceof Animals) {
            if (res.getPermissions().has(Flags.animalkilling, false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", entityType, "Potion-Damage", "return", "animalkilling=true",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        } else if (entity instanceof Player) {
            Player player = (Player) entity;
            if (CorePlusAPI.getPlayerManager().isPvPEnabled(player, true)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", entityType, "Potion-Damage", "return", "PvPManager=true",
                        new Throwable().getStackTrace()[0]);
                return;
            } else {
                if (res.getPermissions().has(Flags.pvp, false)) {
                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", entityType, "Potion-Damage", "return", "pvp=true",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
            }
        } else {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", entityType, "Potion-Damage", "return", "not contains",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", entityType, "Potion-Damage", "cancel", "final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Enderman-Pickup-Block
    @EventHandler(priority = EventPriority.HIGH)
    private void onResEndermanPickup(EntityChangeBlockEvent e) {
        if (!ConfigHandler.getConfigPath().isResPrevent()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResPreventEndermanPick()) {
            return;
        }
        Entity entity = e.getEntity();
        if (!(entity instanceof Enderman)) {
            return;
        }
        if (!CorePlusAPI.getConditionManager().checkFlag(entity.getLocation(), "destroy", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "ENDERMAN", "Enderman-Pickup-Block", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onResPreventBlockDamage(EntityExplodeEvent e) {
        if (!ConfigHandler.getConfigPath().isResPrevent()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResPreventBlockDamage()) {
            return;
        }
        Entity entity = e.getEntity();
        if (!(entity instanceof Zombie)) {
            return;
        }
        if (CorePlusAPI.getConditionManager().checkFlag(entity.getLocation(), "destroy", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Zombie", "isResPreventZombieDoor", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    // Zombie-Door-Destruction
    @EventHandler(priority = EventPriority.HIGH)
    private void onResZombieDoor(EntityBreakDoorEvent e) {
        if (!ConfigHandler.getConfigPath().isResPrevent()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResPreventZombieDoor()) {
            return;
        }
        Entity entity = e.getEntity();
        if (!(entity instanceof Zombie)) {
            return;
        }
        if (CorePlusAPI.getConditionManager().checkFlag(entity.getLocation(), "destroy", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Zombie", "Zombie-Door-Destruction", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }
}
