package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
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
            return perms.has(Flags.getFlag(flag), def);
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
                return perms.has(Flags.getFlag(flag), def);
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
        if (ConfigHandler.getRegionConfig().isPointsMode()) {
            size += area.getXSize() * area.getZSize();
        } else {
            size += area.getXSize() * area.getZSize() * area.getYSize();
        }
        return size;
    }

    public static long getSize(ClaimedResidence res) {
        long size = 0;
        boolean ignoreXYZ = ConfigHandler.getRegionConfig().isPointsIgnoreXYZ();
        boolean allAreas = ConfigHandler.getRegionConfig().isResAllAreas();
        boolean mode = ConfigHandler.getRegionConfig().isPointsMode();

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
        boolean mode = ConfigHandler.getRegionConfig().isPointsMode();
        boolean ignoreXYZ = ConfigHandler.getRegionConfig().isPointsIgnoreXYZ();
        boolean allAreas = ConfigHandler.getRegionConfig().isResAllAreas();
        boolean ignoreWithin = ConfigHandler.getRegionConfig().isResIgnoreWithin();
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
        Map<String, Long> groupMap = ConfigHandler.getRegionConfig().getPointsMap();
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
                if (ConfigHandler.getRegionConfig().getPointsMap().get(group) != null) {
                    userGroupMap.put(group, ConfigHandler.getRegionConfig().getPointsMap().get(group));
                }
            }
        }
        if (userGroupMap.isEmpty()) {
            userGroupMap.put("default", ConfigHandler.getRegionConfig().getPointsDefault());
        }
        return Utils.sortByValue(userGroupMap);
    }

    private boolean flagsEditRun = false;

    public void setFlagsEditRun(boolean flagsEditRun) {
        this.flagsEditRun = flagsEditRun;
    }

    public boolean getFlagsEditRun() {
        return this.flagsEditRun;
    }

    public void resetNoPermsFlags() {
        flagsEditRun = true;
        String playerName;
        String resName;
        ResidencePermissions perms;
        PermissionGroup group;
        String[] listSplit;
        String[] listSplit2;
        List<String> permsPlayerList = new ArrayList<>();
        Set<Map.Entry<String, Boolean>> flagSet;
        Set<Map.Entry<String, Boolean>> defaultFlagSet;
        String flagKey;
        String flagValue;
        Map<String, Boolean> permsPlayerFlags;
        boolean restart;
        int last;
        int maxLimit = ConfigHandler.getRegionConfig().getRFMaxLimit();
        long maxInterval = ConfigHandler.getRegionConfig().getRFMaxInterval() * 20;
        boolean restartMsg = ConfigHandler.getRegionConfig().isRFMessage();
        List<OfflinePlayer> playerList = new ArrayList<>(Arrays.asList(Bukkit.getOfflinePlayers()));
        int playerSize = playerList.size();
        restart = maxLimit > 0 && playerList.size() > maxLimit;
        int startAt = 0;
        List<OfflinePlayer> editList;
        if (restart) {
            editList = playerList.subList(0, maxLimit);
        } else {
            editList = playerList;
        }
        while (true) {
            if (!flagsEditRun) {
                break;
            }
            for (OfflinePlayer offlinePlayer : editList) {
                playerName = offlinePlayer.getName();
                if (playerName != null) {
                    for (ClaimedResidence res : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
                        resName = res.getName();
                        perms = res.getPermissions();
                        group = ResidenceApi.getPlayerManager().getGroup(res.getOwner());
                        // The default flags of that group.
                        defaultFlagSet = group.getDefaultResidenceFlags();
                        // The default flags of that residence.
                        flagSet = perms.getFlags().entrySet();
                        // Default - Remove
                        if (ConfigHandler.getRegionConfig().isRFRemove()) {
                            for (Map.Entry<String, Boolean> flag : flagSet) {
                                if (!defaultFlagSet.contains(flag)) {
                                    flagKey = flag.getKey();
                                    perms.setFlag(flagKey, FlagPermissions.FlagState.NEITHER);
                                    ServerHandler.sendDebugMessage("&4Remove default flag: &e" + resName + "&8 - &f" + flagKey);
                                }
                            }
                        }
                        // Default - Update
                        if (ConfigHandler.getRegionConfig().isRFUpdate()) {
                            for (Map.Entry<String, Boolean> defaultFlag : defaultFlagSet) {
                                if (!flagSet.contains(defaultFlag)) {
                                    flagKey = defaultFlag.getKey();
                                    flagValue = defaultFlag.getValue().toString();
                                    perms.setFlag(defaultFlag.getKey(), FlagPermissions.FlagState.valueOf(flagValue.toUpperCase()));
                                    ServerHandler.sendDebugMessage("&6Add default flag: &e" + resName + "&8 - &f" + flagKey + "=" + flagValue);
                                }
                            }
                        }
                        // Permission (player) - Remove
                        if (ConfigHandler.getRegionConfig().isRFRemovePerm()) {
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
                                    for (String flag : permsPlayerFlags.keySet()) {
                                        if (!group.hasFlagAccess(Flags.valueOf(flag))) {
                                            perms.setPlayerFlag(permsPlayer, flag, FlagPermissions.FlagState.NEITHER);
                                            ServerHandler.sendDebugMessage("&cRemove player flag: &e" + resName + "&8 - &6" + permsPlayer + "&8 - &f" + flag);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (restart) {
                last = playerSize - startAt;
                editList = playerList.subList(startAt, last >= maxLimit ? startAt += maxLimit : startAt + last);
                if (editList.size() < maxLimit) {
                    restart = false;
                }
                if (restartMsg) {
                    ServerHandler.sendConsoleMessage("&eFlags-Edit process has not finished yet! &8- &6Last: " + last + "/" + playerSize);
                    ServerHandler.sendConsoleMessage("&fIt will restart after few seconds. &8(Stop process: /rp flagsedit stop)");
                    ServerHandler.sendConsoleMessage("");
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (restartMsg) {
                            ServerHandler.sendConsoleMessage("&6Starting to check residence flags...");
                        }
                    }
                }.runTaskLater(RegionPlus.getInstance(), maxInterval);
            } else {
                break;
            }
        }
        ServerHandler.sendConsoleMessage("&6Flags-Edit process has ended.");
        flagsEditRun = false;
    }

    public static String[] pointsPH(Player player) {
        Map<String, Long> userGroupMap = ResidenceUtils.getUserGroupMap(player);
        Map<String, String> groupDisplayMap = ConfigHandler.getRegionConfig().getPointsDisplayMap();
        Map<String, Long> groupMap = ConfigHandler.getRegionConfig().getPointsMap();
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
        Map<String, Long> groupMap = ConfigHandler.getRegionConfig().getPointsMap();
        Map<String, String> groupDisplayMap = ConfigHandler.getRegionConfig().getPointsDisplayMap();
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
        Map<String, Long> groupMap = ConfigHandler.getRegionConfig().getPointsMap();
        Map<String, String> groupDisplayMap = ConfigHandler.getRegionConfig().getPointsDisplayMap();
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
