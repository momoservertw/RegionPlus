package tw.momocraft.regionplus.listeners;

import me.DeeCaaD.SurvivalMechanics.Events.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class SurvivalMechanics implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void climb(PlayerToggleClimbEvent e) {
        if (!ConfigHandler.getConfigPath().isResSurvivalMechanics())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(),
                "climb", true)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "climb"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(), "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(),
                    ConfigHandler.getPluginPrefix(), "Residence", "Residence", player.getName(), "isResSMClimb", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerToggleCrawl(PlayerToggleCrawlEvent e) {
        if (!ConfigHandler.getConfigPath().isResSurvivalMechanics())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(), "crawl", true)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "crawl"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", "Residence", player.getName(), "isResSMCrawl", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void mobkick(PlayerToggleMobkickEvent e) {
        if (!ConfigHandler.getConfigPath().isResSurvivalMechanics())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(), "mobkick", true)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "mobkick"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", player.getName(), "isResSMMobkick", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void roofhang(PlayerToggleRoofhangEvent e) {
        if (!ConfigHandler.getConfigPath().isResSurvivalMechanics())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(), "roofhang", true)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "roofhang"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", "Residence", player.getName(), "isResSMRoofhang", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void slide(PlayerToggleSlideEvent e) {
        if (!ConfigHandler.getConfigPath().isResSurvivalMechanics())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(), "slide", true)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "slide"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", "Residence", player.getName(), "isResSMClimb", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void swim(PlayerToggleSwimEvent e) {
        if (!ConfigHandler.getConfigPath().isResSurvivalMechanics())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(), "swim", true)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "swim"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", "Residence", player.getName(), "isResSMSwim", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void wallkick(PlayerToggleWallkickEvent e) {
        if (!ConfigHandler.getConfigPath().isResSurvivalMechanics())
            return;
        Player player = e.getPlayer();
        if (!CorePlusAPI.getCond().checkFlag(player, player.getLocation(), "wallkick", true)) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[13] = "wallkick"; // %flag%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", "Residence", player.getName(), "isResSMWallkick", "cancel",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }
}
