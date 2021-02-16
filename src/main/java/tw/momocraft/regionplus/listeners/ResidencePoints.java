package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.event.*;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.RegionUtils;

import java.util.UUID;

public class ResidencePoints implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                RegionUtils.refreshPointsMap(e.getPlayer());
            }
        }.runTaskLater(RegionPlus.getInstance(), 20);
    }

    @EventHandler(ignoreCancelled = true)
    public void onResidenceDeleteEvent(ResidenceDeleteEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                RegionUtils.refreshPointsMap(e.getPlayer());
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                RegionUtils.removePointsMap(e.getPlayer().getName());
            }
        }.runTaskLater(RegionPlus.getInstance(), 20);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void selectInform(PlayerInteractEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        if (!ConfigHandler.getConfigPath().isPointsSelectInfo()) {
            return;
        }
        if (!e.getMaterial().equals(ConfigHandler.getConfigPath().getPointsSelectTool())) {
            return;
        }
        Player player = e.getPlayer();
        SelectionManager sm = Residence.getInstance().getSelectionManager();
        Location loc1 = sm.getSelection(player).getBaseLoc1();
        Location loc2 = sm.getSelection(player).getBaseLoc2();
        if (loc1 == null || loc2 == null) {
            return;
        }
        World world1 = loc1.getWorld();
        World world2 = loc2.getWorld();
        if (world2 == null || world1 != world2) {
            return;
        }
        double size;
        if (ConfigHandler.getConfigPath().isResIgnoreY()) {
            size = Math.abs(loc1.getBlockX() - loc2.getBlockX()) * Math.abs(loc1.getBlockZ() - loc2.getBlockZ());
        } else {
            size = Math.abs(loc1.getBlockX() - loc2.getBlockX()) * Math.abs(loc1.getBlockZ() - loc2.getBlockZ())
                    * Math.abs(loc1.getBlockY() - loc2.getBlockY());
        }
        Residence.getInstance().getConfigManager().useActionBarOnSelection();
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPointsSelect(), player,
                RegionUtils.getPointsPlaceholders(player, size));
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                "Residence-Points", player.getName(), "selectInform", "return", "show",
                new Throwable().getStackTrace()[0]);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onResidenceCreation(ResidenceCreationEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), player, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Create", "return", "bypass permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        ClaimedResidence res = e.getResidence();
        CuboidArea area = res.getMainArea();
        RegionUtils.refreshPointsMap(e.getPlayer());
        double size = RegionUtils.getAreaSize(area);
        double last = RegionUtils.getPointsLastMap().get(playerName);
        if (size > last) {
            e.setCancelled(true);
            // Sending "not enough points "message.
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoney",
                    player, RegionUtils.getPointsPlaceholders(player, size));
            // Sending "return money" message.
            double cost = res.getBlockSellPrice() * size;
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[9] = "money"; // %pricetype%
            placeHolders[10] = String.valueOf(cost); // %price%
            CorePlusAPI.getPlayerManager().giveTypeMoney(player.getUniqueId(), "money", cost);
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgResReturnMoney(), player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Create Residence", "cancel", "notEnoughPoints",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Sending "new points balance" message.
        new BukkitRunnable() {
            @Override
            public void run() {
                RegionUtils.refreshPointsMap(player);
                RegionUtils.sendPointsMsg(player);
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Residence-Points", playerName, "Create Residence", "success", "final",
                        new Throwable().getStackTrace()[0]);
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onResidenceOwnerChange(ResidenceOwnerChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        ClaimedResidence res = e.getResidence();
        String resName = res.getName();
        UUID newOwnerUUID = e.getNewOwnerUuid();
        Player newOwner = Bukkit.getPlayer(newOwnerUUID);
        String newOwnerName = e.getNewOwner();
        UUID ownerUUID = res.getOwnerUUID();
        Player owner = Bukkit.getPlayer(ownerUUID);
        // Cancel event if the new owner not online.
        if (newOwner == null) {
            e.setCancelled(true);
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[1] = newOwnerName; // %targetplayer%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", owner, placeHolders);
            //  Returning the money of trade.
            if (res.isForSell()) {
                double price = res.getSellPrice();
                CorePlusAPI.getPlayerManager().takeTypeMoney(newOwnerUUID, "money", price);
                CorePlusAPI.getPlayerManager().giveTypeMoney(ownerUUID, "money", price);
                placeHolders[9] = "money"; // %pricetype%
                placeHolders[10] = String.valueOf(price); // %price%
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgResReturnMoney(), owner, placeHolders);
            }
            return;
        }
        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), newOwner, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                    "Residence", newOwnerName, "Points", "return", "bypass permission, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        RegionUtils.refreshPointsMap(newOwner);
        int size = RegionUtils.getAreaSize(res.getMainArea());
        double last = RegionUtils.getPointsLastMap().get(newOwnerName);
        if (last < size) {
            e.setCancelled(true);
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[1] = newOwnerName; // %targetplayer%
            placeHolders[9] = "res_points"; // %pricetype%
            placeHolders[10] = String.valueOf(size); // %price%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoney", newOwner, placeHolders);
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoneyTarget", owner, placeHolders);
            //  Returning the money of trade.
            if (res.isForSell()) {
                double price = res.getSellPrice();
                CorePlusAPI.getPlayerManager().takeTypeMoney(newOwnerUUID, "money", price);
                CorePlusAPI.getPlayerManager().giveTypeMoney(ownerUUID, "money", price);
                placeHolders[9] = "money"; // %pricetype%
                placeHolders[10] = String.valueOf(price); // %price%
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgResReturnMoney(), owner, placeHolders);
            }
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                    "Residence-Points", newOwnerName, "Owner Change", "cancel", "targetNotEnoughPoints, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Sending "new points balance" message.
        new BukkitRunnable() {
            @Override
            public void run() {
                RegionUtils.refreshPointsMap(newOwner);
                RegionUtils.sendPointsMsg(newOwner);
                if (owner != null) {
                    RegionUtils.refreshPointsMap(owner);
                    RegionUtils.sendPointsMsg(owner);
                }
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Residence-Points", newOwnerName, "Owner Change", "success", "final",
                        new Throwable().getStackTrace()[0]);
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onResidenceSizeChange(ResidenceSizeChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), player, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Size Change", "return", "bypass permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        ClaimedResidence res = e.getResidence();
        String resName = res.getName();
        CuboidArea area = e.getOldArea();
        CuboidArea newArea = e.getNewArea();
        int size = RegionUtils.getAreaSize(newArea) - RegionUtils.getAreaSize(area);
        double cost = res.getBlockSellPrice() * (size);
        // Prevent Limit Y size change.
        if (!RegionUtils.ignoreY(area)) {
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgSizeChangeFailed(), player);
            if (cost >= 0) {
                CorePlusAPI.getPlayerManager().giveTypeMoney(player.getUniqueId(), "money", cost);
            } else {
                CorePlusAPI.getPlayerManager().takeTypeMoney(player.getUniqueId(), "money", -cost);
            }
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgResReturnMoney(), player);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Size Change", "fail", "Limit Y size change",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        // Prevent multiple areas residence size change.
        if (ConfigHandler.getConfigPath().isPointsPreventAreasSizeChange()) {
            if (res.getAreaCount() > 1) {
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgSizeChangeFailed(), player);
                if (cost >= 0) {
                    CorePlusAPI.getPlayerManager().giveTypeMoney(player.getUniqueId(), "money", cost);
                } else {
                    CorePlusAPI.getPlayerManager().takeTypeMoney(player.getUniqueId(), "money", -cost);
                }
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgResReturnMoney(), player);
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Residence-Points", playerName, "Size Change", "fail", "Multiple areas size change",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
                return;
            }
        }
        if (size <= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    RegionUtils.refreshPointsMap(e.getPlayer());
                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                            "Residence-Points", playerName, "Size Change", "return", "Contract, " + resName,
                            new Throwable().getStackTrace()[0]);
                }
            }.runTaskLater(RegionPlus.getInstance(), 20);
            return;
        }
        RegionUtils.refreshPointsMap(e.getPlayer());
        double last = RegionUtils.getPointsLastMap().get(playerName);
        if (size > last) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[9] = "res_points"; // %pricetype%
            placeHolders[10] = String.valueOf(size); // %price%
            placeHolders[11] = String.valueOf(last); // %balance%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoney", player, placeHolders);
            CorePlusAPI.getPlayerManager().giveTypeMoney(player.getUniqueId(), "money", cost);
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgResReturnMoney(), player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Size Change", "cancel", "notEnoughPoints, " + resName,
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        // Sending "new points balance" message.
        new BukkitRunnable() {
            @Override
            public void run() {
                RegionUtils.refreshPointsMap(player);
                RegionUtils.sendPointsMsg(player);
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Residence-Points", playerName, "Size Change", "success", "final",
                        new Throwable().getStackTrace()[0]);
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
    }
}
