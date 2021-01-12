package tw.momocraft.regionplus.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class WorldControl implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        String entityType = e.getEntity().getType().name();
        if (ConfigHandler.getConfigPath().isWorldPreventCreeper()) {
            if (entityType.equals("CREEPER")) {
                e.setCancelled(true);
                return;
            }
        }
        if (ConfigHandler.getConfigPath().isWorldPreventEnderdrangon()) {
            if (entityType.equals("ENDER_DRAGON")) {
                e.setCancelled(true);
                return;
            }
        }
        if (ConfigHandler.getConfigPath().isWorldPreventExplode()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockIgniteEvent(BlockIgniteEvent e) {
        if (!ConfigHandler.getConfigPath().isWorldPreventFireSpread()) {
            return;
        }
        String cause = e.getCause().name();
        if (!cause.equals("SPREAD") && !cause.equals("LIGHTNING") && !cause.equals("LAVA")) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBurnEvent(BlockBurnEvent e) {
        if (!ConfigHandler.getConfigPath().isWorldPreventFireSpread()) {
            return;
        }
        e.setCancelled(true);
    }
}
