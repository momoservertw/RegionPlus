package tw.momocraft.regionplus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class PermissionsHandler {

    public static boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(permission) || sender.hasPermission("regionplus.*") || sender.isOp() || (sender instanceof ConsoleCommandSender);
    }

    public static boolean hasPermissionOffline(OfflinePlayer player, String permission) {
        return ConfigHandler.getDepends().getVault().getPermissions().playerHas(Bukkit.getWorlds().get(0).getName(), player, permission)
                || player.isOp() || (player instanceof ConsoleCommandSender);
    }
    /*
    public boolean giveMeADamnUser(UUID uniqueId) {
        if (ConfigHandler.getDepends().LuckPermsEnabled()) {
            giveMeADamnUser(player.getUniqueId()).getCachedData().
        }
        CachedPermissionData permissionData = user.getCachedData().getPermissionData(queryOptions);
        CachedMetaData metaData = user.getCachedData().getMetaData(queryOptions);
        Tristate checkResult = permissionData.checkPermission("some.permission.node");
        // the same as what Player#hasPermission would return
        return checkResult.asBoolean();
    }*/
}