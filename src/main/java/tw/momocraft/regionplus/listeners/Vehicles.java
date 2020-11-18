package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.event.*;
import es.pollitoyeye.vehicles.events.VehicleEnterEvent;
import es.pollitoyeye.vehicles.events.VehiclePickupEvent;
import es.pollitoyeye.vehicles.events.VehiclePlaceEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.ResidenceUtils;
import tw.momocraft.regionplus.utils.Utils;

import java.util.List;

public class Vehicles implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onVehiclePickupEvent(VehiclePickupEvent e) {
        if (ConfigHandler.getConfigPath().isResVehicles()) {
            Player player = e.getPlayer();
            if (!ResidenceUtils.getBuildPerms(player.getLocation(), "destroy", false, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResVehicles", "bypass",
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
            if (!ResidenceUtils.getBuildPerms(player.getLocation(), "place", false, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResVehicles", "cancel",
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
            if (!ResidenceUtils.getPerms(player.getLocation(), "use", false, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResVehicles", "cancel",
                        new Throwable().getStackTrace()[0]);
                Language.sendLangMessage("Message.RegionPlus.noPermUse", player);
                e.setCancelled(true);
            }
        }
    }

    /*
    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceFlagCheck(ResidencePlayerFlagEvent e) {
        if (ConfigHandler.getConfigPath().isResVehicles()) {
            if (e.getFlag().equals("container")) {
                if (getSearchBlocks(e.getPlayer().getLocation(), "ARMOR_STAND", 3)) {
                    e.
                }
            }
            if (!ResidenceUtils.getBuildPerms(player.getLocation(), "destroy", false, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResVehicles", "bypass",
                        new Throwable().getStackTrace()[0]);
                Language.sendLangMessage("Message.RegionPlus.noPermDestroy", player);
                e.overrideCheck().setCancelled(true);
            }
        }
    }

    /**
     * @param loc the checking location.
     * @return Check if there are matching materials nearby.
     */
    private boolean getSearchBlocks(Location loc, String blockType, int range) {
        Location blockLoc;
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    if (x * z <= range) {
                        for (int y = -range; y <= range; y++) {
                            blockLoc = loc.clone().add(x, y, z);
                            if (blockType.equals(blockLoc.getBlock().getType().name())) {
                                return true;
                            }
                        }
                    }
                }
            }
        return false;
    }
    */
}
