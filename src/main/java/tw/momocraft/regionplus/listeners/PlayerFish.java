package tw.momocraft.regionplus.listeners;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;

public class PlayerFish implements Listener {

    /**
     * Visitor
     *
     * @param e PlayerFishEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorItemsFishing(PlayerFishEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            if (ConfigHandler.getConfigPath().isVisUseItems()) {
                if (!ConfigHandler.getConfigPath().isVisItemsFishing()) {
                    Player player = e.getPlayer();
                    if (RegionUtils.bypassBorder(player, player.getLocation())) {
                        ServerHandler.sendFeatureMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "return", "border",
                                new Throwable().getStackTrace()[0]);
                        return;
                    }
                    // Allow-ItemJoin
                    if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                        if (!ConfigHandler.getConfigPath().isVisItemJoin()) {
                            ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                            if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                                ServerHandler.sendFeatureMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "bypass", "Allow-ItemJoin=true",
                                        new Throwable().getStackTrace()[0]);
                                return;
                            }
                        }
                    }
                    // Cancel
                    if (ConfigHandler.getConfigPath().isVisUseItemsMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                    }
                    ServerHandler.sendFeatureMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "cancel", "Allow-Fishing=false",
                    new Throwable().getStackTrace()[0]);
                    e.setCancelled(true);
                }
            }
        }
    }
}
