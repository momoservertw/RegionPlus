package tw.momocraft.regionplus.listeners;

import es.pollitoyeye.vehicles.events.VehicleEnterEvent;
import es.pollitoyeye.vehicles.events.VehiclePickupEvent;
import es.pollitoyeye.vehicles.events.VehiclePlaceEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class Vehicles implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onVehiclePickupEvent(VehiclePickupEvent e) {
        if (ConfigHandler.getConfigPath().isResVehicles()) {
            Player player = e.getPlayer();
            if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "destroy", false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResVehicles", "bypass",
                        new Throwable().getStackTrace()[0]);
                Language.sendLangMessage("Message.RegionPlus.noPermDestroy", player);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onVehiclePlaceEvent(VehiclePlaceEvent e) {
        if (ConfigHandler.getConfigPath().isResVehicles()) {
            Player player = e.getOwner();
            if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "destroy", false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResVehicles", "cancel",
                        new Throwable().getStackTrace()[0]);
                Language.sendLangMessage("Message.RegionPlus.noPermPlace", player);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onVehicleEnterEvent(VehicleEnterEvent e) {
        if (ConfigHandler.getConfigPath().isResVehicles()) {
            Player player = e.getPlayer();
            if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "destroy", false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResVehicles", "cancel",
                        new Throwable().getStackTrace()[0]);
                Language.sendLangMessage("Message.RegionPlus.noPermUse", player);
                e.setCancelled(true);
            }
        }
    }
}
