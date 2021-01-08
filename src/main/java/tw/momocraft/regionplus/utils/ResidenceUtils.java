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
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.*;

public class ResidenceUtils {

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

    public static void editFlags() {
        FlagsEditor flagsEditor = ConfigHandler.getEditor();
        flagsEditor.setUp();
        boolean restartMsg = ConfigHandler.getConfigPath().isResFlagMaxMessage();
        startEditFlags(flagsEditor.getEditList());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (flagsEditor.isEnd()) {
                    try {
                        flagsEditor.resetUp();
                        startEditFlags(flagsEditor.getEditList());
                    } catch (Exception e) {
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&cThere is an error occur while starting to edit residence flags.");
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&cPlease turn on the debug mode and send the error messages to plugin author.");
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&ehttps://github.com/momoservertw/RegionPlus/issues");
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&6Flags-Edit process has ended.");
                        CorePlusAPI.getLangManager().sendDebugTrace(ConfigHandler.getPlugin(), e);
                        flagsEditor.setRun(false);
                        cancel();
                        return;
                    }
                    if (!flagsEditor.isRun()) {
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&6Flags-Edit process has ended.");
                        flagsEditor.setRun(false);
                        cancel();
                        return;
                    }
                    if (!flagsEditor.isRestart()) {
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&6Flags-Edit process has ended.");
                        flagsEditor.setRun(false);
                        cancel();
                        return;
                    }
                    if (restartMsg) {
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&eFlags-Edit process has not finished yet! &8- &6Player: " + flagsEditor.getLast() + "/" + flagsEditor.getPlayerSize());
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&fIt will restart after few seconds. &8(Stop process: /rp flagsedit stop)");
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "");
                    }
                }
            }
        }.runTaskTimer(RegionPlus.getInstance(), 0, ConfigHandler.getConfigPath().getResFlagMaxInterval() * 20);
    }

    private static void startEditFlags(List<OfflinePlayer> editList) {
        List<String> groups = getResGroups();
        String playerName;
        String resName;
        ResidencePermissions resPerm;
        PermissionGroup group;
        String groupName;
        String[] listSplit;
        String[] listSplit2;
        List<String> permsPlayerList = new ArrayList<>();
        Set<Map.Entry<String, Boolean>> flagSet;
        Set<Map.Entry<String, Boolean>> defaultFlags;
        List<String> removeFlagList = new ArrayList<>();
        List<String> removeFlagPlayerList = new ArrayList<>();
        Map<String, String> addFlagMap = new HashMap<>();
        String flag;
        Map<String, Boolean> permsPlayerFlags;
        boolean bypassPerm = ConfigHandler.getConfigPath().isResFlagBypassPerms();
        boolean bypassCustom = ConfigHandler.getConfigPath().isResFlagBypassCustom();
        boolean defaultRemove = ConfigHandler.getConfigPath().isResFlagRemove();
        boolean defaultRemoveOnly = ConfigHandler.getConfigPath().isResFlagRemoveOnly();
        List<String> defaultRemoveIgnore = ConfigHandler.getConfigPath().getResFlagRemoveIgnore();
        boolean defaultUpdate = ConfigHandler.getConfigPath().isResFlagUpdate();
        List<String> defaultUpdateIgnore = ConfigHandler.getConfigPath().getResFlagUpdateIgnore();
        boolean permsRemove = ConfigHandler.getConfigPath().isResFlagPermsRemove();
        List<String> permsRemoveIgnore = ConfigHandler.getConfigPath().getResFlagPermsRemoveIgnore();

        for (OfflinePlayer offlinePlayer : editList) {
            playerName = offlinePlayer.getName();
            if (bypassPerm) {
                if (CorePlusAPI.getPlayerManager().hasPermissionOffline(offlinePlayer, "regionplus.bypass.flagsedit")) {
                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence.Flags-Editor", playerName, "permission", "bypass",
                            new Throwable().getStackTrace()[0]);
                    break;
                }
            }
            if (playerName != null) {
                groupName = getUserGroup(offlinePlayer, groups);
                group = Residence.getInstance().getPermissionManager().getGroupByName(groupName);
                for (ClaimedResidence res : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
                    resName = res.getName();
                    resPerm = res.getPermissions();
                    // The default flags of that group.
                    defaultFlags = group.getDefaultResidenceFlags();
                    // The default flags of that residence.
                    flagSet = resPerm.getFlags().entrySet();
                    // Default - Remove
                    if (defaultRemove) {
                        for (Map.Entry<String, Boolean> flagEntry : flagSet) {
                            if (!defaultFlags.contains(flagEntry)) {
                                flag = flagEntry.getKey();
                                if (defaultRemoveIgnore.contains(flag)) {
                                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", resName, "default flags", "bypass", "Flag: " + flag + "Owner: " + playerName + ", Group: " + groupName,
                                            new Throwable().getStackTrace()[0]);
                                    break;
                                }
                                try {
                                    if (defaultRemoveOnly) {
                                        if (group.hasFlagAccess(Flags.valueOf(flag))) {
                                            break;
                                        }
                                    }
                                    removeFlagList.add(flag);
                                } catch (Exception e) {
                                    if (bypassCustom) {
                                        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", resName, "default flags", "bypass", "Flag: " + flag + ", Owner: " + playerName + ", Group: " + groupName,
                                                new Throwable().getStackTrace()[0]);
                                    } else {
                                        removeFlagList.add(flag);
                                    }
                                }
                            }
                        }
                        for (String removeFlag : removeFlagList) {
                            resPerm.setFlag(removeFlag, FlagPermissions.FlagState.NEITHER);
                            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", resName, "default flags", "remove", "Flag: " + removeFlag + ", Owner: " + playerName + ", Group: " + groupName,
                                    new Throwable().getStackTrace()[0]);
                        }
                    }
                    // Default - Update
                    if (defaultUpdate) {
                        for (Map.Entry<String, Boolean> resFlagEntry : flagSet) {
                            if (!flagSet.contains(resFlagEntry)) {
                                flag = resFlagEntry.getKey();
                                if (defaultUpdateIgnore.contains(flag)) {
                                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", resName, "update flags", "bypass", "Flag: " + flag + ", Owner: " + playerName + ", Group: " + groupName,
                                            new Throwable().getStackTrace()[0]);
                                    break;
                                }
                                if (!group.hasFlagAccess(Flags.valueOf(flag))) {
                                    addFlagMap.put(flag, resFlagEntry.getValue().toString());
                                }
                            }
                        }
                        for (String addFlagKey : addFlagMap.keySet()) {
                            flag = addFlagMap.get(addFlagKey);
                            resPerm.setFlag(addFlagKey, FlagPermissions.FlagState.valueOf(flag.toUpperCase()));
                            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", resName, "update flags", "change", "Flag: " + addFlagKey + "=" + flag + ", Owner: " + playerName + ", Group: " + groupName,
                                    new Throwable().getStackTrace()[0]);
                        }
                    }
                    // Permission (player) - Remove
                    if (permsRemove) {
                        // Get player permission list in residence.
                        listSplit = resPerm.listPlayersFlags().split("§f\\[");
                        permsPlayerList.add(listSplit[0]);
                        for (String flagsSplit : listSplit) {
                            listSplit2 = flagsSplit.split("§f] ");
                            for (int j = 0; j < listSplit2.length; j++) {
                                if (j % 2 == 1) {
                                    permsPlayerList.add(listSplit2[j]);
                                }
                            }
                        }
                        // Check every player's permissions.
                        for (String permsPlayer : permsPlayerList) {
                            permsPlayerFlags = resPerm.getPlayerFlags(permsPlayer);
                            if (permsPlayerFlags != null) {
                                for (String permsPlayerFlag : permsPlayerFlags.keySet()) {
                                    if (permsRemoveIgnore.contains(permsPlayerFlag)) {
                                        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", resName, "player flags", "bypass", "Flag: " + permsPlayerFlag + ", Owner: " + playerName + ", Group: " + groupName,
                                                new Throwable().getStackTrace()[0]);
                                        break;
                                    }
                                    try {
                                        if (!group.hasFlagAccess(Flags.valueOf(permsPlayerFlag))) {
                                            removeFlagPlayerList.add(permsPlayerFlag);
                                        }
                                    } catch (Exception e) {
                                        if (bypassCustom) {
                                            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", resName, "player flags", "bypass", "Flag: " + permsPlayerFlag + ", Owner: " + playerName + ", Group: " + groupName,
                                                    new Throwable().getStackTrace()[0]);
                                        } else {
                                            removeFlagPlayerList.add(permsPlayerFlag);
                                        }
                                    }
                                }
                                for (String removePlayerFlag : removeFlagPlayerList) {
                                    resPerm.setPlayerFlag(permsPlayer, removePlayerFlag, FlagPermissions.FlagState.NEITHER);
                                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", resName, "player flags", "remove", "Flag: " + removePlayerFlag + ", Player: " + permsPlayer + ", Owner: " + playerName + ", Group: " + groupName,
                                            new Throwable().getStackTrace()[0]);
                                }
                            }
                        }
                    }
                }
            }
        }
        ConfigHandler.getEditor().setEnd(true);
    }

    private static List<String> getResGroups() {
        List<String> list = new ArrayList<>(Residence.getInstance().getPermissionManager().getGroups().keySet());
        list.sort(Collections.reverseOrder());
        return list;
    }

    private static String getUserGroup(OfflinePlayer offlinePlayer, List<String> groups) {
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
        Pair<String, Integer> userGroup = ResidenceUtils.getUserGroup(player);
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
        long used = ResidenceUtils.getUsed(player);
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
        Pair<String, Integer> userGroup = ResidenceUtils.getUserGroup(player);
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
        long used = ResidenceUtils.getUsed(player);
        long last = limit - used;
        String[] placeHolders = CorePlusAPI.getLangManager().newString();
        // %player%
        placeHolders[1] = player.getName();
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
}
