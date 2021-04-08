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

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVehiclePickupEvent(VehiclePickupEvent e) {
        if (!ConfigHandler.getConfigPath().isResVehicles())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(),
                "destroy", false) &&
                !e.getOwner().equals(player.getName())) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "destroy"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", player.getName(),
                    "VehiclePickupEvent", "bypass",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVehiclePlaceEvent(VehiclePlaceEvent e) {
        if (!ConfigHandler.getConfigPath().isResVehicles())
            return;
        Player player = e.getOwner();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(),
                "place", false)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "place"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", player.getName(), "VehiclePlaceEvent", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVehicleEnterEvent(VehicleEnterEvent e) {
        if (!ConfigHandler.getConfigPath().isResVehicles())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(), "use", false)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "use"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", player.getName(), "VehicleEnterEvent", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }
}
