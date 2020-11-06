package tw.momocraft.regionplus.listeners;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.VisitorMap;

import java.util.Map;

public class PlayerBucketFill implements Listener {
    /**
     * Residence-Prevent
     *
     * @param e PlayerBucketFillEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorItemsBucket(PlayerBucketFillEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            Player player = e.getPlayer();
            String itemType = e.getItemStack().getType().name();
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
                    if (!visitorMap.isItemsBucket()) {
                        return;
                    }
                    // Location
                    loc = player.getLocation();
                    if (RegionUtils.bypassBorder(player, loc, visitorMap.getLocMaps())) {
                        ServerHandler.sendFeatureMessage("Visitor", itemType, "Use-Items: Bucket-Location", "return", groupName,
                                new Throwable().getStackTrace()[0]);
                        continue;
                    }
                    // Allow-ItemJoin
                    if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                        if (!visitorMap.isItemJoin()) {
                            ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                            if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                                ServerHandler.sendFeatureMessage("Visitor", itemType, "Use-Items: Bucket-ItemJoin", "bypass", groupName,
                                        new Throwable().getStackTrace()[0]);
                                continue;
                            }
                        }
                    }
                    // Cancel
                    if (visitorMap.isUseItemsMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                    }
                    ServerHandler.sendFeatureMessage("Visitor", itemType, "Use-Items: Bucket-Final", "cancel", groupName,
                            new Throwable().getStackTrace()[0]);
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
