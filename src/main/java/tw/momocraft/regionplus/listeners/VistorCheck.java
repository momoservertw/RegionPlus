package tw.momocraft.regionplus.listeners;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.PermissionsHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.LocationAPI;

public class VistorCheck implements Listener {

/*
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!preventEvent(e.getPlayer(), e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        if (!preventEvent(e.getPlayer(), e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(PlayerInteractEntityEvent e) {
            e.setCancelled(true);
        return;
        Player player = e.getPlayer();
        if (!preventEvent(player, player.getLocation())) {
            e.setCancelled(true);
        }
    }
         */

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (ConfigHandler.getRegionConfig().isVistorInteractBlock())
            e.setCancelled(true);
    }

    /*
    Player player = e.getPlayer();
    if (!preventEvent(player, player.getLocation())) {
        e.setCancelled(true);
    }
    }

     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().hasMetadata("NPC")) {
            ServerHandler.sendConsoleMessage(e.getEventName() + " NPC bypass");
            return;
        }
        ServerHandler.sendConsoleMessage(e.getEventName());
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {
                ServerHandler.sendConsoleMessage(e.getEventName() + " Player bypass");
                return;
            }
            ServerHandler.sendConsoleMessage(e.getEventName());
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        ServerHandler.sendConsoleMessage(e.getEventName());
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityPickupItem(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            ServerHandler.sendConsoleMessage(e.getEventName());
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent e) {
        ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
        if (itemJoinAPI.isCustom(e.getPlayer().getInventory().getItemInMainHand())) {
            ServerHandler.sendConsoleMessage(e.getEventName() + " ItemJoin bypass");
            return;
        }
        ServerHandler.sendConsoleMessage(e.getEventName());
        e.setCancelled(true);
    }

    // This Event will cause error while starting server.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerBucket(PlayerBucketFillEvent e) {
        ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
        if (itemJoinAPI.isCustom(e.getItemStack())) {
            ServerHandler.sendConsoleMessage(e.getEventName() + " ItemJoin bypass");
            return;
        }
        ServerHandler.sendConsoleMessage(e.getEventName());
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
        if (itemJoinAPI.isCustom(e.getItem())) {
            ServerHandler.sendConsoleMessage(e.getEventName() + " ItemJoin bypass");
            return;
        }
        ServerHandler.sendConsoleMessage(e.getEventName());
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player player = (Player) e.getEntity().getShooter();
            ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
            if (itemJoinAPI.isCustom((player.getInventory().getItemInMainHand()))) {
                ServerHandler.sendConsoleMessage(e.getEventName() + " ItemJoin bypass");
                return;
            }
            ServerHandler.sendConsoleMessage(e.getEventName());
            e.setCancelled(true);
        }
    }

    private boolean preventEvent(Player player, Location location) {
        if (LocationAPI.getLocation(location, "Visitor.Border." + location.getWorld())) {
            return PermissionsHandler.hasPermission(player, "regionplus.bypass.worldborder.*") &&
                    PermissionsHandler.hasPermission(player, "regionplus.bypass.worldborder." + location.getBlock().getWorld().getName());
        }
        return true;
    }

    /*
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        String blockType;
        if (e.getAction().equals(Action.PHYSICAL)) {
            blockType = e.getMaterial().name();
            switch (blockType) {
                case "TRIPWIRE":
                case "ACACIA_PRESSURE_PLATE":
                case "BIRCH_PRESSURE_PLATE":
                case "DARK_OAK_PRESSURE_PLATE":
            }
        }

        Block block = e.getClickedBlock();
        if (block != null) {
            blockType = block.getType().name();
            if (isCanUseEntity(blockType) || isContainer(blockType)) {
                if (!preventEvent(e.getPlayer(), block.getLocation())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private boolean isCanUseEntity(String blockType) {
        switch (blockType) {
            // DOOR
            case "ACACIA_DOOR":
            case "BIRCH_DOOR":
            case "DARK_OAK_DOOR":
            case "JUNGLE_DOOR":
            case "OAK_DOOR":
            case "SPRUCE_DOOR":
                // TRAPDOOR
            case "ACACIA_TRAPDOOR":
            case "BIRCH_TRAPDOOR":
            case "DARK_OAK_TRAPDOOR":
            case "JUNGLE_TRAPDOOR":
            case "OAK_TRAPDOOR":
            case "SPRUCE_TRAPDOOR":
                // TRAPDOOR
            case "ACACIA_FENCE_GATE":
            case "BIRCH_FENCE_GATE":
            case "DARK_OAK_FENCE_GATE":
            case "JUNGLE_FENCE_GATE":
            case "OAK_FENCE_GATE":
            case "SPRUCE_FENCE_GATE":
                // LEVER & BUTTON
            case "LEVER":
            case "ACACIA_BUTTON":
            case "BIRCH_BUTTON":
            case "DARK_OAK_BUTTON":
            case "JUNGLE_BUTTON":
            case "OAK_BUTTON":
            case "SPRUCE_BUTTON":
            case "STONE_BUTTON":
                // CRAFTING
            case "CRAFTING_TABLE":
            case "ENCHANTING_TABLE":
            case "FLETCHING_TABLE":
            case "SMITHING_TABLE":
            case "NOTE_BLOCK":
            case "ANVIL":
            case "BREWING_STAND":
                // REDSTONE MACHINE
            case "DIODE":
            case "COMPARATOR":
            case "REPEATER":
            case "REDSTONE_COMPARATOR":
            case "DAYLIGHT_DETECTOR":
                // OTHER
            case "BEACON":
            case "ITEM_FRAME":
            case "FLOWER_POT":
            case "BED_BLOCK":
            case "CAKE_BLOCK":
            case "COMMAND":
                return true;
            default:
                return false;
        }
    }

    private boolean isContainer(String blockType) {
        switch (blockType) {
            // CHEST
            case "CHEST":
            case "TRAPPED_CHEST":
                // SHULKER_BOX
            case "SHULKER_BOX":
            case "BLACK_SHULKER_BOX":
            case "BROWN_SHULKER_BOX":
            case "BLUE_SHULKER_BOX":
            case "CYAN_SHULKER_BOX":
            case "GRAY_SHULKER_BOX":
            case "GREEN_SHULKER_BOX":
            case "LIGHT_BLUE_SHULKER_BOX":
            case "LIGHT_GRAY_SHULKER_BOX":
            case "LIME_SHULKER_BOX":
            case "MAGENTA_SHULKER_BOX":
            case "ORANGE_SHULKER_BOX":
            case "PINK_SHULKER_BOX":
            case "PURPLE_SHULKER_BOX":
            case "RED_SHULKER_BOX":
            case "WHITE_SHULKER_BOX":
            case "YELLOW_SHULKER_BOX":
                // CRAFTING
            case "BREWING_STAND":
            case "DISPENSER":
            case "DROPPER":
            case "FURNACE":
            case "HOPPER":
            case "SMOKER":
            case "BARREL":
            case "BLAST_FURNACE":
            case "LOOM":
                // OTHER
            case "ITEM_FRAME":
            case "JUKEBOX":
            case "ARMOR_STAND":
                return true;
            default:
                return false;
        }
    }
     */
}
