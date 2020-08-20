package tw.momocraft.regionplus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;

public class PlayerPickupItem implements Listener {

    /**
     * Visitor
     *
     * @param e EntityPickupItemEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorPickupItems(EntityPickupItemEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            if (ConfigHandler.getConfigPath().isVisPickupItems()) {
                if (e.getEntity() instanceof Player) {
                    Player player = (Player) e.getEntity();
                    String itemType = e.getItem().getName();
                    if (RegionUtils.bypassBorder(player, player.getLocation())) {
                        ServerHandler.sendFeatureMessage("Visitor", itemType, "Pickup-Items", "return", "border",
                                new Throwable().getStackTrace()[0]);
                        return;
                    }
                    // Cancel
                    if (ConfigHandler.getConfigPath().isVisPickupItemsMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorPickupItems", player);
                    }
                    ServerHandler.sendFeatureMessage("Visitor", itemType, "Pickup-Items", "cancel",
                            new Throwable().getStackTrace()[0]);
                    e.setCancelled(true);
                }
            }
        }
    }
}
