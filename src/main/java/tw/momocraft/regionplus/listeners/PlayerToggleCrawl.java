package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleCrawlEvent;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class PlayerToggleCrawl implements Listener {

    private void onPlayerToggleCrawl(PlayerToggleCrawlEvent e) {
        if (ConfigHandler.getConfigPath().isResSMCrawl()) {
            if (!ResidenceUtils.getPerms(e.getPlayer().getLocation(), "crawl", true, e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
