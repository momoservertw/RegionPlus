package tw.momocraft.regionplus.listeners;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.VisitorMap;

import java.util.Map;

public class PlayerDropItem implements Listener {

    /**
     * Visitor
     *
     * @param e PlayerDropItemEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorDropItems(PlayerDropItemEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            Player player = e.getPlayer();
            String itemType = e.getItemDrop().getName();
            String worldName = player.getWorld().getName();
            // To get properties.
            Map<String, VisitorMap> visitorProp = ConfigHandler.getConfigPath().getVisitorProp().get(worldName);
            VisitorMap visitorMap;
            if (visitorProp != null) {
                Location loc;
                // Checking every groups.
                for (String groupName : visitorProp.keySet()) {
                    visitorMap = visitorProp.get(groupName);
                    if (!visitorMap.isDropItems()) {
                        return;
                    }
                    // Location
                    loc = player.getLocation();
                    if (RegionUtils.bypassBorder(player, loc, visitorMap.getLocMaps())) {
                        ServerHandler.sendFeatureMessage("Visitor", itemType, "Drop-Items: Location", "return", groupName,
                                new Throwable().getStackTrace()[0]);
                        continue;
                    }
                    // Cancel
                    if (visitorMap.isDropItemsMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorDropItems", player);
                    }
                    ServerHandler.sendFeatureMessage("Visitor", itemType, "Drop-Items: Final", "cancel", groupName,
                            new Throwable().getStackTrace()[0]);
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
