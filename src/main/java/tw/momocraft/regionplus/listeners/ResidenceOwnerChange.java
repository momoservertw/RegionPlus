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
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class ResidenceOwnerChange implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceOwnerChange(ResidenceOwnerChangeEvent e) {
        if (!ConfigHandler.getRegionConfig().isResPointsEnable()) {
            return;
        }
        Player owner = Bukkit.getPlayer(e.getResidence().getOwnerUUID());
        String ownerName = Bukkit.getPlayer(e.getResidence().getOwnerUUID()).getName();
        Player newOwner = Bukkit.getPlayer(e.getNewOwnerUuid());
        String newOwnerName = e.getNewOwner();
        if (newOwner == null) {
            String[] placeHolders = Language.newString();
            placeHolders[2] = String.valueOf(newOwnerName);
            Language.sendLangMessage("Message.targetNotOnline", owner, placeHolders);
            ServerHandler.debugMessage("Residence", newOwnerName, "Points", "cancel", "targetNotOnline");
            return;
        }
        if (PermissionsHandler.hasPermission(newOwner, "regionplus.bypass.points.limit")) {
            return;
        }
        long size = ResidenceUtils.getSize(e.getResidence());
        long pointsLimit = ResidenceUtils.getLimit(newOwner);
        long pointsUsed = ResidenceUtils.getUsed(newOwner);
        long pointsRemainder = pointsLimit - pointsUsed;
        String[] placeHolders = Language.newString();
        placeHolders[8] = String.valueOf(pointsLimit);
        placeHolders[9] = String.valueOf(pointsUsed);
        placeHolders[10] = String.valueOf(pointsRemainder);
        if (size > pointsRemainder) {
            placeHolders[2] = newOwnerName;
            placeHolders[11] = String.valueOf(size);
            Language.sendLangMessage("Message.RegionPlus.targetNotEnoughPoints", owner, placeHolders);
            ServerHandler.debugMessage("Residence", ownerName, "Points", "cancel", "targetNotEnoughPoints");
            e.setCancelled(true);
            return;
        }
        Language.sendLangMessage("Message.RegionPlus.points", newOwner, placeHolders);
    }
}
