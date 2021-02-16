package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceSizeChangeEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.RegionUtils;

import java.util.UUID;

public class VisitorResidence implements Listener {

    // Residence: Get
    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceOwnerChangeEvent(ResidenceOwnerChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isVisResGet()) {
            return;
        }
        String newOwnerName = e.getNewOwner();
        UUID newOwnerUUID = e.getNewOwnerUuid();
        Player newOwner = Bukkit.getPlayer(newOwnerUUID);
        ClaimedResidence res = e.getResidence();
        String resName = res.getName();
        UUID ownerUUID = res.getOwnerUUID();
        String ownerName = res.getOwner();
        Player owner = Bukkit.getPlayer(res.getOwner());
        if (newOwner == null) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[1] = newOwnerName; // %targetplayer%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", owner, placeHolders);
            e.setCancelled(true);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Visitor", newOwnerName, "Residence: Get", "cancel", "newOwner=null, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Location
        if (!CorePlusAPI.getConditionManager().checkLocation(newOwner.getLocation(), ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Visitor", newOwnerName, "Residence: Get", "return", "Location, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), newOwner, "regionplus.bypass.visitor")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Visitor", newOwnerName, "Residence: Get", "bypass", "Permission, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Cancel
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResGet(), owner);
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResGetTarget(), newOwner);

        //  Returning the money of trade.
        if (res.isForSell()) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[1] = newOwnerName; // %targetplayer%
            double price = res.getSellPrice();
            CorePlusAPI.getPlayerManager().takeTypeMoney(newOwnerUUID, "money", price);
            CorePlusAPI.getPlayerManager().giveTypeMoney(ownerUUID, "money", price);
            placeHolders[9] = "money"; // %pricetype%
            placeHolders[10] = String.valueOf(price); // %price%
            CorePlusAPI.getPlayerManager().giveTypeMoney(ownerUUID, "money", price);
            CorePlusAPI.getLangManager().sendLangMsg("", ConfigHandler.getConfigPath().getMsgResReturnMoney(), owner, placeHolders);
        }
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Visitor", newOwnerName, "Residence: Get", "cancel", "Final, " + resName,
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Residence: Create
    @EventHandler(priority = EventPriority.HIGH)
    public void onVisitorResCreate(ResidenceCreationEvent e) {
        if (!ConfigHandler.getConfigPath().isVisResCreate()) {
            return;
        }
        Player player = e.getPlayer();
        String playerName = player.getName();
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getConditionManager().checkLocation(loc, ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Create", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayerManager().hasPerm(ConfigHandler.getPluginName(), player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Create", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Cancel
        String ownerName = e.getResidence().getOwner();
        Player owner = Bukkit.getPlayer(ownerName);
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgVisResCreate(), owner);
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Visitor", playerName, "Residence: Create", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }
}
