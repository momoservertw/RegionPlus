package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceFlagCheckEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.PermissionsHandler;

public class ResidenceFlagCheck implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceFlagCheck(ResidenceFlagCheckEvent e) {
        /*
        Player player = Bukkit.getPlayer(e.getFlagTargetPlayerOrGroup());

        if (e.getFlag().equals("nofly")) {
            if (player != null) {
                if (PermissionsHandler.hasPermission(player, "entityplus.admin")) {
                    e.overrideCheck(true);
                }
            }
        }

         */
    }
}
