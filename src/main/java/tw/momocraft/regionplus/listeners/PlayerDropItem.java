package tw.momocraft.regionplus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;

public class PlayerDropItem implements Listener {

    /**
     * Visitor
     *
     * @param e PlayerDropItemEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorDropItems(PlayerDropItemEvent e) {
        if (ConfigHandler.getConfigPath().isVEnable()) {
            if (ConfigHandler.getConfigPath().isVDropItems()) {
                Player player = e.getPlayer();
                String itemType = e.getItemDrop().getName();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.debugMessage("Visitor", itemType, "Drop-Items", "return", "border");
                    return;
                }
                // Cancel
                if (ConfigHandler.getConfigPath().isVDropItemsMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorDropItems", player);
                }
                ServerHandler.debugMessage("Visitor", itemType, "Drop-Items", "cancel");
                e.setCancelled(true);
            }
        }
    }
}
