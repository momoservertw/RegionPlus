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
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResPrevent()) {
            Player player = null;
            if (e.getDamager() instanceof Player) {
                player = (Player) e.getDamager();
            }
            Entity entity = e.getEntity();
            String entityType = entity.getType().name();
            switch (entityType) {
                case "PAINTING":
                    if (ConfigHandler.getConfigPath().isResPreventPainting()) {
                        if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false, player)) {
                            ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPainting", "return", "destroy=true",
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                        break;
                    }
                    ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPainting", "return", "not enabled",
                            new Throwable().getStackTrace()[0]);
                    return;
                case "ITEM_FRAME":
                    if (ConfigHandler.getConfigPath().isResPreventItemFrame()) {
                        if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false, player)) {
                            ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventItemFrame", "return", "destroy=true",
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                        break;
                    }
                    ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventItemFrame", "return", "not enabled",
                            new Throwable().getStackTrace()[0]);
                    return;
                case "ARMOR_STAND":
                    if (ConfigHandler.getConfigPath().isResPreventArmorStand()) {
                        if (ResidenceUtils.getBuildPerms(entity.getLocation(), "destroy", false, player)) {
                            ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventArmorStand", "return", "destroy=true",
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                        break;
                    }
                    ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventArmorStand", "return", "not enabled",
                            new Throwable().getStackTrace()[0]);
                    return;
                default:
                    ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPainting", "return", "not contains",
                            new Throwable().getStackTrace()[0]);
                    return;
            }
            ServerHandler.sendFeatureMessage("Residence", entityType, "isResPreventPainting", "cancel", "final",
                    new Throwable().getStackTrace()[0]);
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
        if (ConfigHandler.getConfigPath().isVisitor()) {
            if (ConfigHandler.getConfigPath().isVisDamageEnt()) {
                if (e.getDamager() instanceof Player) {
                    Player player = (Player) e.getDamager();
                    Entity entity = e.getEntity();
                    String entityType = entity.getType().name();
                    if (RegionUtils.bypassBorder(player, entity.getLocation())) {
                        ServerHandler.sendFeatureMessage("Visitor", entityType, "Damage-Entities", "return", "border",
                                new Throwable().getStackTrace()[0]);
                        return;
                    }
                    // Allow-Player
                    if (ConfigHandler.getConfigPath().isVisDamageEntPlayer()) {
                        if (entity instanceof Player) {
                            ServerHandler.sendFeatureMessage("Visitor", entityType, "Damage-Entities", "bypass", "Allow-Player=true",
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                    }
                    // Cancel
                    if (ConfigHandler.getConfigPath().isVisDamageEntMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorDamageEntities", player);
                    }
                    ServerHandler.sendFeatureMessage("Visitor", entityType, "Damage-Entities", "cancel",
                            new Throwable().getStackTrace()[0]);
                    e.setCancelled(true);
                }
            }
        }
    }
}


