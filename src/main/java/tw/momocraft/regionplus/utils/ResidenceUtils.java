package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class ResidenceUtils {

    public static boolean getBuildPerms(ResidencePermissions perms, String flag, boolean def, Player player) {
        if (player != null) {
            if (perms.playerHas(player, Flags.build, false)) {
                return perms.playerHas(player, Flags.getFlag(flag), def);
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
                    return perms.playerHas(player, Flags.getFlag(flag), def);
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
}
