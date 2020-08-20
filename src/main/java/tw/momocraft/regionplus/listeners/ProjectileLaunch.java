package tw.momocraft.regionplus.listeners;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;

public class ProjectileLaunch implements Listener {

    /**
     * Visitor
     *
     * @param e ProjectileLaunchEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorItemsProjectile(ProjectileLaunchEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            if (ConfigHandler.getConfigPath().isVisUseItems()) {
                if (!ConfigHandler.getConfigPath().isVisItemsProjectile()) {
                    if (e.getEntity().getShooter() instanceof Player) {
                        Player player = (Player) e.getEntity().getShooter();
                        String entityType = e.getEntity().getType().name();
                        if (RegionUtils.bypassBorder(player, player.getLocation())) {
                            ServerHandler.sendFeatureMessage("Visitor", entityType, "Use-Items.Projectile", "return", "border",
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                        // Allow-ItemJoin
                        if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                            if (!ConfigHandler.getConfigPath().isVisItemJoin()) {
                                ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                                if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                                    ServerHandler.sendFeatureMessage("Visitor", entityType, "Use-Items.Projectile", "bypass", "Allow-ItemJoin=true",
                                            new Throwable().getStackTrace()[0]);
                                    return;
                                }
                            }
                        }
                        // Cancel
                        if (ConfigHandler.getConfigPath().isVisUseItemsMsg()) {
                            Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                        }
                        ServerHandler.sendFeatureMessage("Visitor", entityType, "Use-Items.Projectile", "cancel", "Allow-Projectile=false",
                                new Throwable().getStackTrace()[0]);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
