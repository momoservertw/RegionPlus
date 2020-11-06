package tw.momocraft.regionplus.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.VisitorMap;

import java.util.Map;

public class PlayerPickupItem implements Listener {

    /**
     * Visitor
     *
     * @param e EntityPickupItemEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorPickupItems(EntityPickupItemEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                String itemType = e.getItem().getName();
                String worldName = player.getWorld().getName();
                // To get properties.
                Map<String, VisitorMap> visitorProp = ConfigHandler.getConfigPath().getVisitorProp().get(worldName);
                VisitorMap visitorMap;
                if (visitorProp != null) {
                    Location loc;
                    // Checking every groups.
                    for (String groupName : visitorProp.keySet()) {
                        visitorMap = visitorProp.get(groupName);
                        if (!visitorMap.isPickupItems()) {
                            return;
                        }
                        // Location
                        loc = player.getLocation();
                        if (RegionUtils.bypassBorder(player, loc, visitorMap.getLocMaps())) {
                            ServerHandler.sendFeatureMessage("Visitor", itemType, "Pickup-Items: Location", "return", groupName,
                                    new Throwable().getStackTrace()[0]);
                            continue;
                        }
                        // Cancel
                        if (visitorMap.isPickupItemsMsg()) {
                            Language.sendLangMessage("Message.RegionPlus.visitorPickupItems", player);
                        }
                        ServerHandler.sendFeatureMessage("Visitor", itemType, "Pickup-Items: Final", "cancel", groupName,
                                new Throwable().getStackTrace()[0]);
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
}
