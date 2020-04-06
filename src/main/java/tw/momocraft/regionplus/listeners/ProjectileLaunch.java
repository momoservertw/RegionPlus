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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVisitorItemsProjectile(ProjectileLaunchEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorUseItems()) {
            if (!ConfigHandler.getRegionConfig().isVisitorItemsProjectile()) {
                if (e.getEntity().getShooter() instanceof Player) {
                    Player player = (Player) e.getEntity().getShooter();
                    String entityType = e.getEntity().getType().name();
                    if (RegionUtils.bypassBorder(player, player.getLocation())) {
                        ServerHandler.debugMessage("Visitor", entityType, "Use-Items.Projectile", "return", "border");
                        return;
                    }
                    // Allow-ItemJoin
                    if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                        if (!ConfigHandler.getRegionConfig().isVisitorItemJoin()) {
                            ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                            if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                                ServerHandler.debugMessage("Visitor", entityType, "Use-Items.Projectile", "bypass", "Allow-ItemJoin=true");
                                return;
                            }
                        }
                    }
                    // Cancel
                    if (ConfigHandler.getRegionConfig().isVisitorUseItemsMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                    }
                    ServerHandler.debugMessage("Visitor", entityType, "Use-Items.Projectile", "cancel", "Allow-Projectile=false");
                    e.setCancelled(true);
                }
            }
        }
    }
}
