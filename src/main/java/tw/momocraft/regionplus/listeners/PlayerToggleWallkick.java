package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleWallkickEvent;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class PlayerToggleWallkick implements Listener {

    private void onPlayerToggleWallkick(PlayerToggleWallkickEvent e) {
        if (ConfigHandler.getConfigPath().isResSMWallkick()) {
            if (!ResidenceUtils.getPerms(e.getPlayer().getLocation(), "wallkick", true, e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
