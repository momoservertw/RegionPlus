package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleSwimEvent;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class PlayerToggleSwim implements Listener {

    private void onPlayerToggleSwim(PlayerToggleSwimEvent e) {
        if (ConfigHandler.getConfigPath().isResSMSwim()) {
            if (!ResidenceUtils.getPerms(e.getPlayer().getLocation(), "swim", true, e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
