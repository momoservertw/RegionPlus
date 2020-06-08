package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleSlideEvent;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class PlayerToggleSlide implements Listener {

    private void onPlayerToggleSlide(PlayerToggleSlideEvent e) {
        if (ConfigHandler.getConfigPath().isResSMClimb()) {
            if (!ResidenceUtils.getPerms(e.getPlayer().getLocation(), "slide", true, e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
