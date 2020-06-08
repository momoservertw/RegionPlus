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
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;

import java.util.*;

public class ResidenceUtils {

    public static boolean getBuildPerms(ResidencePermissions perms, String flag, boolean def, Player player) {
        if (player != null) {
            if (perms.playerHas(player, Flags.build, false)) {
                return perms.playerHas(player, Flags.getFlag(flag), true);
            }
        }
        if (perms.has(Flags.build, false)) {
            return perms.has(Flags.getFlag(flag), true);
        }
        return perms.has(Flags.getFlag(flag), def);
    }

    public static boolean getBuildPerms(ResidencePermissions perms, String flag, boolean def) {
        if (perms.has(Flags.build, false)) {
            return perms.has(Flags.getFlag(flag), true);
        }
        return perms.has(Flags.getFlag(flag), def);
    }

    public static boolean getBuildPerms(Location location, String flag, boolean def, Player player) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
        if (res != null) {
            ResidencePermissions perms = res.getPermissions();
            if (player != null) {
                if (perms.playerHas(player, Flags.build, false)) {
                    return perms.playerHas(player, Flags.getFlag(flag), true);
                }
            }
            if (perms.has(Flags.build, false)) {
                return perms.has(Flags.getFlag(flag), true);
            }
            return perms.has(Flags.getFlag(flag), def);
        }
        return true;
    }

    public static boolean getBuildPerms(Location location, String flag, boolean def) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
        if (res != null) {
            ResidencePermissions perms = res.getPermissions();
            if (perms.has(Flags.build, false)) {
                return perms.has(Flags.getFlag(flag), true);
            }
            return perms.has(Flags.getFlag(flag), def);
        }
        return true;
    }

    public static boolean getPerms(Location location, String flag, boolean def, Player player) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
        if (res != null) {
            ResidencePermissions perms = res.getPermissions();
            if (player != null) {
                return perms.playerHas(player, Flags.getFlag(flag), def);
            }
            return perms.has(Flags.getFlag(flag), def);
        }
        return true;
    }

    public static boolean getPerms(Location location, String flag, boolean def) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
        if (res != null) {
            ResidencePermissions perms = res.getPermissions();
            return perms.has(Flags.getFlag(flag), def);
        }
        return true;
    }

    public static long getLimit(Player player) {
        Map<String, Long> userGroupMap = ResidenceUtils.getUserGroupMap(player);
        return userGroupMap.get(userGroupMap.keySet().iterator().next());
    }

    public static long getNewSize(CuboidArea area) {
        long size = 0;
        if (ConfigHandler.getConfigPath().isPointsMode()) {
            size += area.getXSize() * area.getZSize();
        } else {
            size += area.getXSize() * area.getZSize() * area.getYSize();
        }
        return size;
    }

    public static long getSize(ClaimedResidence res) {
        long size = 0;
        boolean ignoreXYZ = ConfigHandler.getConfigPath().isPointsIgnoreXYZ();
        boolean allAreas = ConfigHandler.getConfigPath().isResAllAreas();
        boolean mode = ConfigHandler.getConfigPath().isPointsMode();

        CuboidArea mainArea = res.getMainArea();
        for (CuboidArea area : res.getAreaArray()) {
            if (ignoreXYZ) {
                if (area.getYSize() < 256) {
                    continue;
                }
            }
            if (allAreas) {
                if (!mainArea.equals(area) && mainArea.isAreaWithinArea(area)) {
                    continue;
                }
            }
            if (mode) {
                size += area.getXSize() * area.getZSize();
            } else {
                size += area.getXSize() * area.getZSize() * area.getYSize();
            }
        }
        return size;
    }

    public static long getUsed(Player player) {
        long used = 0;
        boolean mode = ConfigHandler.getConfigPath().isPointsMode();
        boolean ignoreXYZ = ConfigHandler.getConfigPath().isPointsIgnoreXYZ();
        boolean allAreas = ConfigHandler.getConfigPath().isResAllAreas();
        boolean ignoreWithin = ConfigHandler.getConfigPath().isResIgnoreWithin();
        CuboidArea mainArea;
        for (ClaimedResidence res : ResidenceApi.getPlayerManager().getResidencePlayer(player.getName()).getResList()) {
            mainArea = res.getMainArea();
            if (allAreas) {
                for (CuboidArea area : res.getAreaArray()) {
                    if (!ignoreWithin && mainArea.isAreaWithinArea(area)) {
                        continue;
                    }
                    if (ignoreXYZ) {
                        if (area.getYSize() < 256) {
                            continue;
                        }
                    }
                    if (mode) {
                        used += area.getXSize() * area.getZSize();
                    } else {
                        used += area.getXSize() * area.getZSize() * area.getYSize();
                    }
                }
            } else {
                if (ignoreXYZ) {
                    if (mainArea.getYSize() < 256) {
                        continue;
                    }
                }
                if (mode) {
                    used += mainArea.getXSize() * mainArea.getZSize();
                } else {
                    used += mainArea.getXSize() * mainArea.getZSize() * mainArea.getYSize();
                }
            }
        }
        return used;
    }

    private static Map<String, Long> getUserGroupMap(Player player) {
        Map<String, Long> userGroupMap = new HashMap<>();
        Map<String, Long> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        String permission;
        String group;
        String highestGroup;
        for (PermissionAttachmentInfo pa : player.getEffectivePermissions()) {
            if (player.isOp()) {
                highestGroup = groupMap.keySet().iterator().next();
                userGroupMap.put(highestGroup, groupMap.get(highestGroup));
                break;
            }
            permission = pa.getPermission();
            if (permission.startsWith("regionplus.points.group.")) {
                group = permission.replaceFirst("regionplus.points.group.", "");
                group = group.toLowerCase();
                if (group.equals("*")) {
                    highestGroup = groupMap.keySet().iterator().next();
                    userGroupMap.put(highestGroup, groupMap.get(highestGroup));
                }
                if (ConfigHandler.getConfigPath().getPointsMap().get(group) != null) {
                    userGroupMap.put(group, ConfigHandler.getConfigPath().getPointsMap().get(group));
                }
            }
        }
        if (userGroupMap.isEmpty()) {
            userGroupMap.put("default", ConfigHandler.getConfigPath().getPointsDefault());
        }
        return Utils.sortByValue(userGroupMap);
    }

    public static void editMessage() {
        String playerName;
        String resName;
        boolean bypassPerm = ConfigHandler.getConfigPath().isRMBypassPerms();
        boolean message = ConfigHandler.getConfigPath().isRMMessage();
        List<String> groups = getResGroups();
        String groupName;
        Table<String, String, List<String>> groupOldTable = ConfigHandler.getConfigPath().getRMGroupTable();
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
                    ServerHandler.sendConsoleMessage("&eMessage-Edit process has not finished yet! &8- &6Player: " + i + "/" + playerSize);
                }
            }
            playerName = offlinePlayer.getName();
            if (bypassPerm) {
                if (PermissionsHandler.hasPermissionOffline(offlinePlayer, "regionplus.bypass.messageedit")) {
                    ServerHandler.debugMessage("Message-Flags-Editor", playerName, "permission", "bypass");
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
                        ServerHandler.sendDebugMessage("&6Edit message: &e" + resName + "&8 - &f" + "enter" + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                    }
                    if (leave.isEmpty() || leave.contains(res.getLeaveMessage())) {
                        res.setLeaveMessage(groupTable.get(groupName, "leave"));
                        ServerHandler.sendDebugMessage("&6Edit message: &e" + resName + "&8 - &f" + "leave" + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                    }
                }
            }
        }
        ServerHandler.sendConsoleMessage("&6Message-Edit process has ended.");
    }

    public static void editFlags() {
        FlagsEditor flagsEditor = ConfigHandler.getEditor();
        flagsEditor.setUp();
        boolean restartMsg = ConfigHandler.getConfigPath().isRFMessage();
        startEditFlags(flagsEditor.getEditList());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (flagsEditor.isEnd()) {
                    try {
                        flagsEditor.resetUp();
                        startEditFlags(flagsEditor.getEditList());
                    } catch (Exception e) {
                        ServerHandler.sendConsoleMessage("&cThere is an error occur while starting to edit residence flags.");
                        ServerHandler.sendConsoleMessage("&cPlease turn on the debug mode and send the error messages to plugin author.");
                        ServerHandler.sendConsoleMessage("&ehttps://github.com/momoservertw/RegionPlus/issues");
                        ServerHandler.sendConsoleMessage("&6Flags-Edit process has ended.");
                        flagsEditor.setRun(false);
                        ServerHandler.sendDebugTrace(e);
                        cancel();
                        return;
                    }
                    if (!flagsEditor.isRun()) {
                        ServerHandler.sendConsoleMessage("&6Flags-Edit process has ended.");
                        flagsEditor.setRun(false);
                        cancel();
                        return;
                    }
                    if (!flagsEditor.isRestart()) {
                        ServerHandler.sendConsoleMessage("&6Flags-Edit process has ended.");
                        flagsEditor.setRun(false);
                        cancel();
                        return;
                    }
                    if (restartMsg) {
                        ServerHandler.sendConsoleMessage("&eFlags-Edit process has not finished yet! &8- &6Player: " + flagsEditor.getLast() + "/" + flagsEditor.getPlayerSize());
                        ServerHandler.sendConsoleMessage("&fIt will restart after few seconds. &8(Stop process: /rp flagsedit stop)");
                        ServerHandler.sendConsoleMessage("");
                    }
                }
            }
        }.runTaskTimer(RegionPlus.getInstance(), 0, ConfigHandler.getConfigPath().getRFMaxInterval() * 20);
    }

    private static void startEditFlags(List<OfflinePlayer> editList) {
        List<String> groups = getResGroups();
        String playerName;
        String resName;
        ResidencePermissions perms;
        PermissionGroup group;
        String groupName;
        String[] listSplit;
        String[] listSplit2;
        List<String> permsPlayerList = new ArrayList<>();
        Set<Map.Entry<String, Boolean>> flagSet;
        Set<Map.Entry<String, Boolean>> defaultFlagSet;
        List<String> removeFlagList = new ArrayList<>();
        List<String> removeFlagPlayerList = new ArrayList<>();
        Map<String, String> addFlagMap = new HashMap<>();
        String flag;
        Map<String, Boolean> permsPlayerFlags;
        boolean RFBypassPerm = ConfigHandler.getConfigPath().isRFBypassPerms();
        boolean RFBypassCustom = ConfigHandler.getConfigPath().isRFBypassCustom();
        boolean RFDefaultRemove = ConfigHandler.getConfigPath().isRFDefaultRemove();
        boolean RFDefaultRemoveOnly = ConfigHandler.getConfigPath().isRFDefaultRemoveOnly();
        List<String> RFDefaultRemoveIgnore = ConfigHandler.getConfigPath().getRFDefaultRemoveIgnore();
        boolean RFDefaultUpdate = ConfigHandler.getConfigPath().isRFDefaultUpdate();
        List<String> RFDefaultUpdateIgnore = ConfigHandler.getConfigPath().getRFDefaultUpdateIgnore();
        boolean RFPermsRemove = ConfigHandler.getConfigPath().isRFPermsRemove();
        List<String> RFPermsRemoveIgnore = ConfigHandler.getConfigPath().getRFPermsRemoveIgnore();

        for (OfflinePlayer offlinePlayer : editList) {
            playerName = offlinePlayer.getName();
            if (RFBypassPerm) {
                if (PermissionsHandler.hasPermissionOffline(offlinePlayer, "regionplus.bypass.flagsedit")) {
                    ServerHandler.debugMessage("Residence-Flags-Editor", playerName, "permission", "bypass");
                    break;
                }
            }
            if (playerName != null) {
                groupName = getUserGroup(offlinePlayer, groups);
                group = Residence.getInstance().getPermissionManager().getGroupByName(groupName);
                for (ClaimedResidence res : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
                    resName = res.getName();
                    perms = res.getPermissions();
                    // The default flags of that group.
                    defaultFlagSet = group.getDefaultResidenceFlags();
                    // The default flags of that residence.
                    flagSet = perms.getFlags().entrySet();
                    // Default - Remove
                    if (RFDefaultRemove) {
                        for (Map.Entry<String, Boolean> flagEntry : flagSet) {
                            if (!defaultFlagSet.contains(flagEntry)) {
                                flag = flagEntry.getKey();
                                if (RFDefaultRemoveIgnore.contains(flag)) {
                                    ServerHandler.sendDebugMessage("&5Bypass default flag: &e" + resName + "&8 - &f" + flag + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                                    break;
                                }
                                try {
                                    if (RFDefaultRemoveOnly) {
                                        if (group.hasFlagAccess(Flags.valueOf(flag))) {
                                            break;
                                        }
                                    }
                                    removeFlagList.add(flag);
                                } catch (Exception e) {
                                    if (RFBypassCustom) {
                                        ServerHandler.sendDebugMessage("&5Bypass default flag: &e" + resName + "&8 - &f" + flag + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                                    } else {
                                        removeFlagList.add(flag);
                                    }
                                }
                            }
                        }
                        for (String removeFlag : removeFlagList) {
                            perms.setFlag(removeFlag, FlagPermissions.FlagState.NEITHER);
                            ServerHandler.sendDebugMessage("&4Remove default flag: &e" + resName + "&8 - &f" + removeFlag + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                        }
                    }
                    // Default - Update
                    if (RFDefaultUpdate) {
                        for (Map.Entry<String, Boolean> resFlagEntry : flagSet) {
                            if (!flagSet.contains(resFlagEntry)) {
                                flag = resFlagEntry.getKey();
                                if (RFDefaultUpdateIgnore.contains(flag)) {
                                    ServerHandler.sendDebugMessage("&5Bypass update default flag: &e" + resName + "&8 - &f" + flag + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                                    break;
                                }
                                if (!group.hasFlagAccess(Flags.valueOf(flag))) {
                                    addFlagMap.put(flag, resFlagEntry.getValue().toString());
                                }
                            }
                        }
                        for (String addFlagKey : addFlagMap.keySet()) {
                            flag = addFlagMap.get(addFlagKey);
                            perms.setFlag(addFlagKey, FlagPermissions.FlagState.valueOf(flag.toUpperCase()));
                            ServerHandler.sendDebugMessage("&6Add default flag: &e" + resName + "&8 - &f" + addFlagKey + "=" + flag + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                        }
                    }
                    // Permission (player) - Remove
                    if (RFPermsRemove) {
                        // Get player permission list in residence.
                        listSplit = perms.listPlayersFlags().split("§f\\[");
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
                            permsPlayerFlags = perms.getPlayerFlags(permsPlayer);
                            if (permsPlayerFlags != null) {
                                for (String permsPlayerFlag : permsPlayerFlags.keySet()) {
                                    if (RFPermsRemoveIgnore.contains(permsPlayerFlag)) {
                                        ServerHandler.sendDebugMessage("&5Bypass player flag: &e" + resName + "&8 - &f" + permsPlayerFlag + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                                        break;
                                    }
                                    try {
                                        if (!group.hasFlagAccess(Flags.valueOf(permsPlayerFlag))) {
                                            removeFlagPlayerList.add(permsPlayerFlag);
                                        }
                                    } catch (Exception e) {
                                        if (RFBypassCustom) {
                                            ServerHandler.sendDebugMessage("&5Bypass player flag: &e" + resName + "&8 - &f" + permsPlayerFlag + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
                                        } else {
                                            removeFlagPlayerList.add(permsPlayerFlag);
                                        }
                                    }
                                }
                                for (String removePlayerFlag : removeFlagPlayerList) {
                                    perms.setPlayerFlag(permsPlayer, removePlayerFlag, FlagPermissions.FlagState.NEITHER);
                                    ServerHandler.sendDebugMessage("&cRemove player flag: &e" + resName + "&8 - &6" + permsPlayer + "&8 - &f" + removePlayerFlag + " &7(Owner: " + playerName + ", Group: " + groupName + ")");
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
            if (PermissionsHandler.hasPermissionOffline(offlinePlayer, "residence.group." + group)) {
                return group;
            }
        }
        return "default";
    }

    public static String[] pointsPH(Player player) {
        Map<String, Long> userGroupMap = ResidenceUtils.getUserGroupMap(player);
        Map<String, String> groupDisplayMap = ConfigHandler.getConfigPath().getPointsDisplayMap();
        Map<String, Long> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        List<String> groupList = new ArrayList<>(groupMap.keySet());

        String group = userGroupMap.keySet().iterator().next();
        long limit = userGroupMap.get(group);
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
        String[] placeHolders = Language.newString();

        // %player%
        placeHolders[1] = player.getName();
        // %points_group%
        placeHolders[21] = groupDisplayMap.get(group);
        // %points_limit%
        placeHolders[22] = String.valueOf(limit);
        // %points_used%
        placeHolders[23] = String.valueOf(used);
        // %points_last%
        placeHolders[24] = String.valueOf(limit - used);
        // %points_nextgroup%
        placeHolders[27] = groupDisplayMap.get(nextGroup);
        // %points_nextlimit%
        placeHolders[28] = String.valueOf(nextLimit);
        // %points_nextbonus%
        placeHolders[29] = String.valueOf(nextLimit - limit);
        return placeHolders;
    }

    public static String[] targetPointsPH(CommandSender sender, Player player) {
        Map<String, Long> userGroupMap = ResidenceUtils.getUserGroupMap(player);
        Map<String, Long> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        Map<String, String> groupDisplayMap = ConfigHandler.getConfigPath().getPointsDisplayMap();
        List<String> groupList = new ArrayList<>(groupMap.keySet());
        String group = userGroupMap.keySet().iterator().next();
        String nextGroup;
        long limit = userGroupMap.get(group);
        long nextLimit;
        if (groupList.indexOf(group) > 0) {
            nextGroup = groupList.get(groupList.indexOf(group) - 1);
            nextLimit = groupMap.get(nextGroup);
        } else {
            nextGroup = group;
            nextLimit = limit;
        }
        long used = ResidenceUtils.getUsed(player);
        String[] placeHolders = Language.newString();

        // %player%
        placeHolders[1] = sender.getName();
        // %target%
        placeHolders[2] = player.getName();
        // %points_group%
        placeHolders[21] = groupDisplayMap.get(group);
        // %points_limit%
        placeHolders[22] = String.valueOf(limit);
        // %points_used%
        placeHolders[23] = String.valueOf(used);
        // %points_last%
        placeHolders[24] = String.valueOf(limit - used);
        // %points_nextgroup%
        placeHolders[27] = groupDisplayMap.get(nextGroup);
        // %points_nextlimit%
        placeHolders[28] = String.valueOf(nextLimit);
        // %points_nextbonus%
        placeHolders[29] = String.valueOf(nextLimit - limit);
        return placeHolders;
    }

    public static String[] selectPointsPH(Player player, long size) {
        Map<String, Long> userGroupMap = ResidenceUtils.getUserGroupMap(player);
        Map<String, Long> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        Map<String, String> groupDisplayMap = ConfigHandler.getConfigPath().getPointsDisplayMap();
        List<String> groupList = new ArrayList<>(groupMap.keySet());
        String group = userGroupMap.keySet().iterator().next();
        String nextGroup;
        long limit = userGroupMap.get(group);
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
        String[] placeHolders = Language.newString();

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
