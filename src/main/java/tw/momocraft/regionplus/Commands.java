package tw.momocraft.regionplus;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.sun.javafx.collections.MappingChange;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.ResidenceReset;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        switch (args.length) {
            case 0:
                if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.use")) {
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
            case 1:
                if (args[0].equalsIgnoreCase("help")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.use")) {
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgTitle(), sender);
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + RegionPlus.getInstance().getDescription().getName()
                                + " &ev" + RegionPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgHelp(), sender);
                        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.reload")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgReload(), sender);
                        }
                        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.version")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVersion(), sender);
                        }
                        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.flagsedit")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgFlagseditCmd(), sender);
                        }
                        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.points.other")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPointsCmd(), sender);
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPointsOtherCmd(), sender);
                        } else if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.points")) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgPointsCmd(), sender);
                        }
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.reload")) {
                        ConfigHandler.generateData(true);
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("version")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.version")) {
                        CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + RegionPlus.getInstance().getDescription().getName()
                                + " &ev" + RegionPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        CorePlusAPI.getUpdateManager().check(ConfigHandler.getPrefix(), sender,
                                RegionPlus.getInstance().getName(), RegionPlus.getInstance().getDescription().getVersion(), true);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("points")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.points")) {
                        if (!ConfigHandler.getConfigPath().isPoints()) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.featureDisabled", sender);
                            return true;
                        }
                        if (sender instanceof ConsoleCommandSender) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.onlyPlayer", sender);
                            return true;
                        }
                        RegionUtils.sendPointsMsg((Player) sender);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("residence") && args[1].equalsIgnoreCase("returnignorey")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.residence.returnignorey")) {
                        RegionUtils.returnIgnoreY(sender);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("residence") && args[1].equalsIgnoreCase("resetall")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.residence.resetall")) {
                        if (!ConfigHandler.getConfigPath().isResResetAll()) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.featureDisabled", sender);
                            return true;
                        }
                        ResidenceReset.resetAll(sender);
                        return true;
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("residence") && args[1].equalsIgnoreCase("resetflags")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.residence.resetflags")) {
                        if (!ConfigHandler.getConfigPath().isResResetFlags()) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.featureDisabled", sender);
                            return true;
                        }
                        ResidenceReset.resetFlags(sender);
                        return true;
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("residence") && args[1].equalsIgnoreCase("resetmessages")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.residence.resetmessages")) {
                        if (!ConfigHandler.getConfigPath().isResResetMsg()) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.featureDisabled", sender);
                            return true;
                        }
                        ResidenceReset.resetMessage(sender);
                        return true;
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("points")) {
                    if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), sender, "regionplus.command.points.other")) {
                        if (!ConfigHandler.getConfigPath().isPoints()) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.featureDisabled", sender);
                            return true;
                        }
                        String[] placeHolders = CorePlusAPI.getLangManager().newString();
                        placeHolders[1] = args[1]; // %targetplayer%
                        Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                        if (player == null) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                            return true;
                        }
                        RegionUtils.sendTargetPointsMsg(sender, player);
                    } else {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
        }
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
        return true;
    }
}

