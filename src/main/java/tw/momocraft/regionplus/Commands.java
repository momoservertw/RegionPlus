package tw.momocraft.regionplus;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.ResidenceUtils;

import java.util.UUID;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        if (args.length == 0) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.use")) {
                Language.dispatchMessage(sender, "");
                Language.sendLangMessage("Message.RegionPlus.Commands.title", sender, false);
                Language.sendLangMessage("Message.RegionPlus.Commands.help", sender, false);
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            Language.dispatchMessage(sender, "");
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.use")) {
                Language.sendLangMessage("Message.RegionPlus.Commands.title", sender, false);
                Language.sendLangMessage("Message.RegionPlus.Commands.help", sender, false);
                if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.version")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.version", sender, false);
                }
                if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.reload")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.reload", sender, false);
                }
                if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.flagsedit")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.flagsedit", sender, false);
                }
                if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.points")) {
                    Language.sendLangMessage("Message.RegionPlus.Commands.points", sender, false);
                    if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.points.other")) {
                        Language.sendLangMessage("Message.RegionPlus.Commands.targetPoints", sender, false);
                    }
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.reload")) {
                ConfigHandler.generateData(true);
                Language.sendLangMessage("Message.configReload", sender);
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("version")) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.version")) {
                Language.dispatchMessage(sender, "&d&lRegionPlus &ev" + RegionPlus.getInstance().getDescription().getVersion() + "&8 - &fby Momocraft");
                ConfigHandler.getUpdater().checkUpdates(sender);
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("flagsedit")) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.flagsedit")) {
                if (ConfigHandler.getConfigPath().isResFlag()) {
                    if (ConfigHandler.getEditor().isRun()) {
                        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&cThe process of Flags-Edit is still running! &8(Stop process: /rp flagsedit stop)");
                        return true;
                    }
                    CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&6Starting to check residence flags...");
                    ResidenceUtils.editFlags();
                    return true;
                }
                CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&cYou don't enable the residence Flags-Edit feature in config.yml.");
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("flagsedit") && args[1].equalsIgnoreCase("stop")) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.flagsedit")) {
                if (!ConfigHandler.getEditor().isRun()) {
                    CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&cThe process of Flags-Edit isn't running now.");
                    return true;
                }
                CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&6Stops the Flags-Edit process after finished this editing.");
                ConfigHandler.getEditor().setRun(false);
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("messageedit")) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.messageedit")) {
                if (ConfigHandler.getConfigPath().isResMsg()) {
                    CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&6Starting to check residence message...");
                    ResidenceUtils.editMessage();
                    return true;
                }
                CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&cYou don't enable the residence Message-Edit feature in config.yml.");
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("points")) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.points")) {
                if (ConfigHandler.getConfigPath().isPoints()) {
                    Language.sendLangMessage("Message.RegionPlus.points", sender, ResidenceUtils.pointsValues((Player) sender));
                } else {
                    Language.sendLangMessage("Message.featureNotEnable", sender);
                }
            } else {
                Language.sendLangMessage("Message.noPermission", sender);
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("points")) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.points.other")) {
                if (ConfigHandler.getConfigPath().isPoints()) {
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
        } else if (args.length == 2 && args[0].equalsIgnoreCase("return") && args[1].equalsIgnoreCase("ignoreyresidence")) {
            if (CorePlusAPI.getPlayerManager().hasPermission(sender, "regionplus.command.return")) {
                ClaimedResidence res;
                CuboidArea area;
                int price;
                UUID playerUUID;
                for (String resName : Residence.getInstance().getResidenceManager().getResidenceList()) {
                    res = Residence.getInstance().getResidenceManager().getByName(resName);
                    area = res.getMainArea();
                    if (area.getWorld().getEnvironment().name().equals("NETHER") && area.getYSize() >= 129) {
                        continue;
                    } else if (area.getYSize() >= 256) {
                        continue;
                    }
                    price = res.getSellPrice();
                    playerUUID = res.getOwnerUUID();
                    ConfigHandler.getDepends().getVault().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(playerUUID), price);
                    CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "Return residence value: " + resName + ", " + price + ", " + playerUUID);
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

