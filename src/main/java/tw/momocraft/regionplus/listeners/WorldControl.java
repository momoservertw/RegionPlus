package tw.momocraft.regionplus.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class WorldControl implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        if (!ConfigHandler.getConfigPath().isWorldControl() || !ConfigHandler.getConfigPath().isWorldPreventExplode()) {
            return;
        }
        if (ConfigHandler.getConfigPath().getWorldPreventExplodeIgnore().contains(e.getEntity().getType().name())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockExplodeEvent(BlockExplodeEvent e) {
        if (!ConfigHandler.getConfigPath().isWorldControl() || !ConfigHandler.getConfigPath().isWorldPreventExplode()) {
            return;
        }
        if (ConfigHandler.getConfigPath().getWorldPreventExplodeIgnore().contains(e.getBlock().getType().name())) {
            e.setCancelled(true);
        }
    }
}
