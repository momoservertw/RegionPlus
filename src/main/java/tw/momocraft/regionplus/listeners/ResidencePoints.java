package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceSizeChangeEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class ResidencePoints implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceCreation(ResidenceCreationEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Create", "return", "bypass permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        long size = ResidenceUtils.getNewSize(e.getPhysicalArea());
        long last = ResidenceUtils.getLimit(player) - ResidenceUtils.getUsed(player);
        if (size > last) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[9] = "res_points"; // %pricetype%
            placeHolders[10] = String.valueOf(size); // %price%
            placeHolders[11] = String.valueOf(last); // %balance%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoney", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Create", "cancel", "notEnoughMoney",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPoints(), player,
                        ConfigHandler.getConfigPath().getMsgPoints());
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Create", "return", "final",
                new Throwable().getStackTrace()[0]);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceOwnerChange(ResidenceOwnerChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        Player newOwner = Bukkit.getPlayer(e.getNewOwnerUuid());
        String newOwnerName = e.getNewOwner();
        Player owner = Bukkit.getPlayer(e.getResidence().getOwner());
        if (newOwner == null) {
            return;
        }
        if (CorePlusAPI.getPlayerManager().hasPermission(newOwner, "regionplus.bypass.points.limit")) {
            return;
        }
        ClaimedResidence res = e.getResidence();
        long size = ResidenceUtils.getSize(res);
        long last = ResidenceUtils.getLimit(newOwner) - ResidenceUtils.getUsed(newOwner);
        if (last < size) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[1] = newOwnerName; // %targetplayer%
            placeHolders[9] = "res_points"; // %pricetype%
            placeHolders[10] = String.valueOf(size); // %price%
            placeHolders[11] = String.valueOf(last); // %balance%
            new BukkitRunnable() {
                @Override
                public void run() {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoney", newOwner, placeHolders);
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoneyTarget", newOwner, placeHolders);
                }
            }.runTaskLater(RegionPlus.getInstance(), 10);
                /*
                if (ResidenceApi.getMarketBuyManager().isForSale(resName)) {
                    double price = ResidenceApi.getMarketBuyManager().getSaleAmount(resName);
                    CorePlusAPI.getPlayerManager().takeTypeMoney(e.getResidence().getOwnerUUID(), "money", price);
                    CorePlusAPI.getPlayerManager().giveTypeMoney(e.getResidence().getOwnerUUID(), "money", price);
                }
                 */
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", newOwnerName, "Owner Change", "cancel", "targetNotEnoughPoints",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPoints(), newOwner, ResidenceUtils.pointsValues(newOwner));
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", newOwnerName, "ResidenceOwnerChangeEvent", "return", "succeed",
                new Throwable().getStackTrace()[0]);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onResPointsSelectInfo(PlayerInteractEvent e) {
        if (!ConfigHandler.getConfigPath().isPointsSelectInfo()) {
            return;
        }
        if (!e.getMaterial().equals(ConfigHandler.getConfigPath().getPointsSelectTool())) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        SelectionManager sm = Residence.getInstance().getSelectionManager();
        Location loc1 = sm.getSelection(player).getBaseLoc1();
        Location loc2 = sm.getSelection(player).getBaseLoc2();
        if (loc1 == null || loc2 == null) {
            return;
        }
        World world1 = loc1.getWorld();
        World world2 = loc2.getWorld();
        if (world1 == null || world2 == null || !world1.equals(loc2.getWorld())) {
            return;
        }
        long size;
        if (ConfigHandler.getConfigPath().isPointsMode()) {
            size = (Math.abs(loc1.getBlockX() - loc2.getBlockX()) + 1) * (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) + 1);
        } else {
            size = (Math.abs(loc1.getBlockX() - loc2.getBlockX()) + 1) * (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) + 1)
                    * (Math.abs(loc1.getBlockY() - loc2.getBlockY()) + 1);
        }
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPointsSelect(), player, ResidenceUtils.selectPointsPH(player, size));
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", playerName, "Select-Tool", "return", "show",
                new Throwable().getStackTrace()[0]);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onResidenceSizeChange(ResidenceSizeChangeEvent e) {
        if (ConfigHandler.getConfigPath().isPoints()) {
            Player player = e.getPlayer();
            String playerName = player.getName();
            if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.bypass.points.limit")) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", playerName, "Points", "return", "bypass permission",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            if (!ConfigHandler.getConfigPath().isResIgnoreYSize()) {
                CuboidArea area = e.getResidence().getMainArea();
                if (area.getWorld().getEnvironment().name().equals("NETHER") && area.getYSize() < 129) {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgSizeChangeFailed(), player);
                    return;
                } else if (area.getYSize() < 256) {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgSizeChangeFailed(), player);
                    return;
                }
            }
            long size = ResidenceUtils.getNewSize(e.getNewArea()) - ResidenceUtils.getNewSize(e.getOldArea());
            if (size < 0) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Size Change", "return", " contract",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            long last = ResidenceUtils.getLimit(player) - ResidenceUtils.getUsed(player);
            if (size > last) {
                String[] placeHolders = CorePlusAPI.getLangManager().newString();
                placeHolders[9] = "res_points"; // %pricetype%
                placeHolders[10] = String.valueOf(size); // %price%
                placeHolders[11] = String.valueOf(last); // %balance%
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoney", player, placeHolders);
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Size Change", "cancel", "notEnoughMoney",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPoints(), player, ResidenceUtils.pointsValues(player));
                }
            }.runTaskLater(RegionPlus.getInstance(), 10);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Size Change", "return", "final",
                    new Throwable().getStackTrace()[0]);
        }
    }
}
