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
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.use")) {
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgTitle(), sender);
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + RegionPlus.getInstance().getDescription().getName()
                        + " &ev" + RegionPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgHelp(), sender);
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
            } else {
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.use")) {
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgTitle(), sender);
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + RegionPlus.getInstance().getDescription().getName()
                            + " &ev" + RegionPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgHelp(), sender);
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.reload")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgReload(), sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.version")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVersion(), sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.residence.updateflags")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdResUpdateFlags(), sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.residence.updatemessages")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdResUpdateMessages(), sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.residence.returnignorey")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdResReturnIgnoreY(), sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.pointslevel")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdPointslevel(), sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.points")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdPoints(), sender);
                        if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.points.other")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdPointsOther(), sender);
                        }
                    }
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "reload":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.reload")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.reload")) {
                        ConfigHandler.generateData(true);
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case "version":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.version")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.version")) {
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + RegionPlus.getInstance().getDescription().getName()
                                + " &ev" + RegionPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        CorePlusAPI.getUpdateManager().check(ConfigHandler.getPrefix(), sender,
                                RegionPlus.getInstance().getName(), RegionPlus.getInstance().getDescription().getVersion(), true);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case "points":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.points")) {
                    if (!ConfigHandler.getConfigPath().isPoints()) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.featureDisabled", sender);
                        return true;
                    }
                    if (length == 1) {
                        if (sender instanceof ConsoleCommandSender) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.onlyPlayer", sender);
                            return true;
                        }
                        ResidencePoints.sendPointsMsg((Player) sender);
                        return true;
                    } else if (length == 2) {
                        if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.points.other")) {
                            Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                            if (player == null) {
                                String[] placeHolders = CorePlusAPI.getLangManager().newString();
                                placeHolders[1] = args[1]; // %targetplayer%
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            ResidencePoints.sendTargetPointsMsg(sender, player);
                        } else {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                        }
                        return true;
                    }
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdPoints(), sender);
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.points.other")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdPointsOther(), sender);
                    }


                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "residence":
                if (args[1].equalsIgnoreCase("returnignorey")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.residence.returnignorey")) {
                        ResidencePoints.returnIgnoreY(sender);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("updateflags")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.residence.updaterflags")) {
                        ResidenceEdit.updateFlags(sender);
                        return true;
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("updatemessages")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.residence.updatemessages")) {
                        ResidenceEdit.updateMessages(sender);
                        return true;
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case "pointslevel":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "regionplus.command.pointslevel")) {
                    if (args[1].equalsIgnoreCase("promote")) {
                        if (length == 3) {
                            String[] placeHolders = CorePlusAPI.getLangManager().newString();
                            placeHolders[1] = args[2]; // %targetplayer%
                            Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                            if (player == null) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            CorePlusAPI.getPlayerManager().changePermLevel(player.getUniqueId(), "regionplus.points.level.", 1, 0);
                            return true;
                        } else if (length == 4) {
                            String[] placeHolders = CorePlusAPI.getLangManager().newString();
                            placeHolders[1] = args[2]; // %targetplayer%
                            Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                            if (player == null) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            try {
                                CorePlusAPI.getPlayerManager().changePermLevel(player.getUniqueId(), "regionplus.points.level.", Integer.parseInt(args[3]), 0);
                            } catch (Exception ex) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdPointslevel(), sender);
                            }
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("demote")) {
                        if (length == 3) {
                            String[] placeHolders = CorePlusAPI.getLangManager().newString();
                            placeHolders[1] = args[2]; // %targetplayer%
                            Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                            if (player == null) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            CorePlusAPI.getPlayerManager().changePermLevel(player.getUniqueId(), "regionplus.points.level.", 1, 0);
                            return true;
                        } else if (length == 4) {
                            String[] placeHolders = CorePlusAPI.getLangManager().newString();
                            placeHolders[1] = args[2]; // %targetplayer%
                            Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                            if (player == null) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                                return true;
                            }
                            try {
                                CorePlusAPI.getPlayerManager().changePermLevel(player.getUniqueId(), "regionplus.points.level.", Integer.parseInt(args[3]), 0);
                            } catch (Exception ex) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdPointslevel(), sender);
                            }
                            return true;
                        }
                    }
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgCmdPointslevel(), sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            default:
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                break;
        }
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
        return true;
    }
}

