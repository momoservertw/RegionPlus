package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;


public class PlayerToggleFlight implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        if (ConfigHandler.getRegionConfig().isResPreventEnable()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            if (!e.isFlying() && !e.getPlayer().isSneaking()) {
                if (ConfigHandler.getRegionConfig().isResPreventFlyDisable()) {
                    Player player = e.getPlayer();
                    ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(e.getPlayer().getLocation());
                    if (res != null) {
                        ResidencePermissions perms = res.getPermissions();
                        if (perms.has(Flags.fly, false) || perms.playerHas(player, Flags.fly, false)) {
                            ServerHandler.debugMessage("Residence", "isResPreventFly", "residence flag", "cancel");
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
        /*
        if (e.isFlying()) {
            if (ConfigHandler.getRegionConfig().isPlayerPreventFly()) {
                String flyPerm = ConfigHandler.getRegionConfig().getPlayerPreventFlyPerm();
                if (PermissionsHandler.hasPermission(e.getPlayer(), flyPerm)) {
                    ServerHandler.debugMessage("Residence", "Fly-Disable", "Permission", "bypass");
                    return;
                }
                if (LocationAPI.getLocation(e.getPlayer().getLocation(), "Player.Prevent.Fly-Disable.Location")) {
                    e.setCancelled(true);
                }
            }
        }
         */

}
