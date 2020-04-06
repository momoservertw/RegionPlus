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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorPickupItems(EntityPickupItemEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorPickupItems()) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                String itemType = e.getItem().getName();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.debugMessage("Visitor", itemType, "Pickup-Items", "return", "border");
                    return;
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVisitorPickupItemsMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorPickupItems", player);
                }
                ServerHandler.debugMessage("Visitor", itemType, "Pickup-Items", "cancel");
                e.setCancelled(true);
            }
        }
    }
}
