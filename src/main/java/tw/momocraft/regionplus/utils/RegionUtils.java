package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;

import java.util.*;


/*
clainmedResidence.getName() => simon04
claimedResidence.getPermissions();
claimedResidence.getOwner();
claimedResidence.getName();

for (String flag : perms.getFlags().keySet()) {
    ServerHandler.sendConsoleMessage(flag + "=" + perms.getFlags().get(flag));
}

perms.getPlayerFlags("Momocraft");
for (String key : permsPlayerFlags.keySet()) {
    ServerHandler.sendConsoleMessage(permsPlayerFlags.get(key).toString());
}

ServerHandler.sendConsoleMessage(perms.listPlayersFlags());
cola001§f[§2mobkilling §2tp §2usef]
Darren_houng§f[§2container §2hook §2shear §2mobkilling §2build §2use §2vehicledestroy §2tp §2leash §2animalkilling§f]
Momocraft§f[§4container §4ignite §2mobkilling §4shear §4build §2use §4vehicledestroy §2tp §4leash §2animalkilling§f]

 */

public class RegionUtils {

    public static void resetNoPermsFlags() {
        if (ConfigHandler.getRegionConfig().isResFlagEdit()) {
            String playerName;
            ResidencePermissions perms;
            String owner;
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

            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                playerName = offlinePlayer.getName();
                if (playerName != null) {
                    for (ClaimedResidence claimedResidence : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
                        perms = claimedResidence.getPermissions();
                        owner = claimedResidence.getOwner();
                        ownerGroup = ResidenceApi.getPlayerManager().getGroup(owner);
                        flagEntrySet = claimedResidence.getPermissions().getFlags().entrySet();
                        groupFlagEntrySet = ownerGroup.getDefaultResidenceFlags();
                        if (ConfigHandler.getRegionConfig().isResFlagDefaultRemove()) {
                            for (Map.Entry<String, Boolean> resFlagEntry : flagEntrySet) {
                                if (!groupFlagEntrySet.contains(resFlagEntry)) {
                                    removeFlagList.add(resFlagEntry.getKey());
                                }
                            }
                            for (String removeFlag : removeFlagList) {
                                perms.setFlag(removeFlag, FlagPermissions.FlagState.NEITHER);
                                ServerHandler.sendDebugMessage("&cRemove default flag: &e" + claimedResidence.getName() + "&8 - &f" + removeFlag);
                            }
                        }
                        if (ConfigHandler.getRegionConfig().isResFlagDefaultUpdate()) {
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
                        if (ConfigHandler.getRegionConfig().isResFlagPermissionRemove()) {
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
                                        ServerHandler.sendDebugMessage("&eRemove player flag: &e" + claimedResidence.getName() + "&8 - &6" + permsPlayer + "&8 - &f" + removePlayerFlag);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
