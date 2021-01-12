package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.OfflinePlayer;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.*;

public class FlagsEditor {
    public static void editFlags() {
        boolean bypassPerm = ConfigHandler.getConfigPath().isResFlagBypassPerms();
        boolean bypassCustom = ConfigHandler.getConfigPath().isResFlagBypassCustom();
        boolean envRemove = ConfigHandler.getConfigPath().isResFlagRemove();
        List<String> envRemoveIgnore = ConfigHandler.getConfigPath().getResFlagRemoveIgnore();
        boolean envUpdate = ConfigHandler.getConfigPath().isResFlagUpdate();
        List<String> envUpdateIgnore = ConfigHandler.getConfigPath().getResFlagUpdateIgnore();
        boolean playerRemove = ConfigHandler.getConfigPath().isResFlagPlayerRemove();
        List<String> playerRemoveIgnore = ConfigHandler.getConfigPath().getResFlagPlayerRemoveIgnore();

        Map<String, ClaimedResidence> resMap = Residence.getInstance().getResidenceManager().getResidences();
        String playerName;
        ClaimedResidence res;
        OfflinePlayer offlinePlayer;
        PermissionGroup group;
        ResidencePermissions resPerm;
        Set<Map.Entry<String, Boolean>> defaultFlags;
        Map<String, Boolean> resFlagMap;
        String flag;
        for (String resName : resMap.keySet()) {
            System.out.println(resName);
            res = resMap.get(resName);
            playerName = res.getOwner();
            if (CorePlusAPI.getUtilsManager().containIgnoreValue(resName, ConfigHandler.getConfigPath().getResFlagByPassRes(), false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence.Flags-Editor", playerName, "residences", "bypass",
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            if (CorePlusAPI.getUtilsManager().containIgnoreValue(playerName, ConfigHandler.getConfigPath().getResFlagByPassResOwners(), false)) {
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence.Flags-Editor", playerName, "residences", "bypass",
                        new Throwable().getStackTrace()[0]);
                continue;
            }
            offlinePlayer = CorePlusAPI.getPlayerManager().getOfflinePlayer(playerName);
            if (bypassPerm) {
                if (CorePlusAPI.getPlayerManager().hasPermissionOffline(offlinePlayer, "regionplus.bypass.flagsedit")) {
                    CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence", "Residence.Flags-Editor", playerName, "permission", "bypass",
                            new Throwable().getStackTrace()[0]);
                    break;
                }
            }
            // The default flags of that group.
            group = res.getOwnerGroup();
            defaultFlags = group.getDefaultResidenceFlags();
            // The default flags of that residence.
            resPerm = res.getPermissions();
            resFlagMap = resPerm.getFlags();
            // Environment - Remove
            if (envRemove) {
                for (String resFlag : resFlagMap.keySet()) {
                    if (group.hasFlagAccess(Flags.valueOf(resFlag)) || envRemoveIgnore.contains(resFlag)) {
                        break;
                    }
                    for (Map.Entry<String, Boolean> defaultFlag : defaultFlags) {
                        if (bypassCustom) {
                            if (FlagPermissions.FlagState.valueOf(resFlag).equals(FlagPermissions.FlagState.INVALID)) {
                                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(),
                                        "Residence.Flags-Editor", "Environment-Remove", playerName, "bypass", "Bypass custom: " + resFlag,
                                        new Throwable().getStackTrace()[0]);
                                continue;
                            }
                        }
                        if (defaultFlag.getKey().equals(resFlag)) {
                            if (defaultFlag.getValue()) {
                                resPerm.setFlag(resFlag, FlagPermissions.FlagState.TRUE);
                                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", "Environment-Remove", playerName, "bypass", resFlag + "=true",
                                        new Throwable().getStackTrace()[0]);
                            } else {
                                resPerm.setFlag(resFlag, FlagPermissions.FlagState.FALSE);
                                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", "Environment-Remove", playerName, "bypass", resFlag + "=false",
                                        new Throwable().getStackTrace()[0]);
                            }
                        } else {
                            resPerm.setFlag(resFlag, FlagPermissions.FlagState.NEITHER);
                            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", "Environment-Remove", playerName, "bypass", resFlag + "=remove",
                                    new Throwable().getStackTrace()[0]);
                        }
                    }
                }
            }
            // Environment - Update
            if (envUpdate) {
                for (Map.Entry<String, Boolean> defaultFlag : defaultFlags) {
                    flag = defaultFlag.getKey();
                    if (bypassCustom) {
                        if (FlagPermissions.FlagState.valueOf(flag).equals(FlagPermissions.FlagState.INVALID)) {
                            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(),
                                    "Residence.Flags-Editor", "Environment-Remove", playerName, "bypass", "Bypass custom: " + flag,
                                    new Throwable().getStackTrace()[0]);
                            continue;
                        }
                    }
                    if (resFlagMap.containsKey(flag) || envUpdateIgnore.contains(flag)) {
                        continue;
                    }
                    if (defaultFlag.getValue()) {
                        resPerm.setFlag(flag, FlagPermissions.FlagState.TRUE);
                        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", "Environment-Update", playerName, "bypass", flag + "=true",
                                new Throwable().getStackTrace()[0]);
                    } else {
                        resPerm.setFlag(flag, FlagPermissions.FlagState.FALSE);
                        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Residence.Flags-Editor", "Environment-Update", playerName, "bypass", flag + "=false",
                                new Throwable().getStackTrace()[0]);
                    }
                }
            }
            // Player - Remove
            if (playerRemove) {
                Map<String, Map<String, Boolean>> playerFlagMap = getPlayerFlagMap(resPerm);
                for (String key : playerFlagMap.keySet()) {
                    for (String key2 : playerFlagMap.get(key).keySet()) {
                        if (bypassCustom) {
                            if (FlagPermissions.FlagState.valueOf(key2).equals(FlagPermissions.FlagState.INVALID)) {
                                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(),
                                        "Residence.Flags-Editor", "Environment-Remove", playerName, "bypass", "Bypass custom: " + key2,
                                        new Throwable().getStackTrace()[0]);
                                continue;
                            }
                        }
                        if (group.hasFlagAccess(Flags.valueOf(key2)) || playerRemoveIgnore.contains(key2)) {
                            break;
                        }
                        resPerm.setPlayerFlag(key, key2, FlagPermissions.FlagState.NEITHER);
                    }
                }
            }
        }
    }

    private static Map<String, Map<String, Boolean>> getPlayerFlagMap(ResidencePermissions resPerm) {
        Map<String, Map<String, Boolean>> playerFlagMap = new HashMap<>();
        String playerName;
        String[] playerFlagSplit = resPerm.listPlayersFlags().split("§f]");
        Map<String, Boolean> flagMap;
        for (String playerPerm : playerFlagSplit) {
            flagMap = new HashMap<>();
            playerName = playerPerm.split("§f\\[")[0];
            playerPerm = playerPerm.replace(playerName + "§f[", "");
            playerPerm = playerPerm.replace("§f] ", "");
            playerPerm = playerPerm.replace("§f]", "");
            String[] flagArray = playerPerm.split(("\\s+"));
            for (String playerFlag : flagArray) {
                if (playerFlag.contains("§a")) {
                    flagMap.put(playerFlag.substring(1), true);
                } else {
                    flagMap.put(playerFlag.substring(1), false);
                }
            }
            playerFlagMap.put(playerName, flagMap);
        }
        return playerFlagMap;
    }
}
