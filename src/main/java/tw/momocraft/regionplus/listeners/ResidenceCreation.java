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
import tw.momocraft.regionplus.utils.ResidencePoints;

public class ResidenceCreation implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceCreation(ResidenceCreationEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (ConfigHandler.getRegionConfig().isVisitorEnable()) {
            if (ConfigHandler.getRegionConfig().isVisitorCreateRes()) {
                if (!RegionUtils.bypassBorder(player, player.getLocation())) {
                    // Cancel
                    if (ConfigHandler.getRegionConfig().isVisitorCreateResMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorCreateResidence", player);
                    }
                    ServerHandler.debugMessage("Visitor", playerName, "Create-Residence", "cancel", "border");
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
            long size;
            long X = e.getPhysicalArea().getXSize();
            long Z = e.getPhysicalArea().getZSize();
            String mode = ConfigHandler.getRegionConfig().getResPointsMode();
            if (mode != null && mode.equals("XYZ")) {
                long Y = e.getPhysicalArea().getYSize();
                size = X * Z * Y;
            } else {
                size = X * Z;
            }
            long pointsRemainder = ResidencePoints.getPointsRemainder(player);
            if (size > pointsRemainder && (!PermissionsHandler.hasPermission(player, "regionplus.bypass.points.limit"))) {
                String[] placeHolders = Language.newString();
                placeHolders[8] = String.valueOf(ResidencePoints.getPointsLimit(player));
                placeHolders[9] = String.valueOf(ResidencePoints.getPointsUsed(player));

                placeHolders[10] = String.valueOf(pointsRemainder);
                placeHolders[11] = String.valueOf(size);
                Language.sendLangMessage("Message.RegionPlus.notEnoughPoints", player, placeHolders);
                ServerHandler.debugMessage("Residence", playerName, "Points", "cancel", "notEnoughPoints");
                e.setCancelled(true);
            }
        }
    }
}
