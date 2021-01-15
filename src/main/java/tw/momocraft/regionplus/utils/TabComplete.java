package tw.momocraft.regionplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        Player[] playersOnlineOld;
        Collection<?> playersOnlineNew;
        switch (args.length) {
            case 1:
                if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.use")) {
                    commands.add("help");
                }
                if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.reload")) {
                    commands.add("reload");
                }
                if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.version")) {
                    commands.add("version");
                }
                if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.residence.reset") ||
                        CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.returnignorey")) {
                    commands.add("residence");
                }
                if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.points")) {
                    commands.add("points");
                }
            case 2:
                if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.residence.reset")) {
                    commands.add("resetall");
                    commands.add("resetflags");
                    commands.add("resetmessages");
                } else if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.residence.returnignorey")) {
                    commands.add("returnignorey");
                } else if ((args[0].equalsIgnoreCase("points"))) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.points.other")) {
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
                            CorePlusAPI.getLangManager().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                        }
                    }
                }
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}