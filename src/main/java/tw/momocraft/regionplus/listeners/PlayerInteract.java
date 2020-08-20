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
            if (ConfigHandler.getConfigPath().isVisInteractBlock()) {
                Player player = e.getPlayer();
                String blockType = e.getMaterial().name();
                if (e.getAction().equals(Action.PHYSICAL)) {
                    // Allow-Use
                    if (ConfigHandler.getConfigPath().isVisInteractBlockUse()) {
                        if (RegionUtils.bypassBorder(player, player.getLocation())) {
                            ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "return", "border",
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                        switch (blockType) {
                            case "TRIPWIRE":
                            case "ACACIA_PRESSURE_PLATE":
                            case "BIRCH_PRESSURE_PLATE":
                                if (ConfigHandler.getConfigPath().isVisInteractBlockMsg()) {
                                    Language.sendLangMessage("Message.RegionPlus.visitorInteractEntities", player);
                                }
                                ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "cancel", "Physical",
                                        new Throwable().getStackTrace()[0]);
                                e.setCancelled(true);
                                return;
                            default:
                                ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "return", "Physical not contains",
                                        new Throwable().getStackTrace()[0]);
                                return;
                        }
                    }
                    ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "return", "Physical, Use=false",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
                Block block = e.getClickedBlock();
                if (block != null) {
                    if (RegionUtils.bypassBorder(player, block.getLocation())) {
                        ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "return", "border",
                                new Throwable().getStackTrace()[0]);
                        return;
                    }
                    blockType = block.getType().name();
                    // Allow-Use
                    if (RegionUtils.isCanUseEntity(blockType)) {
                        if (ConfigHandler.getConfigPath().isVisInteractBlockUse()) {
                            // Allow-Container
                            if (RegionUtils.isContainer(blockType)) {
                                if (ConfigHandler.getConfigPath().isVisInteractBlockContainer()) {
                                    ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "bypass", "Allow-Use=true, Allow-Container=true",
                                            new Throwable().getStackTrace()[0]);
                                    return;
                                }
                            } else {
                                ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "bypass", "Allow-Use=true",
                                        new Throwable().getStackTrace()[0]);
                                return;
                            }
                        }
                    }
                    // Allow-Container
                    if (RegionUtils.isContainer(blockType)) {
                        if (ConfigHandler.getConfigPath().isVisInteractBlockContainer()) {
                            ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "bypass", "Allow-Container=true",
                                    new Throwable().getStackTrace()[0]);
                            return;
                        }
                    }
                    // Cancel
                    if (ConfigHandler.getConfigPath().isVisInteractBlockMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorInteractBlocks", player);
                    }
                    ServerHandler.sendFeatureMessage("Visitor", blockType, "Interact-Blocks", "cancel",
                            new Throwable().getStackTrace()[0]);
                    e.setCancelled(true);
                }
            }
        }
    }
}
