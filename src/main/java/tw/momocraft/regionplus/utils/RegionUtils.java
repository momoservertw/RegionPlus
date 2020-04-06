package tw.momocraft.regionplus.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import tw.momocraft.regionplus.handlers.PermissionsHandler;

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

    public static boolean bypassBorder(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            String worldName = world.getName();
            if (!LocationAPI.getLocation(location, "Visitor.Border")) {
                return PermissionsHandler.hasPermission(player, "regionplus.bypass.visitor.*") &&
                        PermissionsHandler.hasPermission(player, "regionplus.bypass.visitor." + worldName);
            }
        }
        return true;
    }
}
