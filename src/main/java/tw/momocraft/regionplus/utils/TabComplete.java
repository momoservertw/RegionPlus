package tw.momocraft.regionplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.UtilsHandler;
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
        Collection<?> playersOnlineNew;
        Player[] playersOnlineOld;
        int length = args.length;
        if (length == 0) {
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.use")) {
                commands.add("help");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.reload")) {
                commands.add("reload");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.version")) {
                commands.add("version");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "serverplus.command.points")) {
                commands.add("points");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "serverplus.command.residence")) {
                commands.add("residence");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "serverplus.command.bankreturn")) {
                commands.add("bankreturn");
            }
        }
        switch (args[0]) {
            case "test":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.test")) {
                    if (length == 1) {
                        commands.add("location");
                        commands.add("blocks");
                    } else if (length == 2) {
                        if (args[1].equalsIgnoreCase("location")) {
                            commands.addAll(tw.momocraft.coreplus.handlers.ConfigHandler.getConfigPath().getLocProp().keySet());
                        } else if (args[1].equalsIgnoreCase("blocks")) {
                            commands.addAll(tw.momocraft.coreplus.handlers.ConfigHandler.getConfigPath().getBlocksProp().keySet());
                        }
                    }
                }
                break;
            case "configbuilder":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.configbuilder")) {
                    if (length == 1) {
                        commands.addAll(tw.momocraft.coreplus.handlers.ConfigHandler.getConfigPath().getConfigBuilderProp().keySet());
                    } else if (length == 2) {
                        if (args[1].equalsIgnoreCase("location")) {
                            commands.addAll(tw.momocraft.coreplus.handlers.ConfigHandler.getConfigPath().getLocProp().keySet());
                        } else if (args[1].equalsIgnoreCase("blocks")) {
                            commands.addAll(tw.momocraft.coreplus.handlers.ConfigHandler.getConfigPath().getBlocksProp().keySet());
                        }
                    }
                }
                break;
            case "cmdcustom":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.cmdcustom")) {
                    if (length == 1) {
                        commands.addAll(tw.momocraft.coreplus.handlers.ConfigHandler.getConfigPath().getCmdProp().keySet());
                    }
                }
                break;
            case "cmd":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.cmd")) {
                    try {
                        if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
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
                        UtilsHandler.getLang().sendDebugTrace(tw.momocraft.coreplus.handlers.ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
                    }
                }
                break;
            case "cmdplayer":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.cmdplayer")) {
                    try {
                        if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
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
                        UtilsHandler.getLang().sendDebugTrace(tw.momocraft.coreplus.handlers.ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
                    }
                }
                break;
            case "cmdonline":
                if (UtilsHandler.getPlayer().hasPerm(sender, "regionplus.command.cmdonline")) {
                    try {
                        if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
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
                        UtilsHandler.getLang().sendDebugTrace(tw.momocraft.coreplus.handlers.ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
                    }
                }
                break;
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}