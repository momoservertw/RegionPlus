package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.*;

public class ResidenceReset {

    public static void resetAll(CommandSender sender) {
        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPlugin(), sender, "&6Starting to reset residences...");
        List<String> bypassOwners = ConfigHandler.getConfigPath().getResResetAllBypassResOwners();
        List<String> bypassRes = ConfigHandler.getConfigPath().getResResetAllBypassRes();
        Map<String, ClaimedResidence> resMap = Residence.getInstance().getResidenceManager().getResidences();
        ClaimedResidence res;
        String playerName;
        for (String resName : resMap.keySet()) {
            res = resMap.get(resName);
            playerName = res.getOwner();
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(),
                    "&eResidence: " + resName + " (owner: " + playerName + ")");
            // Bypass Owners.
            if (bypassOwners.contains(playerName)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-All", "Owner", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            // Bypass Residences.
            if (bypassRes.contains(resName)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-All", "Residence", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            // Checking the bypass permission.
            if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), res.getOwnerUUID(), "regionplus.bypass.residence.reset")) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-All", "Permission", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                break;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "res reset " + resName);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&6Succeed to reset all residence!");
            }
        }.runTaskLater(RegionPlus.getInstance(), 40);
    }

    public static void resetFlags(CommandSender sender) {
        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPlugin(), sender, "&6Starting to check residence flags...");
        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPlugin(), sender, "&7The detail information is print on console.");
        boolean bypassCustom = ConfigHandler.getConfigPath().isResResetFlagsBypassCustom();
        List<String> bypassOwners = ConfigHandler.getConfigPath().getResResetFlagsByPassResOwners();
        List<String> bypassRes = ConfigHandler.getConfigPath().getResResetFlagsByPassRes();
        boolean envRemove = ConfigHandler.getConfigPath().isResResetFlagsRemove();
        List<String> envRemoveIgnore = ConfigHandler.getConfigPath().getResResetFlagsRemoveIgnore();
        boolean envUpdate = ConfigHandler.getConfigPath().isResResetFlagsUpdate();
        List<String> envUpdateIgnore = ConfigHandler.getConfigPath().getResResetFlagsUpdateIgnore();
        boolean playerRemove = ConfigHandler.getConfigPath().isResResetFlagsPlayerRemove();
        List<String> playerRemoveIgnore = ConfigHandler.getConfigPath().getResResetFlagsPlayerRemoveIgnore();

        Map<String, ClaimedResidence> resMap = Residence.getInstance().getResidenceManager().getResidences();
        String playerName;
        ClaimedResidence res;
        PermissionGroup group;
        ResidencePermissions resPerm;
        Set<Map.Entry<String, Boolean>> defaultFlags;
        Map<String, Boolean> resFlagMap;
        String flag;
        List<String> removeFlags;
        for (String resName : resMap.keySet()) {
            res = resMap.get(resName);
            playerName = res.getOwner();
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(),
                    "&eResidence: " + resName + " (owner: " + playerName + ")");
            // Bypass Owners.
            if (bypassOwners.contains(playerName)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-Flags", "Owner", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            // Bypass Residences.
            if (bypassRes.contains(resName)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-Flags", "Residence", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            // Checking the bypass permission.
            if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), res.getOwnerUUID(), "regionplus.bypass.residence.reset")) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-Flags", "Permission", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                break;
            }
            group = res.getOwnerGroup();
            defaultFlags = group.getDefaultResidenceFlags();
            resPerm = res.getPermissions();
            resFlagMap = resPerm.getFlags();
            // Environment - Remove
            if (envRemove) {
                removeFlags = new ArrayList<>();
                for (String resFlag : resFlagMap.keySet()) {
                    flag = resFlag;
                    try {
                        Flags.valueOf(flag);
                    } catch (Exception ex) {
                        if (!bypassCustom) {
                            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                                    "Reset-Flags", "Environment", playerName, "bypass", resName + ", Custom: " + flag,
                                    new Throwable().getStackTrace()[0]);
                            continue;
                        }
                        removeFlags.add(flag);
                        continue;
                    }
                    if (group.hasFlagAccess(Flags.valueOf(flag)) || envRemoveIgnore.contains(flag)) {
                        break;
                    }
                    removeFlags.add(flag);
                }
                for (String removeFlag : removeFlags) {
                    resPerm.setFlag(removeFlag, FlagPermissions.FlagState.NEITHER);
                    CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(),
                            "&cRemove flag - " + removeFlag + "=remove");
                }
            }
            // Environment - Update
            if (envUpdate) {
                for (Map.Entry<String, Boolean> defaultFlag : defaultFlags) {
                    flag = defaultFlag.getKey();
                    if (resFlagMap.containsKey(flag) || envUpdateIgnore.contains(flag)) {
                        continue;
                    }
                    if (defaultFlag.getValue()) {
                        resPerm.setFlag(flag, FlagPermissions.FlagState.TRUE);
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(),
                                "&aAdd flag - " + flag + "=true");
                    } else {
                        resPerm.setFlag(flag, FlagPermissions.FlagState.FALSE);
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(),
                                "&aAdd flag - " + flag + "=false");
                    }
                }
            }
            // Player - Remove
            if (playerRemove) {
                // Map < playerName, Map < flag, value > >
                Map<String, Map<String, Boolean>> playerFlagMap = getPlayerFlagMap(resPerm);
                // key: playerName
                removeFlags = new ArrayList<>();
                for (String key : playerFlagMap.keySet()) {
                    // key2: flag
                    for (String key2 : playerFlagMap.get(key).keySet()) {
                        flag = key2;
                        try {
                            Flags.valueOf(flag);
                        } catch (Exception ex) {
                            if (!bypassCustom) {
                                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                                        "Reset-Flags", "Environment", playerName, "bypass", resName + ", Custom: " + flag,
                                        new Throwable().getStackTrace()[0]);
                                continue;
                            }
                            removeFlags.add(flag);
                            continue;
                        }
                        if (group.hasFlagAccess(Flags.valueOf(flag)) || playerRemoveIgnore.contains(flag)) {
                            break;
                        }
                        removeFlags.add(flag);
                    }
                    for (String removeFlag : removeFlags) {
                        resPerm.setFlag(removeFlag, FlagPermissions.FlagState.NEITHER);
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(),
                                "&cRemove flag - " + key + ": " + removeFlag + "=remove");
                    }
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&6Succeed to reset all residence flags!");
            }
        }.runTaskLater(RegionPlus.getInstance(), 40);
    }

    public static void resetMessage(CommandSender sender) {
        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPlugin(), sender, "&6Starting to check residence messagess...");
        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPlugin(), sender, "&7The detail information is print on console.");
        List<String> bypassOwners = ConfigHandler.getConfigPath().getResResetMsgBypassResOwners();
        List<String> bypassRes = ConfigHandler.getConfigPath().getResResetMsgBypassRes();
        String playerName;
        ClaimedResidence res;
        PermissionGroup group;
        Map<String, ClaimedResidence> resMap = Residence.getInstance().getResidenceManager().getResidences();
        for (String resName : resMap.keySet()) {
            res = resMap.get(resName);
            playerName = res.getOwner();
            group = res.getOwnerGroup();
            if (group.canSetEnterLeaveMessages()) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-Message", "Can edit", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            // Bypass Owners.
            if (bypassOwners.contains(playerName)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-Message", "Owner", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            // Bypass Residences.
            if (bypassRes.contains(resName)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-Message", "Residence", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            // Checking the bypass permission.
            if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), res.getOwnerUUID(), "regionplus.bypass.residence.reset")) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                        "Reset-Message", "Permission", playerName, "bypass", resName,
                        new Throwable().getStackTrace()[0]);
                break;
            }
            res.setEnterMessage(group.getDefaultEnterMessage());
            res.setLeaveMessage(group.getDefaultLeaveMessage());
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(), "&cReset message - " + resName);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&6Succeed to reset all residence messages!");
            }
        }.runTaskLater(RegionPlus.getInstance(), 40);
    }

    private static Map<String, Map<String, Boolean>> getPlayerFlagMap(ResidencePermissions resPerm) {
        Map<String, Map<String, Boolean>> playerFlagMap = new HashMap<>();
        String playerName;
        String[] playerFlagSplit = resPerm.listPlayersFlags().split("§f]");
        Map<String, Boolean> flagMap;
        for (String playerPerm : playerFlagSplit) {
            flagMap = new HashMap<>();
            playerName = playerPerm.split("§f\\[")[0];
            playerName = playerName.replaceAll("\\s+", "");
            playerPerm = playerPerm.replace(" " + playerName + "§f[", "");
            playerPerm = playerPerm.replace(playerName + "§f[", "");
            String[] flagArray = playerPerm.split(("\\s+"));
            for (String playerFlag : flagArray) {
                if (playerFlag.contains("§2")) {
                    flagMap.put(playerFlag.substring(2), true);
                } else {
                    flagMap.put(playerFlag.substring(2), false);
                }
            }
            playerFlagMap.put(playerName, flagMap);
        }
        return playerFlagMap;
    }
}
