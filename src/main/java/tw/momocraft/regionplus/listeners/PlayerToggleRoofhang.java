package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleRoofhangEvent;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class PlayerToggleRoofhang implements Listener {

    private void onPlayerToggleRoofhang(PlayerToggleRoofhangEvent e) {
        if (ConfigHandler.getConfigPath().isResSMRoofhang()) {
            if (!ResidenceUtils.getPerms(e.getPlayer().getLocation(), "roofhang", true, e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
