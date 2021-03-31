package tw.momocraft.regionplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.listeners.ResidencePoints;
import tw.momocraft.regionplus.utils.ResidenceEdit;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        int length = args.length;
        if (length == 0) {
            if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.use")) {
                CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "");
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgTitle(), sender);
                CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                        "&f " + RegionPlus.getInstance().getDescription().getName()
                                + " &ev" + RegionPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgHelp(), sender);
                CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "");
            } else {
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                        "Message.noPermission", sender);
            }
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help":
                if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.use")) {
                    CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            ConfigHandler.getConfigPath().getMsgTitle(), sender);
                    CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                            "&f " + RegionPlus.getInstance().getDescription().getName()
                                    + " &ev" + RegionPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            ConfigHandler.getConfigPath().getMsgHelp(), sender);
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.reload"))
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                ConfigHandler.getConfigPath().getMsgReload(), sender);
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.version"))
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                ConfigHandler.getConfigPath().getMsgVersion(), sender);
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.residence.updateflags"))
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                ConfigHandler.getConfigPath().getMsgCmdResUpdateFlags(), sender);
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.residence.updatemessages"))
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                ConfigHandler.getConfigPath().getMsgCmdResUpdateMessages(), sender);
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.residence.returnignorey"))
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                ConfigHandler.getConfigPath().getMsgCmdResReturnIgnoreY(), sender);
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.pointslevel"))
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                ConfigHandler.getConfigPath().getMsgCmdPointsLevel(), sender);
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.points")) {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                ConfigHandler.getConfigPath().getMsgCmdPoints(), sender);
                        if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.points.other"))
                            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                    ConfigHandler.getConfigPath().getMsgCmdPointsOther(), sender);
                    }
                    CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "");
                } else {
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "reload":
                if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.reload")) {
                    ConfigHandler.generateData(true);
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            "Message.configReload", sender);
                } else {
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "version":
                if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.version")) {
                    CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                            "&f " + RegionPlus.getInstance().getDescription().getName()
                                    + " &ev" + RegionPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getUpdate().check(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(), sender,
                            RegionPlus.getInstance().getName(), RegionPlus.getInstance().getDescription().getVersion(), true);
                } else {
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "points":
                if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.points")) {
                    if (!ConfigHandler.getConfigPath().isPoints()) {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                "Message.featureDisabled", sender);
                        return true;
                    }
                    if (length == 1) {
                        if (sender instanceof ConsoleCommandSender) {
                            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                    "Message.onlyPlayer", sender);
                            return true;
                        }
                        ResidencePoints.sendPointsMsg((Player) sender);
                        return true;
                    } else if (length == 2) {
                        if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.points.other")) {
                            Player player = CorePlusAPI.getPlayer().getPlayerString(args[1]);
                            if (player == null) {
                                String[] placeHolders = CorePlusAPI.getMsg().newString();
                                placeHolders[1] = args[1]; // %targetplayer%
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                        "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            ResidencePoints.sendTargetPointsMsg(sender, player);
                        } else {
                            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                    "Message.noPermission", sender);
                        }
                        return true;
                    }
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            ConfigHandler.getConfigPath().getMsgCmdPoints(), sender);
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.points.other"))
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                ConfigHandler.getConfigPath().getMsgCmdPointsOther(), sender);
                } else {
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "residence":
                if (args[1].equalsIgnoreCase("returnignorey")) {
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.residence.returnignorey")) {
                        ResidencePoints.returnIgnoreY(sender);
                    } else {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("updateflags")) {
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.residence.updaterflags")) {
                        ResidenceEdit.updateFlags(sender);
                    } else {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("updatemessages")) {
                    if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.residence.updatemessages")) {
                        ResidenceEdit.updateMessages(sender);
                    } else {
                        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case "pointslevel":
                if (CorePlusAPI.getPlayer().hasPerm(sender, "regionplus.command.pointslevel")) {
                    if (args[1].equalsIgnoreCase("promote")) {
                        // rgp pointslevel promote <player>
                        if (length == 3) {
                            Player player = CorePlusAPI.getPlayer().getPlayerString(args[2]);
                            if (player == null) {
                                String[] placeHolders = CorePlusAPI.getMsg().newString();
                                placeHolders[1] = args[2]; // %targetplayer%
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                        "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            CorePlusAPI.getPlayer().changePermLevel(player.getUniqueId(),
                                    "regionplus.points.level.", 1, 0);
                            return true;
                            // rgp pointslevel promote <player> <level>
                        } else if (length == 4) {
                            Player player = CorePlusAPI.getPlayer().getPlayerString(args[2]);
                            if (player == null) {
                                String[] placeHolders = CorePlusAPI.getMsg().newString();
                                placeHolders[1] = args[2]; // %targetplayer%
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                        "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            try {
                                CorePlusAPI.getPlayer().changePermLevel(player.getUniqueId(),
                                        "regionplus.points.level.", Integer.parseInt(args[3]), 0);
                            } catch (Exception ex) {
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                        ConfigHandler.getConfigPath().getMsgCmdPointsLevel(), sender);
                            }
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("demote")) {
                        if (length == 3) {
                            Player player = CorePlusAPI.getPlayer().getPlayerString(args[2]);
                            if (player == null) {
                                String[] placeHolders = CorePlusAPI.getMsg().newString();
                                placeHolders[1] = args[2]; // %targetplayer%
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                        "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            CorePlusAPI.getPlayer().changePermLevel(player.getUniqueId(),
                                    "regionplus.points.level.", 1, 0);
                            return true;
                        } else if (length == 4) {
                            Player player = CorePlusAPI.getPlayer().getPlayerString(args[2]);
                            if (player == null) {
                                String[] placeHolders = CorePlusAPI.getMsg().newString();
                                placeHolders[1] = args[2]; // %targetplayer%
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                        "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            try {
                                CorePlusAPI.getPlayer().changePermLevel(player.getUniqueId(),
                                        "regionplus.points.level.", Integer.parseInt(args[3]), 0);
                            } catch (Exception ex) {
                                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                                        ConfigHandler.getConfigPath().getMsgCmdPointsLevel(), sender);
                            }
                            return true;
                        }
                    }
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            ConfigHandler.getConfigPath().getMsgCmdPointsLevel(), sender);
                } else {
                    CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
        }
        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPlugin(), ConfigHandler.getPrefix(),
                "Message.unknownCommand", sender);
        return true;
    }
}

