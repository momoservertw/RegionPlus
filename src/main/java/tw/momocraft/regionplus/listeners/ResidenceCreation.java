package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class ResidenceCreation implements Listener {

    /**
     * Residence-Points
     *
     * @param e ResidenceCreationEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    private void onResPointsEnable(ResidenceCreationEvent e) {
        if (ConfigHandler.getConfigPath().isPointsEnable()) {
            Player player = e.getPlayer();
            String playerName = player.getName();
            if (PermissionsHandler.hasPermission(player, "regionplus.bypass.points.limit")) {
                ServerHandler.debugMessage("Residence", playerName, "Points", "return", "bypass permission");
                return;
            }
            long size = ResidenceUtils.getNewSize(e.getPhysicalArea());
            long last = ResidenceUtils.getLimit(player) - ResidenceUtils.getUsed(player);
            if (size > last) {
                String[] placeHolders = Language.newString();
                placeHolders[24] = String.valueOf(last);
                placeHolders[25] = String.valueOf(size);
                Language.sendLangMessage("Message.RegionPlus.notEnoughPoints", player, placeHolders);
                ServerHandler.debugMessage("Residence-Points", playerName, "ResidenceCreationEvent", "cancel", "notEnoughPoints");
                e.setCancelled(true);
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    Language.sendLangMessage("Message.RegionPlus.points", player, ResidenceUtils.pointsPH(player));
                }
            }.runTaskLater(RegionPlus.getInstance(), 10);
            ServerHandler.debugMessage("Residence-Points", playerName, "ResidenceCreationEvent", "return", "final");
        }
    }

    /**
     * Visitor
     *
     * @param e ResidenceCreationEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    private void onVisitorCreateRes(ResidenceCreationEvent e) {
        if (ConfigHandler.getConfigPath().isVEnable()) {
            if (ConfigHandler.getConfigPath().isVCreateRes()) {
                Player player = e.getPlayer();
                String playerName = player.getName();
                if (!RegionUtils.bypassBorder(player, player.getLocation())) {
                    // Cancel
                    if (ConfigHandler.getConfigPath().isVCreateResMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorCreateResidence", player);
                    }
                    ServerHandler.debugMessage("Visitor", playerName, "Create-Residence", "cancel", "border");
                    e.setCancelled(true);
                }
            }
        }
    }
}
