package tw.momocraft.regionplus.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.VisitorMap;

import java.util.Map;

public class PlayerInteractEntity implements Listener {

    /**
     * Visitor
     *
     * @param e PlayerInteractEntityEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorInteractEntities(PlayerInteractEntityEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            Player player = e.getPlayer();
            String worldName = player.getWorld().getName();
            Entity entity = e.getRightClicked();
            String entityType = entity.getType().name();
            // To get properties.
            Map<String, VisitorMap> visitorProp = ConfigHandler.getConfigPath().getVisitorProp().get(worldName);
            VisitorMap visitorMap;
            if (visitorProp != null) {
                Location loc;
                // Checking every groups.
                for (String groupName : visitorProp.keySet()) {
                    visitorMap = visitorProp.get(groupName);
                    if (!visitorMap.isResCreate()) {
                        return;
                    }
                    // Location
                    loc = player.getLocation();
                    if (RegionUtils.bypassBorder(player, loc, visitorMap.getLocMaps())) {
                        ServerHandler.sendFeatureMessage("Visitor", entityType, "Interact-Entities: Location", "return", groupName,
                                new Throwable().getStackTrace()[0]);
                        continue;
                    }
                    // Allow-NPC
                    if (entity.hasMetadata("NPC")) {
                        if (visitorMap.isInterEntNPC()) {
                            ServerHandler.sendFeatureMessage("Visitor", entityType, "Interact-Entities: NPC", "bypass", groupName,
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                    }
                    // Cancel
                    if (visitorMap.isInterEntMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorInteractEntities", player);
                    }
                    ServerHandler.sendFeatureMessage("Visitor", entityType, "Interact-Entities: Final", "cancel", groupName,
                            new Throwable().getStackTrace()[0]);
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
