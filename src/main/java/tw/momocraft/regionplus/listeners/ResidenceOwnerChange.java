package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.ResidencePoints;


public class ResidenceOwnerChange implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceOwnerChange(ResidenceOwnerChangeEvent e) {
        Player owner = Bukkit.getPlayer(e.getResidence().getOwnerUUID());
        Player newOwner = Bukkit.getPlayer(e.getNewOwnerUuid());
        String newOwnerName = e.getNewOwner();
        if (newOwner != null) {
            long size;
            long X = e.getResidence().getMainArea().getXSize();
            long Z = e.getResidence().getMainArea().getZSize();
            String mode = ConfigHandler.getRegionConfig().getResPointsMode();
            if (mode != null && mode.equals("XYZ")) {
                long Y = e.getResidence().getMainArea().getYSize();
                size = X * Z * Y;
            } else {
                size = X * Z;
            }
            long pointsRemainder = ResidencePoints.getPointsRemainder(newOwner);
            if (size > pointsRemainder && (!PermissionsHandler.hasPermission(newOwner, "regionplus.bypass.points.limit"))) {
                String[] placeHolders = Language.newString();
                placeHolders[2] = String.valueOf(newOwnerName);
                Language.sendLangMessage("Message.RegionPlus.targetNotEnoughPoints", owner, placeHolders);
                placeHolders[8] = String.valueOf(ResidencePoints.getPointsLimit(newOwner));
                placeHolders[9] = String.valueOf(ResidencePoints.getPointsUsed(newOwner));

                placeHolders[10] = String.valueOf(pointsRemainder);
                placeHolders[11] = String.valueOf(size);
                Language.sendLangMessage("Message.RegionPlus.notEnoughPoints", newOwner, placeHolders);
                ServerHandler.debugMessage("Residence", newOwnerName, "Points", "cancel", "notEnoughPoints");
                e.setCancelled(true);
            }
            return;
        }
        String[] placeHolders = Language.newString();
        placeHolders[2] = String.valueOf(newOwnerName);
        Language.sendLangMessage("Message.targetNotOnline", owner, placeHolders);
        ServerHandler.debugMessage("Residence", newOwnerName, "Points", "cancel", "targetNotOnline");
    }
}
