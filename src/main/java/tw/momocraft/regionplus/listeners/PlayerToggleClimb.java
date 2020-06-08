package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleClimbEvent;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class PlayerToggleClimb implements Listener {

    private void PlayerToggleClimb(PlayerToggleClimbEvent e) {
        if (ConfigHandler.getConfigPath().isResSMClimb()) {
            if (!ResidenceUtils.getPerms(e.getPlayer().getLocation(), "climb", true, e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
