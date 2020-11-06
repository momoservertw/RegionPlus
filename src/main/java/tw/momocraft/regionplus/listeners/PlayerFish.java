package tw.momocraft.regionplus.listeners;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.VisitorMap;

import java.util.Map;

public class PlayerFish implements Listener {

    /**
     * Visitor
     *
     * @param e PlayerFishEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorItemsFishing(PlayerFishEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            Player player = e.getPlayer();
            String worldName = player.getWorld().getName();
            // To get properties.
            Map<String, VisitorMap> visitorProp = ConfigHandler.getConfigPath().getVisitorProp().get(worldName);
            VisitorMap visitorMap;
            if (visitorProp != null) {
                Location loc;
                // Checking every groups.
                for (String groupName : visitorProp.keySet()) {
                    visitorMap = visitorProp.get(groupName);
                    if (!visitorMap.isUseItems()) {
                        return;
                    }
                    if (!visitorMap.isItemsFishing()) {
                        return;
                    }
                    // Location
                    loc = player.getLocation();
                    if (RegionUtils.bypassBorder(player, loc, visitorMap.getLocMaps())) {
                        ServerHandler.sendFeatureMessage("Visitor", "FISHING_ROD", "Use-Items: Location", "return", groupName,
                                new Throwable().getStackTrace()[0]);
                        continue;
                    }
                    // Cancel
                    if (visitorMap.isUseItemsMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                    }
                    ServerHandler.sendFeatureMessage("Visitor", "FISHING_ROD", "Use-Items: Final", "cancel", groupName,
                            new Throwable().getStackTrace()[0]);
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
