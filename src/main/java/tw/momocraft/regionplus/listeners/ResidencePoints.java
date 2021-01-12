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
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.RegionUtils;

import java.util.Arrays;
import java.util.List;

public class ResidencePoints implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onResPointsSelectInfo(PlayerInteractEvent e) {
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
        if (world1 == null || world2 == null || !world1.equals(loc2.getWorld())) {
            return;
        }
        int size;
        if (ConfigHandler.getConfigPath().isPointsMode()) {
            size = (Math.abs(loc1.getBlockX() - loc2.getBlockX()) + 1) * (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) + 1);
        } else {
            size = (Math.abs(loc1.getBlockX() - loc2.getBlockX()) + 1) * (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) + 1)
                    * (Math.abs(loc1.getBlockY() - loc2.getBlockY()) + 1);
        }
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPointsSelect(), player,
                RegionUtils.getPointsPlaceholders(player, size));
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", player.getName(), "Select-Tool", "return", "show",
                new Throwable().getStackTrace()[0]);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceAreaAddEvent(ResidenceAreaAddEvent e) {
        if (!ConfigHandler.getConfigPath().isPointsAddArea()) {
            return;
        }
        Player player = e.getPlayer();
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgAreaAddFailed(), player);
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", player.getName(), "Create", "cancel", "notEnoughMoney",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceCreation(ResidenceCreationEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints()) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Create", "return", "bypass permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        ClaimedResidence res = e.getResidence();
        CuboidArea area = res.getMainArea();
        // Ignoring the residences witch is "IgnoreY" is false.
        if (RegionUtils.ignoreYBypass(area)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "ignoreYBypass", "return",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        int size = RegionUtils.getAreaSize(area);
        int last = RegionUtils.getLimit(player) - RegionUtils.getUsed(playerName);
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
    public void onResidenceOwnerChange(ResidenceOwnerChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints() || e.isCancelled()) {
            return;
        }
        Player newOwner = Bukkit.getPlayer(e.getNewOwnerUuid());
        String newOwnerName = e.getNewOwner();
        ClaimedResidence res = e.getResidence();
        String resName = res.getName();
        Player owner = Bukkit.getPlayer(res.getOwner());
        if (newOwner == null) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[1] = newOwnerName; // %targetplayer%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", owner, placeHolders);
            e.setCancelled(true);
            return;
        }
        if (CorePlusAPI.getPlayerManager().hasPermission(newOwner, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", newOwnerName, "Points", "return", "bypass permission, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        CuboidArea area = res.getMainArea();
        int size = RegionUtils.getAreaSize(area);
        int last = RegionUtils.getLimit(newOwner) - RegionUtils.getUsed(newOwnerName);
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
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoneyTarget", owner, placeHolders);
                }
            }.runTaskLater(RegionPlus.getInstance(), 10);
            //  Returning the money of trade.
            if (res.isForSell() && RegionUtils.onResidenceBuying(newOwnerName)) {
                double price = res.getSellPrice();
                CorePlusAPI.getPlayerManager().takeTypeMoney(e.getNewOwnerUuid(), "money", price);
                CorePlusAPI.getPlayerManager().giveTypeMoney(res.getOwnerUUID(), "money", price);
            }
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", newOwnerName, "Owner Change", "cancel", "targetNotEnoughPoints, " + resName,
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),
                        RegionUtils.translatePlaceholders(ConfigHandler.getConfigPath().getMsgPoints(), newOwner, size), newOwner);

            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", newOwnerName, "ResidenceOwnerChangeEvent", "return", "succeed, " + resName,
                new Throwable().getStackTrace()[0]);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceSizeChange(ResidenceSizeChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints() || e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", playerName, "Points", "return", "bypass permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        if (ConfigHandler.getConfigPath().isPointsAreasSize()) {
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgSizeChangeFailed(), player);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", playerName, "Points", "fail", "Size change",
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        ClaimedResidence res = e.getResidence();
        String resName = res.getName();
        // Ignoring the residences witch is "IgnoreY" is false.
        if (RegionUtils.ignoreYBypass(res.getMainArea())) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "ignoreYBypass", "return", resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        int size = RegionUtils.getAreaSize(e.getNewArea()) - RegionUtils.getAreaSize(e.getOldArea());
        if (size < 0) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Size Change", "return", " contract, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        int last = RegionUtils.getLimit(player) - RegionUtils.getUsed(playerName);
        if (size > last) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[9] = "res_points"; // %pricetype%
            placeHolders[10] = String.valueOf(size); // %price%
            placeHolders[11] = String.valueOf(last); // %balance%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.notEnoughMoney", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Size Change", "cancel", "notEnoughMoney, " + resName,
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),
                        RegionUtils.translatePlaceholders(ConfigHandler.getConfigPath().getMsgPoints(), player, size), player);
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence-Points", playerName, "Size Change", "return", "final, " + resName,
                new Throwable().getStackTrace()[0]);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceBuyEvent(ResidenceCommandEvent e) {
        if (e.isCancelled()) {
            return;
        }
        try {
            if (e.getCommand().equals("res")) {
                List<String> list = Arrays.asList(e.getArgs());
                if (list.size() > 2 && list.get(0).equals("market") && list.get(1).equals("buy")) {
                    if (e.getSender() instanceof Player) {
                        RegionUtils.addBuying(e.getSender().getName());
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
