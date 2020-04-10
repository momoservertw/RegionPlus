package tw.momocraft.regionplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.handlers.PlayerHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        if (args.length == 0) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.use")) {
                Language.dispatchMessage(sender, "");
                Language.sendLangMessage("Message.RegionPlus.Commands.title", sender, false);
                Language.sendLangMessage("Message.RegionPlus.Commands.help", sender, false);
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            Language.dispatchMessage(sender, "");
            if (PermissionsHandler.hasPermission(sender, "regionplus.use")) {
                Language.sendLangMessage("Message.RegionPlus.Commands.title", sender, false);
                Language.sendLangMessage("Message.RegionPlus.Commands.help", sender, false);
                if (PermissionsHandler.hasPermission(sender, "regionplus.command.version")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.version", sender, false);
                }
                if (PermissionsHandler.hasPermission(sender, "regionplus.command.reload")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.reload", sender, false);
                }
                if (PermissionsHandler.hasPermission(sender, "regionplus.command.flagsedit")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.flagsedit", sender, false);
                }
                if (PermissionsHandler.hasPermission(sender, "regionplus.command.points")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.pointsLook", sender, false);
                    Language.sendLangMessage("Message.RegionPlus.Commands.pointsUsed", sender, false);
                    Language.sendLangMessage("Message.RegionPlus.Commands.pointsLimit", sender, false);
                    if (PermissionsHandler.hasPermission(sender, "regionplus.command.points.other")) {
                        Language.sendLangMessage("Message.RegionPlus.Commands.targetPointsLook", sender, false);
                        Language.sendLangMessage("Message.RegionPlus.Commands.targetPointsUsed", sender, false);
                        Language.sendLangMessage("Message.RegionPlus.Commands.targetPointsLimit", sender, false);
                    }
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.reload")) {
                ConfigHandler.generateData(true);
                Language.sendLangMessage("Message.configReload", sender);
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("version")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.version")) {
                Language.dispatchMessage(sender, "&d&lRegionPlus &ev" + RegionPlus.getInstance().getDescription().getVersion() + "&8 - &fby Momocraft");
                ConfigHandler.getUpdater().checkUpdates(sender);
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("flagsedit")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.flagsedit")) {
                if (ConfigHandler.getRegionConfig().isRFEnable()) {
                    if (ConfigHandler.getEditor().isRun()) {
                        ServerHandler.sendConsoleMessage("&cThe process of Flags-Edit is still running! &8(Stop process: /rp flagsedit stop)");
                        return true;
                    }
                    ServerHandler.sendConsoleMessage("&6Starting to check residence flags...");
                    ResidenceUtils.editFlags();
                    return true;
                }
                ServerHandler.sendConsoleMessage("&cYou don't enable the residence Flags-Edit feature in config.yml.");
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("flagsedit") && args[1].equalsIgnoreCase("stop")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.flagsedit")) {
                if (!ConfigHandler.getEditor().isRun()) {
                    ServerHandler.sendConsoleMessage("&cThe process of Flags-Edit isn't running now.");
                    return true;
                }
                ServerHandler.sendConsoleMessage("&6Stops the Flags-Edit process after finished this editing.");
                ConfigHandler.getEditor().setRun(false);
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("messageedit")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.messageedit")) {
                if (ConfigHandler.getRegionConfig().isRMEnable()) {
                    ServerHandler.sendConsoleMessage("&6Starting to check residence message...");
                    ResidenceUtils.editMessage();
                    return true;
                }
                ServerHandler.sendConsoleMessage("&cYou don't enable the residence Message-Edit feature in config.yml.");
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("points")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points")) {
                if (ConfigHandler.getRegionConfig().isPointsEnable()) {
                    Language.sendLangMessage("Message.RegionPlus.points", sender, ResidenceUtils.pointsPH((Player) sender));
                } else {
                    Language.sendLangMessage("Message.featureNotEnable", sender);
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("points")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points.other")) {
                if (ConfigHandler.getRegionConfig().isPointsEnable()) {
                    Player player = PlayerHandler.getPlayerString(args[1]);
                    if (player == null) {
                        String[] placeHolders = Language.newString();
                        placeHolders[2] = args[1];
                        Language.sendLangMessage("Message.targetNotOnline", sender, placeHolders);
                        return true;
                    }
                    Language.sendLangMessage("Message.RegionPlus.targetPoints", sender, ResidenceUtils.targetPointsPH(sender, player));
                } else {
                    Language.sendLangMessage("Message.featureNotEnable", sender);
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else {
            Language.sendLangMessage("Message.unknownCommand", sender);
            return true;
        }
        return true;
    }
}

