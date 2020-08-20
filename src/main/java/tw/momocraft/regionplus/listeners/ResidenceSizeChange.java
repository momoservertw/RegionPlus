package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.ResidenceSizeChangeEvent;
import com.bekvon.bukkit.residence.protection.CuboidArea;
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
import tw.momocraft.regionplus.utils.ResidenceUtils;

public class ResidenceSizeChange implements Listener {

    /**
     * Residence-Points
     *
     * @param e ResidenceCreationEvent
     */
    @EventHandler(priority = EventPriority.HIGH)

    private void onResidenceSizeChange(ResidenceSizeChangeEvent e) {
        if (ConfigHandler.getConfigPath().isPoints()) {
            Player player = e.getPlayer();
            String playerName = player.getName();
            if (PermissionsHandler.hasPermission(player, "regionplus.bypass.points.limit")) {
                ServerHandler.sendFeatureMessage("Residence", playerName, "Points", "return", "bypass permission",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            if (!ConfigHandler.getConfigPath().isResIgnoreYExpand()) {
                CuboidArea area = e.getResidence().getMainArea();
                if (area.getWorld().getEnvironment().name().equals("NETHER") && area.getYSize() < 129) {
                    Language.sendLangMessage("Message.RegionPlus.expandXYZFailed", player);
                    return;
                } else if (area.getYSize() < 256) {
                    Language.sendLangMessage("Message.RegionPlus.expandXYZFailed", player);
                    return;
                }
            }
            long size = ResidenceUtils.getNewSize(e.getNewArea()) - ResidenceUtils.getNewSize(e.getOldArea());
            if (size < 0) {
                ServerHandler.sendFeatureMessage("Residence-Points", playerName, "ResidenceSizeChangeEvent", "return", " contract",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            long last = ResidenceUtils.getLimit(player) - ResidenceUtils.getUsed(player);
            if (size > last) {
                String[] placeHolders = Language.newString();
                placeHolders[24] = String.valueOf(last);
                placeHolders[25] = String.valueOf(size);
                Language.sendLangMessage("Message.RegionPlus.notEnoughPoints", player, placeHolders);
                ServerHandler.sendFeatureMessage("Residence-Points", playerName, "ResidenceSizeChangeEvent", "cancel", "notEnoughPoints",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    Language.sendLangMessage("Message.RegionPlus.points", player, ResidenceUtils.pointsPH(player));
                }
            }.runTaskLater(RegionPlus.getInstance(), 10);
            ServerHandler.sendFeatureMessage("Residence-Points", playerName, "ResidenceSizeChangeEvent", "return", "final",
                    new Throwable().getStackTrace()[0]);
        }
    }
}
