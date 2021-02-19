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
    public void onVehiclePickupEvent(VehiclePickupEvent e) {
        if (!ConfigHandler.getConfigPath().isResVehicles()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(),
                "destroy", false) &&
                !e.getOwner().equals(player.getName())) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "destroy"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(),
                    ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                    "Residence", player.getName(),
                    "VehiclePickupEvent", "bypass",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onVehiclePlaceEvent(VehiclePlaceEvent e) {
        if (!ConfigHandler.getConfigPath().isResVehicles()) {
            return;
        }
        Player player = e.getOwner();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(),
                "place", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "place"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                    "Residence", player.getName(), "VehiclePlaceEvent", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onVehicleEnterEvent(VehicleEnterEvent e) {
        if (!ConfigHandler.getConfigPath().isResVehicles()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "use", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "use"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                    "Residence", player.getName(), "VehicleEnterEvent", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }
}
