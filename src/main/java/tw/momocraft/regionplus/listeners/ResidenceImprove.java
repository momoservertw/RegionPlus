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
        if (entity instanceof Monster) {
            if (CorePlusAPI.getConditionManager().checkFlag(loc, "mobkilling", false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", entityType, "Potion-Damage", "return", "mobkilling=true",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        } else if (entity instanceof Animals) {
            if (CorePlusAPI.getConditionManager().checkFlag(loc, "animalkilling", false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", entityType, "Potion-Damage", "return", "animalkilling=true",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        } else if (entity instanceof Player) {
            Player player = (Player) entity;
            if (CorePlusAPI.getPlayerManager().isPvPEnabled(player, true)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", entityType, "Potion-Damage", "return", "PvPManager=true",
                        new Throwable().getStackTrace()[0]);
                return;
            } else {
                if (CorePlusAPI.getConditionManager().checkFlag(loc, "pvp", false)) {
                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", entityType, "Potion-Damage", "return", "pvp=true",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
            }
        } else {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", entityType, "Potion-Damage", "return", "not contains",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", entityType, "Potion-Damage", "cancel", "final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Enderman-Pickup-Block
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
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", "ENDERMAN", "Enderman-Pickup-Block", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    // Painting-Destroy
    // Item-Frame-Destroy
    // Armor-Stand-Destroy
    // Ender-Crystal-Destroy
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
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", entityType, "EntityDamageEvent", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    // Zombie-Door-Destruction
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
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", "Zombie", "Zombie-Door-Destruction", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    // Block-Damage
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDamageEvent(BlockDamageEvent e) {
        if (!ConfigHandler.getConfigPath().isResPreventBlockDamage()) {
            return;
        }
        Block block = e.getBlock();
        Location loc = block.getLocation();
        if (!CorePlusAPI.getConditionManager().isInResidence(loc)) {
            return;
        }
        if (CorePlusAPI.getConditionManager().checkFlag(loc, "destroy", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Residence-Prevent", block.getType().name(), "Block-Damage", "cancel", "destroy=false",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }
}
