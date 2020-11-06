package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.ResidenceUtils;
import tw.momocraft.regionplus.utils.VisitorMap;

import java.util.Map;

public class PlayerInteract implements Listener {

    /**
     * Residence-Points
     *
     * @param e PlayerInteractEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onResPointsSelectInfo(PlayerInteractEvent e) {
        if (ConfigHandler.getConfigPath().isPointsSelectInfo()) {
            if (e.getMaterial().equals(ConfigHandler.getConfigPath().getPointsSelectTool())) {
                Player player = e.getPlayer();
                String playerName = player.getName();
                SelectionManager smanager = Residence.getInstance().getSelectionManager();
                Location loc1 = smanager.getSelection(player).getBaseLoc1();
                Location loc2 = smanager.getSelection(player).getBaseLoc2();
                if (loc1 == null || loc2 == null) {
                    return;
                }
                World world1 = loc1.getWorld();
                World world2 = loc2.getWorld();
                if (world1 == null || world2 == null) {
                    return;
                }
                if (world1.equals(loc2.getWorld())) {
                    long size;
                    if (ConfigHandler.getConfigPath().isPointsMode()) {
                        size = (Math.abs(loc1.getBlockX() - loc2.getBlockX()) + 1) * (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) + 1);
                    } else {
                        size = (Math.abs(loc1.getBlockX() - loc2.getBlockX()) + 1) * (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) + 1)
                                * (Math.abs(loc1.getBlockY() - loc2.getBlockY()) + 1);
                    }
                    Language.sendLangMessage("Message.RegionPlus.pointsSelect", player, ResidenceUtils.selectPointsPH(player, size));
                    ServerHandler.sendFeatureMessage("Residence", playerName, "Select-Tool", "return", "show",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
                ServerHandler.sendFeatureMessage("Residence", playerName, "Select-Tool", "return", "not match",
                        new Throwable().getStackTrace()[0]);
            }
        }
    }

    /**
     * Visitor
     *
     * @param e PlayerInteractEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (ConfigHandler.getConfigPath().isVisitor()) {
            Player player = e.getPlayer();
            String worldName = player.getWorld().getName();
            String blockType = e.getMaterial().name();
            // To get properties.
            Map<String, VisitorMap> visitorProp = ConfigHandler.getConfigPath().getVisitorProp().get(worldName);
            VisitorMap visitorMap;
            if (visitorProp != null) {
                Location loc;
                // Checking every groups.
                for (String groupName : visitorProp.keySet()) {
                    visitorMap = visitorProp.get(groupName);
                    // Location
                    if (!visitorMap.isInterBlock()) {
                        return;
                    }
                    loc = player.getLocation();
                    if (RegionUtils.bypassBorder(player, loc, visitorMap.getLocMaps())) {
                        ServerHandler.sendFeatureMessage("Visitor", blockType, "Use-Items: Location", "return", groupName,
                                new Throwable().getStackTrace()[0]);
                        continue;
                    }
                    if (e.getAction().equals(Action.PHYSICAL)) {
                        switch (blockType) {
                            case "TRIPWIRE":
                            case "ACACIA_PRESSURE_PLATE":
                            case "BIRCH_PRESSURE_PLATE":
                                // Cancel
                                if (visitorMap.isInterEntMsg()) {
                                    Language.sendLangMessage("Message.RegionPlus.visitorInteractBlocks", player);
                                }
                                ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks: Physical", "cancel", groupName,
                                        new Throwable().getStackTrace()[0]);
                                e.setCancelled(true);
                                return;
                            default:
                                ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks: Physical", "return", groupName,
                                        new Throwable().getStackTrace()[0]);
                                return;
                        }
                    }
                    Block block = e.getClickedBlock();
                    if (block != null) {
                        blockType = block.getType().name();
                        // Allow-Use
                        if (RegionUtils.isCanUseEntity(blockType)) {
                            if (visitorMap.isInterBlockUse()) {
                                // Allow-Container
                                ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks: Use", "bypass", groupName,
                                        new Throwable().getStackTrace()[0]);
                                return;
                            }
                        }
                        // Allow-Container
                        if (RegionUtils.isContainer(blockType)) {
                            if (visitorMap.isInterBlockCont()) {
                                ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks: Container", "bypass", groupName,
                                        new Throwable().getStackTrace()[0]);
                                return;
                            }
                        }
                        // Cancel
                        if (visitorMap.isInterBlockMsg()) {
                            Language.sendLangMessage("Message.RegionPlus.visitorInteractBlocks", player);
                        }
                        ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "cancel", groupName,
                                new Throwable().getStackTrace()[0]);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
