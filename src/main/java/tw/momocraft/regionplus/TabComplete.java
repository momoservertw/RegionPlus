package tw.momocraft.regionplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        int length = args.length;
        if (length == 0) {
            if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.use")) {
                commands.add("help");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.reload")) {
                commands.add("reload");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.version")) {
                commands.add("version");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.points")) {
                commands.add("points");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.residence")) {
                commands.add("residence");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.pointslevel")) {
                commands.add("pointslevel");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.points")) {
                commands.add("points");
            }
        }
        switch (args[0]) {
            case "points":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.points.other")) {
                    if (length == 2) {
                        commands.addAll(UtilsHandler.getPlayer().getOnlinePlayerNames());
                    }
                }
                break;
            case "pointslevel":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.pointslevel")) {
                    if (length == 1) {
                        commands.add("promote");
                        commands.add("demote");
                    } else if (length == 2) {
                        commands.addAll(CorePlusAPI.getPlayerManager().getOnlinePlayerNames());
                    } else if (length == 3) {
                        commands.add("1");
                        commands.add("2");
                        commands.add("3");
                        commands.add("4");
                        commands.add("5");
                        commands.add("6");
                        commands.add("7");
                        commands.add("8");
                        commands.add("9");
                        commands.add("10");
                    }
                }
                break;
            case "residence":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.residence")) {
                    if (length == 1) {
                        commands.add("returnignorey");
                        commands.add("updateflags");
                        commands.add("updatemessages");
                    }
                }
                break;
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}