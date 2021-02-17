package tw.momocraft.regionplus.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class ResidenceImprove implements Listener {

    // Potion-Damage
    @EventHandler(priority = EventPriority.HIGH)
    public void onResPotionDamage(EntityDamageEvent e) {
        if (!ConfigHandler.getConfigPath().isResPreventPotion()) {
            return;
        }
        Entity entity = e.getEntity();
        String entityType = entity.getType().name();
        String damageCause = e.getCause().name();
        if (!damageCause.equals("POISON") && !damageCause.equals("MAGIC")) {
            return;
        }
        Location loc = entity.getLocation();
        if (!CorePlusAPI.getConditionManager().isInResidence(loc)) {
            return;
        }
        // Checking the Residence permission.
        if (entity instanceof Monster) {
            if (CorePlusAPI.getConditionManager().checkFlag(loc, "mobkilling", false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", entityType, "Potion-Damage", "return", "mobkilling=true",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        // Checking the Residence permission.
        } else if (entity instanceof Animals) {
            if (CorePlusAPI.getConditionManager().checkFlag(loc, "animalkilling", false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", entityType, "Potion-Damage", "return", "animalkilling=true",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        // Checking the PvPManager status.
        } else if (entity instanceof Player) {
            Player player = (Player) entity;
            if (CorePlusAPI.getPlayerManager().isPvPEnabled(player, true)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", entityType, "Potion-Damage", "return", "PvPManager=true",
                        new Throwable().getStackTrace()[0]);
                return;
            } else {
                if (CorePlusAPI.getConditionManager().checkFlag(loc, "pvp", false)) {
                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", entityType, "Potion-Damage", "return", "pvp=true",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
            }
        } else {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", entityType, "Potion-Damage", "return", "not contains",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", entityType, "Potion-Damage", "cancel", "final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Enderman-Pickup-Block: destroy
    @EventHandler(priority = EventPriority.HIGH)
    public void onResEndermanPickup(EntityChangeBlockEvent e) {
        if (!ConfigHandler.getConfigPath().isResPreventEndermanPick()) {
            return;
        }
        Entity entity = e.getEntity();
        if (!(entity instanceof Enderman)) {
            return;
        }
        Location loc = entity.getLocation();
        if (!CorePlusAPI.getConditionManager().isInResidence(loc)) {
            return;
        }
        if (!CorePlusAPI.getConditionManager().checkFlag(entity.getLocation(), "destroy", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", "ENDERMAN", "Enderman-Pickup-Block", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    // Painting-Destroy: destroy
    // Item-Frame-Destroy: destroy
    // Armor-Stand-Destroy: destroy
    // Ender-Crystal-Destroy: destroy
    @EventHandler(priority = EventPriority.HIGH)
    public void onResEntityDamageEvent(EntityDamageEvent e) {
        if (!ConfigHandler.getConfigPath().isResPrevent()) {
            return;
        }
        Entity entity = e.getEntity();
        Location loc = entity.getLocation();
        if (!CorePlusAPI.getConditionManager().isInResidence(loc)) {
            return;
        }
        String entityType = entity.getType().name();
        switch (entityType) {
            case "PAINTING":
                if (!ConfigHandler.getConfigPath().isResPreventPainting())
                    return;
                break;
            case "ARMOR_STAND":
                if (!ConfigHandler.getConfigPath().isResPreventArmorStand())
                    return;
                break;
            case "ITEM_FRAME":
                if (!ConfigHandler.getConfigPath().isResPreventItemFrame())
                    return;
                break;
            case "ENDER_CRYSTAL":
                if (!ConfigHandler.getConfigPath().isResPreventEnderCrystal())
                    return;
                break;
            default:
                return;
        }
        if (CorePlusAPI.getConditionManager().checkFlag(entity.getLocation(), "destroy", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", entityType, "EntityDamageEvent", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    // Zombie-Door-Destruction: destroy
    @EventHandler(priority = EventPriority.HIGH)
    public void onResZombieDoor(EntityBreakDoorEvent e) {
        if (!ConfigHandler.getConfigPath().isResPreventZombieDoor()) {
            return;
        }
        Entity entity = e.getEntity();
        if (!(entity instanceof Zombie)) {
            return;
        }
        Location loc = entity.getLocation();
        if (!CorePlusAPI.getConditionManager().isInResidence(loc)) {
            return;
        }
        if (CorePlusAPI.getConditionManager().checkFlag(loc, "destroy", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", "Zombie", "Zombie-Door-Destruction", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    // Block-Damage: destroy
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDamageEvent(BlockDamageEvent e) {
        if (!ConfigHandler.getConfigPath().isResPreventBlockDamage()) {
            return;
        }
        e.setCancelled(false);
        Block block = e.getBlock();
        Location loc = block.getLocation();
        if (!CorePlusAPI.getConditionManager().isInResidence(loc)) {
            return;
        }
        if (CorePlusAPI.getConditionManager().checkFlag(loc, "destroy", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), "Residence-Prevent", block.getType().name(), "Block-Damage", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }
}
