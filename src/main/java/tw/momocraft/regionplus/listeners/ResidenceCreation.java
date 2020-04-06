package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class ResidenceCreation implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onResPointsEnable(ResidenceCreationEvent e) {
        if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
            Player player = e.getPlayer();
            String playerName = player.getName();
            if (PermissionsHandler.hasPermission(player, "regionplus.bypass.points.limit")) {
                ServerHandler.debugMessage("Residence", playerName, "Points", "return", "bypass permission");
                return;
            }
            long size = ResidenceUtils.getSize(e.getResidence());
            long pointsLimit = ResidenceUtils.getLimit(player);
            long pointsUsed = ResidenceUtils.getUsed(player);
            long pointsRemainder = pointsLimit - pointsUsed;
            String[] placeHolders = Language.newString();
            placeHolders[8] = String.valueOf(pointsLimit);
            placeHolders[9] = String.valueOf(pointsUsed);
            placeHolders[10] = String.valueOf(pointsRemainder);
            if (size > pointsRemainder) {
                placeHolders[11] = String.valueOf(size);
                Language.sendLangMessage("Message.RegionPlus.notEnoughPoints", player, placeHolders);
                ServerHandler.debugMessage("Residence", playerName, "Points", "cancel", "notEnoughPoints");
                e.setCancelled(true);
                return;
            }
            Language.sendLangMessage("Message.RegionPlus.points", player, placeHolders);
            ServerHandler.debugMessage("Residence", playerName, "Points", "return", "final");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onVisitorCreateRes(ResidenceCreationEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorEnable()) {
            if (ConfigHandler.getRegionConfig().isVisitorCreateRes()) {
                Player player = e.getPlayer();
                String playerName = player.getName();
                if (!RegionUtils.bypassBorder(player, player.getLocation())) {
                    // Cancel
                    if (ConfigHandler.getRegionConfig().isVisitorCreateResMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorCreateResidence", player);
                    }
                    ServerHandler.debugMessage("Visitor", playerName, "Create-Residence", "cancel", "border");
                    e.setCancelled(true);
                }
            }
        }
    }
}
