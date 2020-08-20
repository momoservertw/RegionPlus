package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class ResidenceDelete implements Listener {

    /**
     * Residence-ReturnXYZ
     *
     * @param e ResidenceDeleteEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onResidenceDelete(ResidenceDeleteEvent e) {
        if (ConfigHandler.getConfigPath().isResIgnoreYPoints() && Residence.getInstance().getConfigManager().isSelectionIgnoreY()) {
            Player player = e.getPlayer();
            String playerName = player.getName();
            if (!e.getCause().equals(ResidenceDeleteEvent.DeleteCause.PLAYER_DELETE)) {
                return;
            }
            ClaimedResidence res = e.getResidence();
            CuboidArea mainArea = res.getMainArea();
            if (mainArea.getYSize() > 255) {
                return;
            }
            int X;
            int Y;
            int Z;
            double oldSell = 0;
            double newSell = 0;
            if (ConfigHandler.getConfigPath().isResAllAreas()) {
                boolean ignoreWithin = ConfigHandler.getConfigPath().isResIgnoreWithin();
                for (CuboidArea area : res.getAreaArray()) {
                    if (ignoreWithin && mainArea.isAreaWithinArea(area)) {
                        continue;
                    }
                    X = area.getXSize();
                    Y = area.getYSize();
                    Z = area.getZSize();
                    oldSell += X * Y * Z * e.getResidence().getBlockSellPrice();
                    newSell += X * Z * e.getResidence().getBlockSellPrice();
                }
            } else {
                X = mainArea.getXSize();
                Y = mainArea.getYSize();
                Z = mainArea.getZSize();
                oldSell += X * Y * Z * e.getResidence().getBlockSellPrice();
                newSell += X * Z * e.getResidence().getBlockSellPrice();
            }
            double difference = oldSell - newSell;
            if (difference > 0) {
                ConfigHandler.getDepends().getVault().getEconomy().depositPlayer(player, difference);
                String[] placeHolders = Language.newString();
                placeHolders[5] = String.valueOf(ConfigHandler.getDepends().getVault().getEconomy().getBalance(player));
                placeHolders[6] = String.valueOf(difference);
                Language.sendLangMessage("Message.RegionPlus.returnXYZMoney", player, placeHolders);
                ServerHandler.sendFeatureMessage("Residence-returnXYZ", playerName, "ResidenceDeleteEvent", "return",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    Language.sendLangMessage("Message.RegionPlus.points", player, ResidenceUtils.pointsPH(player));
                }
            }.runTaskLater(RegionPlus.getInstance(), 10);
            ServerHandler.sendFeatureMessage("Residence-returnXYZ", playerName, "ResidenceDeleteEvent", "return", "final",
                    new Throwable().getStackTrace()[0]);
        }
    }
}
