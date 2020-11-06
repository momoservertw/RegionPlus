package tw.momocraft.regionplus.listeners;

import org.bukkit.Location;
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
import tw.momocraft.regionplus.utils.VisitorMap;

import java.util.Map;

public class EntityDamageByEntity implements Listener {

    /**
     * Residence-Prevent
     *
     * @param e EntityDamageByEntityEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onResPreventEnable(EntityDamageByEntityEvent e) {
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
            if (e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();
                Entity entity = e.getEntity();
                String entityType = entity.getType().name();
                String worldName = player.getWorld().getName();
                // To get properties.
                Map<String, VisitorMap> visitorProp = ConfigHandler.getConfigPath().getVisitorProp().get(worldName);
                VisitorMap visitorMap;
                if (visitorProp != null) {
                    Location loc;
                    // Checking every groups.
                    for (String groupName : visitorProp.keySet()) {
                        visitorMap = visitorProp.get(groupName);
                        if (!visitorMap.isDamageEnt()) {
                            return;
                        }
                        // Location
                        loc = player.getLocation();
                        if (RegionUtils.bypassBorder(player, loc, visitorMap.getLocMaps())) {
                            ServerHandler.sendFeatureMessage("Visitor", entityType, "Damage-Entities: Location", "return", groupName,
                                    new Throwable().getStackTrace()[0]);
                            continue;
                        }
                        // Allow-Player
                        if (entity instanceof Player) {
                            if (visitorMap.isDamageEntPlayer()) {
                                ServerHandler.sendFeatureMessage("Visitor", entityType, "Damage-Entities: Player", "bypass", groupName,
                                        new Throwable().getStackTrace()[0]);
                                return;
                            }
                        }
                        // Cancel
                        if (visitorMap.isDamageEntMsg()) {
                            Language.sendLangMessage("Message.RegionPlus.visitorDamageEntities", player);
                        }
                        ServerHandler.sendFeatureMessage("Visitor", entityType, "Damage-Entities: Final", "cancel", groupName,
                                new Throwable().getStackTrace()[0]);
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
}


