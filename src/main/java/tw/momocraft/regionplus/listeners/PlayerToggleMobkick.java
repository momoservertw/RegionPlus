package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleMobkickEvent;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class PlayerToggleMobkick implements Listener {

    private void onPlayerToggleMobkick(PlayerToggleMobkickEvent e) {
        if (ConfigHandler.getConfigPath().isResSMMobkick()) {
            if (!ResidenceUtils.getPerms(e.getPlayer().getLocation(), "mobkick", true, e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
