package tw.momocraft.regionplus.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class WorldControl implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        if (!ConfigHandler.getConfigPath().isWorldPreventExplode()) {
            return;
        }
        if (ConfigHandler.getConfigPath().getWorldPreventExplodeIgnore().contains(e.getEntity().getType().name())) {
            e.blockList().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockExplodeEvent(BlockExplodeEvent e) {
        if (!ConfigHandler.getConfigPath().isWorldPreventExplode()) {
            return;
        }
        if (ConfigHandler.getConfigPath().getWorldPreventExplodeIgnore().contains(e.getBlock().getType().name())) {
            e.blockList().clear();
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (!ConfigHandler.getConfigPath().isWorldPreventDroppedItemExplosion()) {
            return;
        }
        String entityType = e.getEntity().getType().name();
        if ("DROPPED_ITEM".equals(entityType)) {
            String cause = e.getCause().name();
            if (cause.equals("BLOCK_EXPLOSION") || cause.equals("ENTITY_EXPLOSION")) {
                e.setCancelled(true);
            }
        }
    }
}
