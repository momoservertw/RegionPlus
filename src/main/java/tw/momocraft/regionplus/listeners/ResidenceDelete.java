package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.Language;

public class ResidenceDelete implements Listener {

    private void onResidenceDelete(ResidenceDeleteEvent e) {
        if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
            if (ConfigHandler.getRegionConfig().getResPointsMode().equalsIgnoreCase("XZ")) {
                if (ConfigHandler.getRegionConfig().isResPointsReturnXYZ()) {
                    if (!e.getCause().equals(ResidenceDeleteEvent.DeleteCause.PLAYER_DELETE)) {
                        return;
                    }
                    ClaimedResidence res = e.getResidence();
                    CuboidArea mainArea = res.getMainArea();
                    if (mainArea.getYSize() == 256) {
                        return;
                    }
                    int X;
                    int Y;
                    int Z;
                    double oldSell = 0;
                    double newSell = 0;
                    double difference;
                    if (ConfigHandler.getRegionConfig().isResPointsAllAreas()) {
                        boolean ignoreWithin = ConfigHandler.getRegionConfig().isResPointsIgnoreWithin();
                        for (CuboidArea area : res.getAreaArray()) {
                            if (ignoreWithin && mainArea.isAreaWithinArea(area)) {
                                continue;
                            }
                            X = area.getXSize();
                            Y = area.getYSize();
                            Z = area.getZSize();
                            newSell += X * Z * e.getResidence().getOwnerGroup().getSellPerBlock();
                            oldSell += X * Y * Z * e.getResidence().getSellPrice();
                        }
                    } else {
                        X = mainArea.getXSize();
                        Y = mainArea.getYSize();
                        Z = mainArea.getZSize();
                        newSell = X * Z * e.getResidence().getOwnerGroup().getSellPerBlock();
                        oldSell = X * Y * Z * e.getResidence().getSellPrice();
                    }
                    difference = oldSell - newSell;
                    if (difference > 0) {
                        Player player = e.getPlayer();
                        ConfigHandler.getDepends().getVault().getEconomy().depositPlayer(player, difference);
                        String[] placeHolders = Language.newString();
                        placeHolders[6] = String.valueOf(difference);
                        Language.sendLangMessage("Message.RegionPlus.returnXYZMoney", player, placeHolders);
                    }
                }
            }
        }
    }
}
