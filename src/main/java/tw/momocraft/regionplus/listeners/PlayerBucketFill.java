package tw.momocraft.regionplus.listeners;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;

public class PlayerBucketFill implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorItemsBucket(PlayerBucketFillEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorUseItems()) {
            if (!ConfigHandler.getRegionConfig().isVisitorItemsBucket()) {
                Player player = e.getPlayer();
                String itemType = e.getItemStack().getType().name();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Bucket", "return", "border");
                    return;
                }
                // Allow-ItemJoin
                if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                    if (!ConfigHandler.getRegionConfig().isVisitorItemJoin()) {
                        ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                        if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                            ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Bucket", "bypass", "Allow-ItemJoin");
                            return;
                        }
                    }
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVisitorUseItemsMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                }
                ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Bucket", "cancel", "Allow-Bucket=false");
                e.setCancelled(true);
            }
        }
    }
}
