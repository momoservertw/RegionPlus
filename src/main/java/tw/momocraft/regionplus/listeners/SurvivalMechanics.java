package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class SurvivalMechanics implements Listener {

    private void PlayerToggleClimb(PlayerToggleClimbEvent e) {
        if (ConfigHandler.getConfigPath().isResSMClimb()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "climb", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMClimb", "cancel",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleCrawl(PlayerToggleCrawlEvent e) {
        if (ConfigHandler.getConfigPath().isResSMCrawl()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "crawl", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMCrawl", "cancel",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleMobkick(PlayerToggleMobkickEvent e) {
        if (ConfigHandler.getConfigPath().isResSMMobkick()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "mobkick", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMMobkick", "cancel",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleRoofhang(PlayerToggleRoofhangEvent e) {
        if (ConfigHandler.getConfigPath().isResSMRoofhang()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "roofhang", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMRoofhang", "cancel",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleSlide(PlayerToggleSlideEvent e) {
        if (ConfigHandler.getConfigPath().isResSMClimb()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "slide", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMClimb", "cancel",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleSwim(PlayerToggleSwimEvent e) {
        if (ConfigHandler.getConfigPath().isResSMSwim()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "swim", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMSwim", "cancel",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }

    private void onPlayerToggleWallkick(PlayerToggleWallkickEvent e) {
        if (ConfigHandler.getConfigPath().isResSMWallkick()) {
            if (!ConfigHandler.getDepends().ResidenceEnabled()) {
                return;
            }
            Player player = e.getPlayer();
            if (!ResidenceUtils.getPerms(player.getLocation(), "wallkick", true, player)) {
                ServerHandler.sendFeatureMessage("Residence", player.getName(), "isResSMWallkick", "cancel",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
            }
        }
    }
}
