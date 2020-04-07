package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class ResidenceOwnerChange implements Listener {

    /**
     * @param e ResidenceOwnerChangeEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceOwnerChange(ResidenceOwnerChangeEvent e) {
        if (ConfigHandler.getRegionConfig().isPointsEnable()) {
            Player newOwner = Bukkit.getPlayer(e.getNewOwnerUuid());
            String newOwnerName = e.getNewOwner();
            Player owner = Bukkit.getPlayer(e.getResidence().getOwner());
            if (newOwner == null) {
                return;
            }
            if (PermissionsHandler.hasPermission(newOwner, "regionplus.bypass.points.limit")) {
                return;
            }
            ClaimedResidence res = e.getResidence();
            long size = ResidenceUtils.getSize(e.getResidence());
            long last = ResidenceUtils.getLimit(newOwner) - ResidenceUtils.getUsed(newOwner);
            if (size > last) {
                String resName = res.getName();
                String[] placeHolders = Language.newString();
                placeHolders[24] = String.valueOf(last);
                placeHolders[25] = String.valueOf(size);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Language.sendLangMessage("Message.RegionPlus.notEnoughPoints", newOwner, placeHolders);
                        Language.sendLangMessage("Message.RegionPlus.targetNotEnoughPoints", owner, placeHolders);
                    }
                }.runTaskLater(RegionPlus.getInstance(), 10);
                if (ResidenceApi.getMarketBuyManager().isForSale(resName)) {
                    Economy economy = ConfigHandler.getDepends().getVault().getEconomy();
                    double price = ResidenceApi.getMarketBuyManager().getSaleAmount(resName);
                    economy.withdrawPlayer(Bukkit.getOfflinePlayer(e.getResidence().getOwnerUUID()), price);
                    economy.depositPlayer(Bukkit.getOfflinePlayer(newOwner.getUniqueId()), price);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Language.sendLangMessage("Message.RegionPlus.transactionFailed", newOwner, placeHolders);
                            Language.sendLangMessage("Message.RegionPlus.transactionFailed", owner, placeHolders);
                        }
                    }.runTaskLater(RegionPlus.getInstance(), 10);
                }
                ServerHandler.debugMessage("Residence-Points", newOwnerName, "ResidenceOwnerChangeEvent", "cancel", "targetNotEnoughPoints");
                e.setCancelled(true);
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    Language.sendLangMessage("Message.RegionPlus.points", newOwner, ResidenceUtils.pointsPH(newOwner));
                }
            }.runTaskLater(RegionPlus.getInstance(), 10);
            ServerHandler.debugMessage("Residence-Points", newOwnerName, "ResidenceOwnerChangeEvent", "return", "succeed");
        }
    }
}
