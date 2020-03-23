package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.ResidencePoints;

public class ResidenceCreation implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceCreation(ResidenceCreationEvent e) {
        Player player = e.getPlayer();
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
        long pointsRemainer = ResidencePoints.getPointsRemainder(player);
        if (size > pointsRemainer && (!PermissionsHandler.hasPermission(player, "regionplus.bypass.points.limit"))) {
            String[] placeHolders = Language.newString();
            placeHolders[8] = String.valueOf(ResidencePoints.getPointsLimit(player));
            placeHolders[9] = String.valueOf(ResidencePoints.getPointsUsed(player));
            placeHolders[10] = String.valueOf(pointsRemainer);
            placeHolders[11] = String.valueOf(size);
            Language.sendLangMessage("Message.RegionPlus.notEnoughPoints", player, placeHolders);
            e.setCancelled(true);
        }
    }
}
