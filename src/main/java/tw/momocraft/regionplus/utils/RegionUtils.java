package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.*;

public class RegionUtils {

    private static final Map<String, Long> buyingMap = new HashMap<>();

    public static void returnIgnoreY() {
        ClaimedResidence res;
        double price;
        for (String resName : Residence.getInstance().getResidenceManager().getResidenceList()) {
            res = Residence.getInstance().getResidenceManager().getByName(resName);
            if (!ignoreYBypass(res.getMainArea())) {
                continue;
            }
            price = res.getMainArea().getXSize() * res.getMainArea().getZSize() * res.getBlockSellPrice() * (res.getMainArea().getYSize() - 1);
            CorePlusAPI.getPlayerManager().giveTypeMoney(res.getOwnerUUID(), "money", price);
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(), "Return residence value: " + resName + ", " + price + ", " + res.getOwner());
        }
    }

    public static boolean ignoreYBypass(CuboidArea area) {
        if (Residence.getInstance().getConfigManager().isSelectionIgnoreY() &&
                !ConfigHandler.getConfigPath().isPointsIgnoreY()) {
            if (area.getWorld().getEnvironment().name().equals("NETHER") && area.getYSize() < 129) {
                return false;
            } else return area.getYSize() >= 256;
        }
        return true;
    }

    public static int getLimit(Player player) {
        return getUserGroup(player).getValue();
    }

    public static int getAreaSize(CuboidArea area) {
        int size = 0;
        if (ConfigHandler.getConfigPath().isPointsMode()) {
            size += area.getXSize() * area.getZSize();
        } else {
            size += area.getXSize() * area.getZSize() * area.getYSize();
        }
        return size;
    }

    public static int getUsed(String playerName) {
        int used = 0;
        CuboidArea area;
        for (ClaimedResidence res : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
            area = res.getMainArea();
            if (ignoreYBypass(area)) {
                continue;
            }
            used += getAreaSize(area);
        }
        return used;
    }

    private static Pair<String, Integer> getUserGroup(Player player) {
        Map<String, Integer> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        if (player.isOp() || CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.points.group.*")) {
            String highestGroup = groupMap.keySet().iterator().next();
            return new Pair<>(highestGroup, groupMap.get(highestGroup));
        }
        for (String group : groupMap.keySet()) {
            if (CorePlusAPI.getPlayerManager().hasPermission(player, "regionplus.points.group." + group)) {
                return new Pair<>(group, groupMap.get(group));
            }
        }
        return new Pair<>("default", groupMap.get("default"));
    }

    public static void editMessage() {
        String playerName;
        String resName;
        boolean bypassPerm = ConfigHandler.getConfigPath().isResMsgBypassPerm();
        boolean message = ConfigHandler.getConfigPath().isResMsgMsg();
        List<String> groups = getResGroups();
        String groupName;
        Table<String, String, List<String>> groupOldTable = ConfigHandler.getConfigPath().getResMsgGroupTable();
        Table<String, String, String> groupTable = HashBasedTable.create();
        List<String> enter;
        List<String> leave;
        for (PermissionGroup pg : Residence.getInstance().getPermissionManager().getGroups().values()) {
            groupTable.put(pg.getGroupName(), "enter", pg.getDefaultEnterMessage());
            groupTable.put(pg.getGroupName(), "leave", pg.getDefaultLeaveMessage());
        }
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        int playerSize = offlinePlayers.length;
        int i = playerSize;
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            if (message) {
                i--;
                if (i % 300 == 0 && i != 0) {
                    CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&eMessage-Edit process has not finished yet! &8- &6Player: " + i + "/" + playerSize);
                }
            }
            playerName = offlinePlayer.getName();
            if (bypassPerm) {
                if (CorePlusAPI.getPlayerManager().hasPermissionOffline(offlinePlayer, "regionplus.bypass.messageedit")) {
                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Message-Flags-Editor", playerName, "permission", "bypass",
                            new Throwable().getStackTrace()[0]);
                    continue;
                }
            }
            if (playerName != null) {
                groupName = getUserGroup(offlinePlayer, groups);
                enter = groupOldTable.get(groupName, "enter");
                if (enter == null) {
                    enter = groupOldTable.get("other", "enter");
                }
                leave = groupOldTable.get(groupName, "leave");
                if (leave == null) {
                    leave = groupOldTable.get("other", "leave");
                }
                for (ClaimedResidence res : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
                    resName = res.getName();
                    res.getEnterMessage();
                    if (enter.isEmpty() || enter.contains(res.getEnterMessage())) {
                        res.setEnterMessage(groupTable.get(groupName, "enter"));
                        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Message-Editor", resName, "enter", "change", "Owner: " + playerName + ", Group: " + groupName,
                                new Throwable().getStackTrace()[0]);
                    }
                    if (leave.isEmpty() || leave.contains(res.getLeaveMessage())) {
                        res.setLeaveMessage(groupTable.get(groupName, "leave"));
                        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Message-Editor", resName, "leave", "change", "Owner: " + playerName + ", Group: " + groupName,
                                new Throwable().getStackTrace()[0]);
                    }
                }
            }
        }
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&6Message-Edit process has ended.");
    }


    public static List<String> getResGroups() {
        List<String> list = new ArrayList<>(Residence.getInstance().getPermissionManager().getGroups().keySet());
        list.sort(Collections.reverseOrder());
        return list;
    }

    public static String getUserGroup(OfflinePlayer offlinePlayer, List<String> groups) {
        for (String group : groups) {
            if (CorePlusAPI.getPlayerManager().hasPermissionOffline(offlinePlayer, "residence.group." + group)) {
                return group;
            }
        }
        return "default";
    }

    public static String translatePlaceholders(String input, Player player, long size) {
        Map<String, Integer> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        Map<String, String> groupDisplayMap = ConfigHandler.getConfigPath().getPointsDisplayMap();
        List<String> groupList = new ArrayList<>(groupMap.keySet());
        Pair<String, Integer> userGroup = getUserGroup(player);
        String group = userGroup.getKey();
        int limit = userGroup.getValue();
        String nextGroup;
        long nextLimit;
        if (groupList.indexOf(group) > 0) {
            nextGroup = groupList.get(groupList.indexOf(group) - 1);
            nextLimit = groupMap.get(nextGroup);
        } else {
            nextGroup = group;
            nextLimit = limit;
        }
        long used = getUsed(player.getName());
        long last = limit - used;
        return input.replace("%points_group%", groupDisplayMap.get(group))
                .replace("%points_limit%", String.valueOf(limit))
                .replace("%points_used%", String.valueOf(used))
                .replace("%points_last%", String.valueOf(last))
                .replace("%points_size%", String.valueOf(size))
                .replace("%points_newlast%", String.valueOf(last - size))
                .replace("%points_nextgroup%", groupDisplayMap.get(nextGroup))
                .replace("%points_nextlimit%", String.valueOf(nextLimit))
                .replace("%points_nextbonus%", String.valueOf(nextLimit - limit))
                ;
    }

    public static String[] getPointsPlaceholders(Player player, long size) {
        Map<String, Integer> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        Map<String, String> groupDisplayMap = ConfigHandler.getConfigPath().getPointsDisplayMap();
        List<String> groupList = new ArrayList<>(groupMap.keySet());
        Pair<String, Integer> userGroup = getUserGroup(player);
        String group = userGroup.getKey();
        int limit = userGroup.getValue();
        String nextGroup;
        long nextLimit;
        if (groupList.indexOf(group) > 0) {
            nextGroup = groupList.get(groupList.indexOf(group) - 1);
            nextLimit = groupMap.get(nextGroup);
        } else {
            nextGroup = group;
            nextLimit = limit;
        }
        String playerName = player.getName();
        long used = getUsed(playerName);
        long last = limit - used;
        String[] placeHolders = CorePlusAPI.getLangManager().newString();
        // %player%
        placeHolders[1] = playerName;
        // %target%
        // %points_group%
        placeHolders[21] = groupDisplayMap.get(group);
        // %points_limit%
        placeHolders[22] = String.valueOf(limit);
        // %points_used%
        placeHolders[23] = String.valueOf(used);
        // %points_last%
        placeHolders[24] = String.valueOf(last);
        // %points_need%
        placeHolders[25] = String.valueOf(size);
        // %points_newlast%
        placeHolders[26] = String.valueOf(last - size);
        // %points_nextgroup%
        placeHolders[27] = groupDisplayMap.get(nextGroup);
        // %points_nextlimit%
        placeHolders[28] = String.valueOf(nextLimit);
        // %points_nextbonus%
        placeHolders[29] = String.valueOf(nextLimit - limit);
        // %points_nextbonus%
        placeHolders[30] = String.valueOf(
                Residence.getInstance().getPlayerManager().getGroup(player.getName()).getCostPerBlock() * size);
        return placeHolders;
    }

    public static boolean onResidenceBuying(String playerName) {
        int cdTick = 5;
        long playerCD = 0L;
        if (buyingMap.containsKey(playerName)) {
            playerCD = buyingMap.get(playerName);
        }
        return System.currentTimeMillis() - playerCD < cdTick * 50;
    }

    public static void addBuying(String playerName) {
        buyingMap.put(playerName, System.currentTimeMillis());
    }
    public static boolean isCanUse(String blockType) {
        if (blockType.endsWith("PRESSURE_PLATE")) {
            return true;
        }
        if (blockType.equals("TRIPWIRE")) {
            return true;
        }
        if (blockType.endsWith("DOOR")) {
            return true;
        }
        if (blockType.endsWith("FENCE_GATE")) {
            return true;
        }
        if (blockType.endsWith("BUTTON")) {
            return true;
        }
        switch (blockType) {
            // Crafting
            case "CRAFTING_TABLE":
            case "ENCHANTING_TABLE":
            case "FLETCHING_TABLE":
            case "SMITHING_TABLE":
            case "NOTE_BLOCK":
            case "ANVIL":
            case "BREWING_STAND":
                // Redstone Machine
            case "LEVER":
            case "DIODE":
            case "COMPARATOR":
            case "REPEATER":
            case "REDSTONE_COMPARATOR":
            case "DAYLIGHT_DETECTOR":
                // Other
            case "BEACON":
            case "ITEM_FRAME":
            case "FLOWER_POT":
            case "BED_BLOCK":
            case "CAKE_BLOCK":
            case "COMMAND":
                return true;
            default:
                return false;
        }
    }

    public static boolean isContainer(String blockType) {
        if (blockType.endsWith("CHEST")) {
            return true;
        }
        if (blockType.endsWith("SHULKER_BOX")) {
            return true;
        }
        switch (blockType) {
            // Crafting
            case "BREWING_STAND":
            case "DISPENSER":
            case "DROPPER":
            case "FURNACE":
            case "HOPPER":
            case "SMOKER":
            case "BARREL":
            case "BLAST_FURNACE":
            case "LOOM":
                // Other
            case "ITEM_FRAME":
            case "JUKEBOX":
            case "ARMOR_STAND":
                return true;
            default:
                return false;
        }
    }
}
