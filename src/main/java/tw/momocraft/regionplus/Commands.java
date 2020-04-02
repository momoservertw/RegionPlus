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
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.ResidencePoints;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        RegionUtils.resetNoPermsFlags();
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
                if (PermissionsHandler.hasPermission(sender, "regionplus.command.flagedit")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.flagedit", sender, false);
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
        } else if (args.length == 1 && args[0].equalsIgnoreCase("flagedit")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.flagedit")) {
                ServerHandler.sendConsoleMessage("&6Starting to check residence flags...");
                RegionUtils.resetNoPermsFlags();
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("points")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points")) {
                if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.pointsLook", sender, false);
                    Language.sendLangMessage("Message.RegionPlus.Commands.pointsUsed", sender, false);
                    Language.sendLangMessage("Message.RegionPlus.Commands.pointsLimit", sender, false);
                    if (PermissionsHandler.hasPermission(sender, "regionplus.command.points.other")) {
                        Language.sendLangMessage("Message.RegionPlus.Commands.targetPointsLook", sender, false);
                        Language.sendLangMessage("Message.RegionPlus.Commands.targetPointsUsed", sender, false);
                        Language.sendLangMessage("Message.RegionPlus.Commands.targetPointsLimit", sender, false);
                    }
                } else {
                    Language.dispatchMessage(sender, "&cYou don't enable the residence points feature in config.yml.");
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
            // points limit
        } else if (args.length == 2 && args[0].equalsIgnoreCase("points") && args[1].equalsIgnoreCase("limit")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points")) {
                if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
                    Player player = (Player) sender;
                    long pointsLimit = ResidencePoints.getPointsLimit(player);
                    String[] placeHolders = Language.newString();
                    placeHolders[8] = String.valueOf(pointsLimit);
                    Language.sendLangMessage("Message.RegionPlus.pointsLimit", sender, placeHolders);
                } else {
                    Language.dispatchMessage(sender, "&cYou don't enable the residence points feature in config.yml.");
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("points") && args[1].equalsIgnoreCase("limit")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points.other")) {
                if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
                    Player player = PlayerHandler.getPlayerString(args[2]);
                    if (player == null) {
                        String[] placeHolders = Language.newString();
                        placeHolders[2] = args[2];
                        Language.sendLangMessage("Message.targetNotFound", sender, placeHolders);
                        return true;
                    }
                    long pointsLimit = ResidencePoints.getPointsLimit(player);
                    String[] placeHolders = Language.newString();
                    placeHolders[2] = args[2];
                    placeHolders[8] = String.valueOf(pointsLimit);
                    Language.sendLangMessage("Message.RegionPlus.targetPointsLimit", sender, placeHolders);
                } else {
                    Language.dispatchMessage(sender, "&cYou don't enable the residence points feature in config.yml.");
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("points") && args[1].equalsIgnoreCase("used")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points")) {
                if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
                    Player player = (Player) sender;
                    long pointsUsed = ResidencePoints.getPointsUsed(player);
                    String[] placeHolders = Language.newString();
                    placeHolders[9] = String.valueOf(pointsUsed);
                    Language.sendLangMessage("Message.RegionPlus.pointsUsed", sender, placeHolders);
                } else {
                    Language.dispatchMessage(sender, "&cYou don't enable the residence points feature in config.yml.");
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("points") && args[1].equalsIgnoreCase("used")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points.other")) {
                if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
                    Player player = PlayerHandler.getPlayerString(args[2]);
                    if (player == null) {
                        String[] placeHolders = Language.newString();
                        placeHolders[2] = args[2];
                        Language.sendLangMessage("Message.targetNotFound", sender, placeHolders);
                        return true;
                    }
                    long pointsUsed = ResidencePoints.getPointsUsed(player);
                    String[] placeHolders = Language.newString();
                    placeHolders[2] = args[2];
                    placeHolders[9] = String.valueOf(pointsUsed);
                    Language.sendLangMessage("Message.RegionPlus.targetPointsUsed", sender, placeHolders);
                } else {
                    Language.dispatchMessage(sender, "&cYou don't enable the residence points feature in config.yml.");
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("points") && args[1].equalsIgnoreCase("look")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points")) {
                if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
                    Player player = (Player) sender;
                    long pointsRemainder = ResidencePoints.getPointsRemainder(player);
                    String[] placeHolders = Language.newString();
                    placeHolders[10] = String.valueOf(pointsRemainder);
                    Language.sendLangMessage("Message.RegionPlus.pointsRemainder", sender, placeHolders);
                } else {
                    Language.dispatchMessage(sender, "&cYou don't enable the residence points feature in config.yml.");
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("points") && args[1].equalsIgnoreCase("look")) {
            if (PermissionsHandler.hasPermission(sender, "regionplus.command.points.other")) {
                if (ConfigHandler.getRegionConfig().isResPointsEnable()) {
                    Player player = PlayerHandler.getPlayerString(args[2]);
                    if (player == null) {
                        String[] placeHolders = Language.newString();
                        placeHolders[2] = args[2];
                        Language.sendLangMessage("Message.targetNotFound", sender, placeHolders);
                        return true;
                    }
                    long pointsRemainder = ResidencePoints.getPointsRemainder(player);
                    String[] placeHolders = Language.newString();
                    placeHolders[1] = args[2];
                    placeHolders[10] = String.valueOf(pointsRemainder);
                    Language.sendLangMessage("Message.RegionPlus.targetPointsRemainder", sender, placeHolders);
                } else {
                    Language.dispatchMessage(sender, "&cYou don't enable the residence points feature in config.yml.");
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

