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

    /**
     * @param e PlayerToggleFlightEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResPrevent()) {
            if (!e.isFlying() && !e.getPlayer().isSneaking()) {
                if (ConfigHandler.getConfigPath().isResPreventFly()) {
                    Player player = e.getPlayer();
                    ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(e.getPlayer().getLocation());
                    if (res != null) {
                        ResidencePermissions perms = res.getPermissions();
                        if (perms.has(Flags.fly, false) || perms.playerHas(player, Flags.fly, false)) {
                            ServerHandler.sendFeatureMessage("Residence", "isResPreventFly", "residence flag", "cancel",
                                    new Throwable().getStackTrace()[0]);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
