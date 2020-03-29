package tw.momocraft.regionplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        Collection<?> playersOnlineNew = null;
        Player[] playersOnlineOld;
        if (args.length == 2 && args[0].equalsIgnoreCase("help") && PermissionsHandler.hasPermission(sender, "playerdataplus.use")) {
            commands.add("2");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("points") && PermissionsHandler.hasPermission(sender, "playerdataplus.command.points")) {
            commands.add("limit");
            commands.add("used");
            commands.add("look");
        } else if ((args.length == 3) && (args[0].equalsIgnoreCase("points"))) {
            if (PermissionsHandler.hasPermission(sender, "playerdataplus.command.points.other")) {
                try {
                    if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
                        if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
                            playersOnlineNew = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                            for (Object objPlayer : playersOnlineNew) {
                                commands.add(((Player) objPlayer).getName());
                            }
                        }
                    } else {
                        playersOnlineOld = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                        for (Player player : playersOnlineOld) {
                            commands.add(player.getName());
                        }
                    }
                } catch (Exception e) {
                    ServerHandler.sendDebugTrace(e);
                }
            }
        } else if (args.length == 1) {
            if (PermissionsHandler.hasPermission(sender, "playerdataplus.use")) {
                commands.add("help");
            }
            if (PermissionsHandler.hasPermission(sender, "playerdataplus.command.reload")) {
                commands.add("reload");
            }
            if (PermissionsHandler.hasPermission(sender, "playerdataplus.command.version")) {
                commands.add("version");
            }
            if (PermissionsHandler.hasPermission(sender, "playerdataplus.command.flagedit")) {
                commands.add("flagedit");
            }
            if (PermissionsHandler.hasPermission(sender, "playerdataplus.command.points")) {
                commands.add("points");
            }
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}