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
        if (ConfigHandler.getRegionConfig().isPointsSelectInfo()) {
            if (e.getMaterial().equals(ConfigHandler.getRegionConfig().getPointsSelectTool())) {
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
                    if (ConfigHandler.getRegionConfig().isPointsMode()) {
                        size = (Math.abs(loc1.getBlockX() - loc2.getBlockX()) + 1) * (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) + 1);
                    } else {
                        size = (Math.abs(loc1.getBlockX() - loc2.getBlockX()) + 1) * (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) + 1)
                                * (Math.abs(loc1.getBlockY() - loc2.getBlockY()) + 1);
                    }
                    Language.sendLangMessage("Message.RegionPlus.pointsSelect", player, ResidenceUtils.selectPointsPH(player, size));
                    ServerHandler.debugMessage("Residence", playerName, "Select-Tool", "return", "show");
                    return;
                }
                ServerHandler.debugMessage("Residence", playerName, "Select-Tool", "return", "not match");
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
        if (ConfigHandler.getRegionConfig().isVEnable()) {
            if (ConfigHandler.getRegionConfig().isVInteractBlock()) {
                Player player = e.getPlayer();
                String blockType = e.getMaterial().name();
                if (e.getAction().equals(Action.PHYSICAL)) {
                    // Allow-Use
                    if (ConfigHandler.getRegionConfig().isVInteractBlockUse()) {
                        if (RegionUtils.bypassBorder(player, player.getLocation())) {
                            ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "return", "border");
                            return;
                        }
                        switch (blockType) {
                            case "TRIPWIRE":
                            case "ACACIA_PRESSURE_PLATE":
                            case "BIRCH_PRESSURE_PLATE":
                                if (ConfigHandler.getRegionConfig().isVInteractBlockMsg()) {
                                    Language.sendLangMessage("Message.RegionPlus.visitorInteractEntities", player);
                                }
                                ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "cancel", "Physical");
                                e.setCancelled(true);
                                return;
                            default:
                                ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "return", "Physical not contains");
                                return;
                        }
                    }
                    ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "return", "Physical, Use=false");
                    return;
                }
                Block block = e.getClickedBlock();
                if (block != null) {
                    if (RegionUtils.bypassBorder(player, block.getLocation())) {
                        ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "return", "border");
                        return;
                    }
                    blockType = block.getType().name();
                    // Allow-Use
                    if (RegionUtils.isCanUseEntity(blockType)) {
                        if (ConfigHandler.getRegionConfig().isVInteractBlockUse()) {
                            // Allow-Container
                            if (RegionUtils.isContainer(blockType)) {
                                if (ConfigHandler.getRegionConfig().isVInteractBlockContainer()) {
                                    ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "bypass", "Allow-Use=true, Allow-Container=true");
                                    return;
                                }
                            } else {
                                ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "bypass", "Allow-Use=true");
                                return;
                            }
                        }
                    }
                    // Allow-Container
                    if (RegionUtils.isContainer(blockType)) {
                        if (ConfigHandler.getRegionConfig().isVInteractBlockContainer()) {
                            ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "bypass", "Allow-Container=true");
                            return;
                        }
                    }
                    // Cancel
                    if (ConfigHandler.getRegionConfig().isVInteractBlockMsg()) {
                        Language.sendLangMessage("Message.RegionPlus.visitorInteractBlocks", player);
                    }
                    ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "cancel");
                    e.setCancelled(true);
                }
            }
        }
    }
}
