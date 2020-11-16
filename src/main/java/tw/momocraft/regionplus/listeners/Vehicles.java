package tw.momocraft.regionplus.listeners;

import es.pollitoyeye.vehicles.events.VehicleEnterEvent;
import es.pollitoyeye.vehicles.events.VehiclePickupEvent;
import es.pollitoyeye.vehicles.events.VehiclePlaceEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class Vehicles implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onVehiclePickupEvent(VehiclePickupEvent e) {
        if (ConfigHandler.getConfigPath().isResVehicles()) {
            if (!ConfigHandler.getDepends().VehiclesEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getBuildPerms(player.getLocation(), "destroy", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResVehicles", "bypass",
                        new Throwable().getStackTrace()[0]);
                Language.sendLangMessage("Message.BarrierPlus.noPermDestroy", player);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onVehiclePlaceEvent(VehiclePlaceEvent e) {
        if (ConfigHandler.getConfigPath().isResVehicles()) {
            if (!ConfigHandler.getDepends().VehiclesEnabled()) {
                return;
            }
            Player player = e.getOwner();
            if (!ResidenceUtils.getBuildPerms(player.getLocation(), "place", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResVehicles", "cancel",
                        new Throwable().getStackTrace()[0]);
                Language.sendLangMessage("Message.BarrierPlus.noPermPlace", player);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onVehicleEnterEvent(VehicleEnterEvent e) {
        if (ConfigHandler.getConfigPath().isResVehicles()) {
            if (!ConfigHandler.getDepends().VehiclesEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "use", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResVehicles", "cancel",
                        new Throwable().getStackTrace()[0]);
                Language.sendLangMessage("Message.BarrierPlus.noPermUse", player);
                e.setCancelled(true);
            }
        }
    }
}
