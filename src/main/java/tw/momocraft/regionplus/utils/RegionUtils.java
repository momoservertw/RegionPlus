package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.permissions.PermissionManager;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Map;

public class RegionUtils {
    private void resetNoPermsFlags() {
        String playerName;
        ResidencePermissions perms;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            playerName = offlinePlayer.getName();
            if (playerName != null) {
                for (ClaimedResidence claimedResidence : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
                    perms = claimedResidence.getPermissions();
                    for (String flag : perms.getFlags().keySet()) {

                        for (Map.Entry<String, Boolean> resflag : Residence.getInstance().getPlayerManager().getResidencePlayer(playerName).getGroup().getDefaultResidenceFlags()) {
                            resflag.
                        }
                    }
                }
            }
        }
    }
}
