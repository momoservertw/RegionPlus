package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceSizeChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class VisitorResidence implements Listener {

    // Residence: Get
    @EventHandler(priority = EventPriority.HIGH)
    private void onVisitorResSize(ResidenceOwnerChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isVisitor()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isVisRes()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isVisResSize()) {
            return;
        }
        Player player = Bukkit.getPlayer(e.getNewOwner());
        String playerName = player.getName();
        // Location
        if (!CorePlusAPI.getConditionManager().checkLocation(player.getLocation(), ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Size", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Size", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisMsg()) {
            CorePlusAPI.getLangManager().sendPlayerMsg(ConfigHandler.getPrefix(), player, ConfigHandler.getConfigPath().getMsgVisResSize());
        }
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Size", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Residence: Create-Size
    @EventHandler(priority = EventPriority.HIGH)
    private void onVisitorResSize(ResidenceSizeChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isVisitor()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isVisRes()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isVisResSize()) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        // Location
        if (!CorePlusAPI.getConditionManager().checkLocation(player.getLocation(), ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Size", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Size", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisMsg()) {
            CorePlusAPI.getLangManager().sendPlayerMsg(ConfigHandler.getPrefix(), player, ConfigHandler.getConfigPath().getMsgVisResSize());
        }
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Size", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Residence: Create
    @EventHandler(priority = EventPriority.HIGH)
    private void onVisitorResCreate(ResidenceCreationEvent e) {
        if (!ConfigHandler.getConfigPath().isVisitor()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isVisRes()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isVisResCreate()) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getConditionManager().checkLocation(loc, ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Create", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Create", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisMsg()) {
            CorePlusAPI.getLangManager().sendPlayerMsg(ConfigHandler.getPrefix(), player, ConfigHandler.getConfigPath().getMsgVisResCreate());
        }
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Create", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }
}
