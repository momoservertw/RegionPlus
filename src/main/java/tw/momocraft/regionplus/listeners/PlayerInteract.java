package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import org.bukkit.Location;
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onResPointsSelectInfo(PlayerInteractEvent e) {
        if (ConfigHandler.getRegionConfig().isResPointsSelectInfo()) {
            if (e.getMaterial().equals(ConfigHandler.getRegionConfig().getResPointsSelectTool())) {
                Player player = e.getPlayer();
                SelectionManager smanager = Residence.getInstance().getSelectionManager();
                Location loc1 = smanager.getSelection(player).getBaseLoc1();
                Location loc2 = smanager.getSelection(player).getBaseLoc2();
                if (loc1 != null && loc2 != null) {
                    long size;
                    if (ConfigHandler.getRegionConfig().getResPointsMode().equals("XZ")) {
                        size = (loc1.getBlockX() - loc2.getBlockX()) * (loc1.getBlockZ() - loc2.getBlockZ());
                    } else {
                        size = (loc1.getBlockX() - loc2.getBlockX()) * (loc1.getBlockZ() - loc2.getBlockZ()) * (loc1.getBlockY() - loc2.getBlockY());
                    }
                    long pointsLimit = ResidenceUtils.getLimit(player);
                    long pointsUsed = ResidenceUtils.getUsed(player);
                    String[] placeHolders = Language.newString();
                    placeHolders[8] = String.valueOf(pointsLimit);
                    placeHolders[9] = String.valueOf(pointsUsed);
                    placeHolders[10] = String.valueOf(pointsLimit - pointsUsed);
                    placeHolders[11] = String.valueOf(size);
                    placeHolders[12] = String.valueOf(
                            Residence.getInstance().getPlayerManager().getGroup(player.getName()).getCostPerBlock() * size);
                    Language.sendLangMessage("Message.RegionPlus.pointsSelect", player, placeHolders);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorInteractBlock()) {
            Player player = e.getPlayer();
            String blockType = e.getMaterial().name();
            if (e.getAction().equals(Action.PHYSICAL)) {
                // Allow-Use
                if (ConfigHandler.getRegionConfig().isVisitorInteractBlockUse()) {
                    if (RegionUtils.bypassBorder(player, player.getLocation())) {
                        ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "return", "border");
                        return;
                    }
                    switch (blockType) {
                        case "TRIPWIRE":
                        case "ACACIA_PRESSURE_PLATE":
                        case "BIRCH_PRESSURE_PLATE":
                            if (ConfigHandler.getRegionConfig().isVisitorInteractBlockMsg()) {
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
                    if (ConfigHandler.getRegionConfig().isVisitorInteractBlockUse()) {
                        // Allow-Container
                        if (RegionUtils.isContainer(blockType)) {
                            if (ConfigHandler.getRegionConfig().isVisitorInteractBlockContainer()) {
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
                    if (ConfigHandler.getRegionConfig().isVisitorInteractBlockContainer()) {
                        ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "bypass", "Allow-Container=true");
                        return;
                    }
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVisitorInteractBlockMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorInteractBlocks", player);
                }
                ServerHandler.debugMessage("Visitor", blockType, "Interact-Blocks", "cancel");
                e.setCancelled(true);
            }
        }
    }
}
