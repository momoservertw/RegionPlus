package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class SurvivalMechanics implements Listener {

    private void PlayerToggleClimb(PlayerToggleClimbEvent e) {
        if (!ConfigHandler.getConfigPath().isResSMClimb()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "climb", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "climb"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMClimb", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleCrawl(PlayerToggleCrawlEvent e) {
        if (!ConfigHandler.getConfigPath().isResSMCrawl()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "crawl", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "crawl"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMCrawl", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleMobkick(PlayerToggleMobkickEvent e) {
        if (!ConfigHandler.getConfigPath().isResSMMobkick()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "mobkick", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "mobkick"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", player.getName(), "isResSMMobkick", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleRoofhang(PlayerToggleRoofhangEvent e) {
        if (!ConfigHandler.getConfigPath().isResSMRoofhang()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "roofhang", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "roofhang"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMRoofhang", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleSlide(PlayerToggleSlideEvent e) {
        if (!ConfigHandler.getConfigPath().isResSMSlide()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "slide", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "slide"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMClimb", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleSwim(PlayerToggleSwimEvent e) {
        if (!ConfigHandler.getConfigPath().isResSMSwim()) {

            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "swim", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "swim"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMSwim", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    private void onPlayerToggleWallkick(PlayerToggleWallkickEvent e) {
        if (!ConfigHandler.getConfigPath().isResSMWallkick()) {
            return;
        }
        Player player = e.getPlayer();
        if (!CorePlusAPI.getConditionManager().checkFlag(player, player.getLocation(), "wallkick", false)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "wallkick"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence", player.getName(), "isResSMWallkick", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }
}
