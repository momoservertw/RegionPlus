package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import javafx.util.Pair;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.text.DecimalFormat;
import java.util.*;

public class RegionUtils {

    private static final Map<String, Double> pointsLimitMap = new HashMap<>();
    private static final Map<String, Double> pointsUseMap = new HashMap<>();
    private static final Map<String, Double> pointsLastMap = new HashMap<>();

    public static void sendPointsMsg(Player player) {
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),
                ConfigHandler.getConfigPath().getMsgPoints(), player, RegionUtils.getPointsPlaceholders(player, 0));
    }

    public static void sendTargetPointsMsg(CommandSender sender, Player player) {
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(),
                ConfigHandler.getConfigPath().getMsgTargetPoints(), sender, RegionUtils.getPointsPlaceholders(player, 0));
    }

    public static void refreshPointsMap(Player player) {
        String playerName = player.getName();
        double limit = RegionUtils.getLimit(player);
        pointsLimitMap.put(playerName, limit);
        double used = RegionUtils.getUsed(playerName);
        pointsUseMap.put(playerName, used);
        double last = limit - used;
        pointsLastMap.put(playerName, last);
        for (String key : pointsLimitMap.keySet()) {
            System.out.println(key + ": " + pointsLimitMap.get(key));
        }
        for (String key : pointsUseMap.keySet()) {
            System.out.println(key + ": " + pointsUseMap.get(key));
        }
    }

    public static void removePointsMap(String playerName) {
        pointsLimitMap.remove(playerName);
        pointsUseMap.remove(playerName);
        pointsLastMap.remove(playerName);
        for (String key : pointsLimitMap.keySet()) {
            System.out.println(key + ": " + pointsLimitMap.get(key));
        }
        for (String key : pointsUseMap.keySet()) {
            System.out.println(key + ": " + pointsUseMap.get(key));
        }
    }

    public static Map<String, Double> getPointsLimitMap() {
        return pointsLimitMap;
    }

    public static Map<String, Double> getPointsUseMap() {
        return pointsUseMap;
    }

    public static Map<String, Double> getPointsLastMap() {
        return pointsLastMap;
    }

    public static int getUsed(String playerName) {
        int used = 0;
        CuboidArea area;
        for (ClaimedResidence res : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
            area = res.getMainArea();
            if (Residence.getInstance().getConfigManager().isSelectionIgnoreY() &&
                    !ConfigHandler.getConfigPath().isPointsLimitedYCalculate()) {
                if (ignoreY(area)) {
                    continue;
                }
            }
            used += getAreaSize(area);
        }
        return used;
    }

    public static double getLimit(Player player) {
        double group = getUserGroup(player).getValue();
        double level = getUserLevel(player);
        return group + level;
    }

    private static double getUserLevel(Player player) {
        return CorePlusAPI.getPlayerManager().getMaxPerm(player, "regionplus.points.level.", 0);
    }

    private static Pair<String, Integer> getUserGroup(Player player) {
        Map<String, Integer> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        if (player.isOp() || CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), player, "regionplus.points.group.*")) {
            String highestGroup = groupMap.keySet().iterator().next();
            return new Pair<>(highestGroup, groupMap.get(highestGroup));
        }
        for (String group : groupMap.keySet()) {
            if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), player, "regionplus.points.group." + group)) {
                return new Pair<>(group, groupMap.get(group));
            }
        }
        return new Pair<>("default", groupMap.get("default"));
    }


    public static int getAreaSize(CuboidArea area) {
        int size = 0;
        if (ConfigHandler.getConfigPath().isResIgnoreY()) {
            size += area.getXSize() * area.getZSize();
        } else {
            size += area.getXSize() * area.getZSize() * area.getYSize();
        }
        return size;
    }

    public static boolean ignoreY(CuboidArea area) {
        if (area.getWorld().getEnvironment().name().equals("NETHER") && area.getYSize() < 129) {
            return false;
        } else return area.getYSize() >= 256;
    }

    public static String[] getPointsPlaceholders(Player player, double size) {
        String[] placeHolders = CorePlusAPI.getLangManager().newString();
        String playerName = player.getName();
        double limit = pointsLimitMap.get(playerName);
        double used = pointsUseMap.get(playerName);
        double last = pointsLastMap.get(playerName);
        // %group%
        //placeHolders[5] = ConfigHandler.getConfigPath().getPointsDisplayMap().get();
        // %size%
        placeHolders[15] = new DecimalFormat("0").format(size);
        // %pricetype%
        placeHolders[9] = "res_points";
        // %price%
        placeHolders[10] = new DecimalFormat("0").format(
                Residence.getInstance().getPlayerManager().getGroup(player.getName()).getCostPerBlock() * size);
        // %amount%
        placeHolders[6] = new DecimalFormat("0").format(used);
        // %limit%
        placeHolders[23] = new DecimalFormat("0").format(limit);
        // %balance%
        placeHolders[11] = new DecimalFormat("0").format(last);
        return placeHolders;
    }

    public static void returnIgnoreY(CommandSender sender) {
        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPlugin(), sender, "&6Starting to return money...");
        ClaimedResidence res;
        double price;
        CuboidArea area;
        Map<String, ClaimedResidence> resMap = Residence.getInstance().getResidenceManager().getResidences();
        for (String resName : resMap.keySet()) {
            res = resMap.get(resName);
            area = res.getMainArea();
            if (Residence.getInstance().getConfigManager().isSelectionIgnoreY() &&
                    !ConfigHandler.getConfigPath().isPointsLimitedYCalculate()) {
                if (ignoreY(area)) {
                    continue;
                }
            }
            if (res.getBlockSellPrice() == 0) {
                continue;
            }
            price = area.getXSize() * area.getZSize() * (area.getYSize() - 1) * res.getBlockSellPrice();
            CorePlusAPI.getPlayerManager().giveTypeMoney(res.getOwnerUUID(), "money", price);
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(), "Return - &6" + resName + ": &e" + price + ", &a" + res.getOwner());
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&6Succeed to return money to all residences!");
            }
        }.runTaskLater(RegionPlus.getInstance(), 40);
    }
}
