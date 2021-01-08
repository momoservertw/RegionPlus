package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
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
        if (!ConfigHandler.getConfigPath().isVisResGet()) {
            return;
        }
        String ownerName = e.getResidence().getOwner();
        Player owner = Bukkit.getPlayer(ownerName);
        Player player = Bukkit.getPlayer(e.getNewOwner());
        String playerName = player.getName();
        // Location
        if (!CorePlusAPI.getConditionManager().checkLocation(player.getLocation(), ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Get", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Get", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Cancel
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResGet(), owner);
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResGetTarget(), player);
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResGetTarget(), Bukkit.getConsoleSender());
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Get", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Residence: Create
    @EventHandler(priority = EventPriority.HIGH)
    private void onVisitorResCreate(ResidenceCreationEvent e) {
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
        String ownerName = e.getResidence().getOwner();
        Player owner = Bukkit.getPlayer(ownerName);
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResGet(), owner);
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResGetTarget(), player);
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResGetTarget(), Bukkit.getConsoleSender());
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Create", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }
}
