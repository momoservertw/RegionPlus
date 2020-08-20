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
        if (ConfigHandler.getConfigPath().isVisitor()) {
            if (ConfigHandler.getConfigPath().isVisDropItems()) {
                Player player = e.getPlayer();
                String itemType = e.getItemDrop().getName();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.sendFeatureMessage("Visitor", itemType, "Drop-Items", "return", "border",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
                // Cancel
                if (ConfigHandler.getConfigPath().isVisDropItemsMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorDropItems", player);
                }
                ServerHandler.sendFeatureMessage("Visitor", itemType, "Drop-Items", "cancel",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }
}
