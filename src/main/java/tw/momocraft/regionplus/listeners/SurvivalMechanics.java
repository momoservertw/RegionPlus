package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class SurvivalMechanics implements Listener {

    private void PlayerToggleClimb(PlayerToggleClimbEvent e) {
        if (!ConfigHandler.getConfigPath().isResSM()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResSMClimb()) {
            return;
        }
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "climb", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMClimb", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleCrawl(PlayerToggleCrawlEvent e) {
        if (!ConfigHandler.getConfigPath().isResSM()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResSMCrawl()) {
            return;
        }
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "crawl", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMCrawl", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleMobkick(PlayerToggleMobkickEvent e) {
        if (!ConfigHandler.getConfigPath().isResSM()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResSMMobkick()) {
            return;
        }
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "mobkick", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", player.getName(), "isResSMMobkick", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleRoofhang(PlayerToggleRoofhangEvent e) {
        if (!ConfigHandler.getConfigPath().isResSM()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResSMRoofhang()) {
            return;
        }
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "roofhang", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMRoofhang", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleSlide(PlayerToggleSlideEvent e) {
        if (!ConfigHandler.getConfigPath().isResSM()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResSMSlide()) {
            return;
        }
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "slide", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMClimb", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleSwim(PlayerToggleSwimEvent e) {
        if (!ConfigHandler.getConfigPath().isResSM()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResSMSwim()) {

            return;
        }
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "swim", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMSwim", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleWallkick(PlayerToggleWallkickEvent e) {
        if (!ConfigHandler.getConfigPath().isResSM()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isResSMWallkick()) {
            return;
        }
        if (!ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "wallkick", false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMWallkick", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }
}
