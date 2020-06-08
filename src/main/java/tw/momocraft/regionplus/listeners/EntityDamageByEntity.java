package tw.momocraft.regionplus.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class EntityDamageByEntity implements Listener {

    /**
     * Residence-Prevent
     *
     * @param e EntityDamageByEntityEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onResPreventEnable(EntityDamageByEntityEvent e) {
        if (ConfigHandler.getConfigPath().isRPEnable()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            Player player = null;
            if (e.getDamager() instanceof Player) {
                player = (Player) e.getDamager();
            }
            Entity entity = e.getEntity();
            String entityType = entity.getType().name();
            switch (entityType) {
                case "PAINTING":
                    if (ConfigHandler.getConfigPath().isRPPainting()) {
                        if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false, player)) {
                            ServerHandler.debugMessage("Residence", entityType, "isRPPainting", "return", "destroy=true");
                            return;
                        }
                        break;
                    }
                    ServerHandler.debugMessage("Residence", entityType, "isRPPainting", "return", "not enabled");
                    return;
                case "ITEM_FRAME":
                    if (ConfigHandler.getConfigPath().isRPItemFrame()) {
                        if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false, player)) {
                            ServerHandler.debugMessage("Residence", entityType, "isRPItemFrame", "return", "destroy=true");
                            return;
                        }
                        break;
                    }
                    ServerHandler.debugMessage("Residence", entityType, "isRPItemFrame", "return", "not enabled");
                    return;
                case "ARMOR_STAND":
                    if (ConfigHandler.getConfigPath().isRPArmorStand()) {
                        if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false, player)) {
                            ServerHandler.debugMessage("Residence", entityType, "isRPArmorStand", "return", "destroy=true");
                            return;
                        }
                        break;
                    }
                    ServerHandler.debugMessage("Residence", entityType, "isRPArmorStand", "return", "not enabled");
                    return;
                default:
                    ServerHandler.debugMessage("Residence", entityType, "isRPPainting", "return", "not contains");
                    return;
            }
            ServerHandler.debugMessage("Residence", entityType, "isRPPainting", "cancel", "final");
            e.setCancelled(true);
        }
    }

    /**
     * Visitor
     *
     * @param e EntityDamageByEntityEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorDamageEntities(EntityDamageByEntityEvent e) {
        if (ConfigHandler.getConfigPath().isVEnable()) {
            if (ConfigHandler.getConfigPath().isVDamageEntities()) {
                if (e.getDamager() instanceof Player) {
                    Player player = (Player) e.getDamager();
                    Entity entity = e.getEntity();
                    String entityType = entity.getType().name();
                    if (RegionUtils.bypassBorder(player, entity.getLocation())) {
                        ServerHandler.debugMessage("Visitor", entityType, "Damage-Entities", "return", "border");
                        return;
                    }
                    // Allow-Player
                    if (ConfigHandler.getConfigPath().isVDamageEntitiesPlayer()) {
                        if (entity instanceof Player) {
                            ServerHandler.debugMessage("Visitor", entityType, "Damage-Entities", "bypass", "Allow-Player=true");
                            return;
                        }
                    }
                    // Cancel
                    if (ConfigHandler.getConfigPath().isVDamageEntitiesMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorDamageEntities", player);
                    }
                    ServerHandler.debugMessage("Visitor", entityType, "Damage-Entities", "cancel");
                    e.setCancelled(true);
                }
            }
        }
    }
}


