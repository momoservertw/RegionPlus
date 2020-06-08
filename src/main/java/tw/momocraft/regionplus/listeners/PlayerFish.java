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
        if (ConfigHandler.getConfigPath().isVEnable()) {
            if (ConfigHandler.getConfigPath().isVUseItems()) {
                if (!ConfigHandler.getConfigPath().isVItemsFishing()) {
                    Player player = e.getPlayer();
                    if (RegionUtils.bypassBorder(player, player.getLocation())) {
                        ServerHandler.debugMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "return", "border");
                        return;
                    }
                    // Allow-ItemJoin
                    if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                        if (!ConfigHandler.getConfigPath().isVItemJoin()) {
                            ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                            if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                                ServerHandler.debugMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "bypass", "Allow-ItemJoin=true");
                                return;
                            }
                        }
                    }
                    // Cancel
                    if (ConfigHandler.getConfigPath().isVUseItemsMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                    }
                    ServerHandler.debugMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "cancel", "Allow-Fishing=false");
                    e.setCancelled(true);
                }
            }
        }
    }
}
