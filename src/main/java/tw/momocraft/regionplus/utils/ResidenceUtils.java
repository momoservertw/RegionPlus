package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;

import java.util.*;

public class ResidenceUtils {

    private void getSelete(Player player) {
        SelectionManager smanager = Residence.getInstance().getSelectionManager();
        smanager.getSelection(player).getBaseLoc1();
        smanager.getSelection(player).getBaseLoc2();
    }

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



    public static long getSize(ClaimedResidence res) {
        long size = 0;
        String mode = ConfigHandler.getRegionConfig().getResPointsMode();
        boolean ignoreXYZ = ConfigHandler.getRegionConfig().isResPointsIgnoreXYZ();

        CuboidArea mainArea = res.getMainArea();
        for (CuboidArea area : res.getAreaArray()) {
            if (mainArea.isAreaWithinArea(area)) {
                continue;
            }
            if (ignoreXYZ) {
                if (area.getYSize() < 256) {
                    continue;
                }
            }
            if (mode.equals("XZ")) {
                size += area.getXSize() * area.getZSize();
            } else {
                size += area.getXSize() * area.getZSize() * area.getYSize();
            }
        }
        return size;
    }

    public static long getUsed(Player player) {
        long used = 0;
        String mode = ConfigHandler.getRegionConfig().getResPointsMode();
        boolean ignoreXYZ = ConfigHandler.getRegionConfig().isResPointsIgnoreXYZ();
        boolean allAreas = ConfigHandler.getRegionConfig().isResPointsAllAreas();
        boolean ignoreWithin = ConfigHandler.getRegionConfig().isResPointsIgnoreWithin();

        List<ClaimedResidence> resList = ResidenceApi.getPlayerManager().getResidencePlayer(player.getName()).getResList();
        CuboidArea mainArea;
        for (ClaimedResidence res : resList) {
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
                    if (mode.equals("XZ")) {
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
                if (mode.equals("XZ")) {
                    used += mainArea.getXSize() * mainArea.getZSize();
                } else {
                    used += mainArea.getXSize() * mainArea.getZSize() * mainArea.getYSize();
                }
            }
        }
        return used;
    }

    public static long getLimit(Player player) {
        List<Long> sizeList = new ArrayList<>();
        String permission;
        String group;
        for (PermissionAttachmentInfo pa : player.getEffectivePermissions()) {
            permission = pa.getPermission();
            if (permission.startsWith("regionplus.points.limit.")) {
                sizeList.add(Long.valueOf(permission.replaceFirst("regionplus.points.limit.", "")));
            }
            if (permission.startsWith("regionplus.points.group.")) {
                group = permission.replaceFirst("regionplus.points.limit.", "");
                sizeList.add(ConfigHandler.getRegionConfig().getResPointsLimitMap().get(group));
            }
        }
        if (sizeList.isEmpty()) {
            return ConfigHandler.getRegionConfig().getResPointsDefault();
        }
        if (Collections.max(sizeList) == null) {
            return ConfigHandler.getRegionConfig().getResPointsDefault();
        }
        return Collections.max(sizeList);
    }

    public static long getRemainder(Player player) {
        if (player != null) {
            long pointsLimit = getLimit(player);
            long pointsUsed = getUsed(player);
            return pointsLimit - pointsUsed;
        }
        return 0;
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
        ResidencePermissions perms;
        PermissionGroup ownerGroup;
        String[] listSplit;
        String[] listSplit2;
        List<String> permsPlayerList = new ArrayList<>();
        Set<Map.Entry<String, Boolean>> flagEntrySet;
        Set<Map.Entry<String, Boolean>> groupFlagEntrySet;
        List<String> removeFlagList = new ArrayList<>();
        List<String> removeFlagPlayerList = new ArrayList<>();
        Map<String, String> addFlagMap = new HashMap<>();
        String addFlag;
        Map<String, Boolean> permsPlayerFlags;
        boolean restart;
        int last;
        int maxPlayers = ConfigHandler.getRegionConfig().getResFlagEditMax();
        List<OfflinePlayer> playerList = new ArrayList<>(Arrays.asList(Bukkit.getOfflinePlayers()));
        restart = maxPlayers > 0 && playerList.size() > maxPlayers;
        int startAt = 0;
        List<OfflinePlayer> editList;
        if (restart) {
            editList = playerList.subList(0, maxPlayers);
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
                    for (ClaimedResidence claimedResidence : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
                        perms = claimedResidence.getPermissions();
                        ownerGroup = ResidenceApi.getPlayerManager().getGroup(claimedResidence.getOwner());
                        flagEntrySet = claimedResidence.getPermissions().getFlags().entrySet();
                        groupFlagEntrySet = ownerGroup.getDefaultResidenceFlags();
                        if (ConfigHandler.getRegionConfig().isResFlagEditRemove()) {
                            for (Map.Entry<String, Boolean> resFlagEntry : flagEntrySet) {
                                if (!groupFlagEntrySet.contains(resFlagEntry)) {
                                    removeFlagList.add(resFlagEntry.getKey());
                                }
                            }
                            for (String removeFlag : removeFlagList) {
                                perms.setFlag(removeFlag, FlagPermissions.FlagState.NEITHER);
                                ServerHandler.sendDebugMessage("&4Remove default flag: &e" + claimedResidence.getName() + "&8 - &f" + removeFlag);
                            }
                        }
                        if (ConfigHandler.getRegionConfig().isResFlagEditUpdate()) {
                            for (Map.Entry<String, Boolean> resFlagEntry : groupFlagEntrySet) {
                                if (!flagEntrySet.contains(resFlagEntry)) {
                                    addFlagMap.put(resFlagEntry.getKey(), resFlagEntry.getValue().toString());
                                }
                            }
                            for (String addFlagKey : addFlagMap.keySet()) {
                                addFlag = addFlagMap.get(addFlagKey);
                                perms.setFlag(addFlagKey, FlagPermissions.FlagState.valueOf(addFlagMap.get(addFlagKey).toUpperCase()));
                                ServerHandler.sendDebugMessage("&6Add default flag: &e" + claimedResidence.getName() + "&8 - &f" + addFlagKey + "=" + addFlag);
                            }
                        }
                        if (ConfigHandler.getRegionConfig().isResFlagEditRemovePerm()) {
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
                            for (String permsPlayer : permsPlayerList) {
                                permsPlayerFlags = perms.getPlayerFlags(permsPlayer);
                                if (permsPlayerFlags != null) {
                                    for (String flag : permsPlayerFlags.keySet()) {
                                        if (!ownerGroup.hasFlagAccess(Flags.getFlag(flag))) {
                                            removeFlagPlayerList.add(flag);
                                        }
                                    }
                                    for (String removePlayerFlag : removeFlagPlayerList) {
                                        perms.setPlayerFlag(permsPlayer, removePlayerFlag, FlagPermissions.FlagState.NEITHER);
                                        ServerHandler.sendDebugMessage("&cRemove player flag: &e" + claimedResidence.getName() + "&8 - &6" + permsPlayer + "&8 - &f" + removePlayerFlag);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (restart) {
                last = playerList.size() - startAt;
                editList = playerList.subList(startAt, last > maxPlayers ? startAt += maxPlayers : last);
                if (editList.isEmpty()) {
                    break;
                }
                ServerHandler.sendConsoleMessage("&eFlags-Edit process has not finished yet! &8- &6Last: " + last);
                ServerHandler.sendConsoleMessage("&fIt will restart after 3 seconds. &8(Stop process: /rp flagsedit stop)");
                ServerHandler.sendConsoleMessage("");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ServerHandler.sendConsoleMessage("&6Starting to check residence flags...");
                    }
                }.runTaskLater(RegionPlus.getInstance(), 60L);
            } else {
                break;
            }
        }
        ServerHandler.sendConsoleMessage("&6Flags-Edit process has ended.");
        flagsEditRun = false;
    }
}
