package tw.momocraft.regionplus.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;

public class PlayerInteractEntity implements Listener {

    /**
     * Visitor
     *
     * @param e PlayerInteractEntityEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorInteractEntities(PlayerInteractEntityEvent e) {
        if (ConfigHandler.getRegionConfig().isVEnable()) {
            if (ConfigHandler.getRegionConfig().isVInteractEntities()) {
                Player player = e.getPlayer();
                Entity entity = e.getRightClicked();
                String entityType = entity.getType().name();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.debugMessage("Visitor", entityType, "Interact-Entities", "return", "border");
                    return;
                }
                // Allow-NPC
                if (entity.hasMetadata("NPC")) {
                    if (ConfigHandler.getRegionConfig().isVInteractEntitiesNPC()) {
                        ServerHandler.debugMessage("Visitor", entityType, "Interact-Entities", "bypass", "Allow-NPC=true");
                        return;
                    }
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVInteractEntitiesMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorInteractEntities", player);
                }
                ServerHandler.debugMessage("Visitor", entityType, "Interact-Entities", "cancel");
                e.setCancelled(true);
            }
        }
    }
}
