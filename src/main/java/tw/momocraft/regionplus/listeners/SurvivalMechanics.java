package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class SurvivalMechanics implements Listener {

    private void PlayerToggleClimb(PlayerToggleClimbEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResSMClimb()) {
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "climb", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMClimb", "cancel", "climb=false",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleCrawl(PlayerToggleCrawlEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResSMCrawl()) {
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "crawl", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMCrawl", "cancel", "crawl=false",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleMobkick(PlayerToggleMobkickEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResSMMobkick()) {
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "mobkick", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMMobkick", "cancel", "mobkick=false",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleRoofhang(PlayerToggleRoofhangEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResSMRoofhang()) {
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "roofhang", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMRoofhang", "cancel", "roofhang=false",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleSlide(PlayerToggleSlideEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResSMClimb()) {
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "slide", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMClimb", "cancel", "slide=false",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleSwim(PlayerToggleSwimEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResSMSwim()) {
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "swim", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMSwim", "cancel", "swim=false",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleWallkick(PlayerToggleWallkickEvent e) {
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        if (ConfigHandler.getConfigPath().isResSMWallkick()) {
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "wallkick", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMWallkick", "cancel", "wallkick=false",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }
}
