package tw.momocraft.regionplus.listeners;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.Language;
import tw.momocraft.regionplus.utils.RegionUtils;

public class VisitorCheck implements Listener {

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
                if (isCanUseEntity(blockType)) {
                    if (ConfigHandler.getRegionConfig().isVisitorInteractBlockUse()) {
                        // Allow-Container
                        if (isContainer(blockType)) {
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
                if (isContainer(blockType)) {
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


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorInteractEntities()) {
            Player player = e.getPlayer();
            Entity entity = e.getRightClicked();
            String entityType = entity.getType().name();
            if (RegionUtils.bypassBorder(player, player.getLocation())) {
                ServerHandler.debugMessage("Visitor", entityType, "Interact-Entities", "return", "border");
                return;
            }
            // Allow-NPC
            if (entity.hasMetadata("NPC")) {
                if (ConfigHandler.getRegionConfig().isVisitorInteractEntitiesNPC()) {
                    ServerHandler.debugMessage("Visitor", entityType, "Interact-Entities", "bypass", "Allow-NPC=true");
                    return;
                }
            }
            // Cancel
            if (ConfigHandler.getRegionConfig().isVisitorInteractEntitiesMsg()) {
                Language.sendLangMessage("Message.RegionPlus.visitorInteractEntities", player);
            }
            ServerHandler.debugMessage("Visitor", entityType, "Interact-Entities", "cancel");
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorDamageEntities()) {
            if (e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();
                Entity entity = e.getEntity();
                String entityType = entity.getType().name();
                if (RegionUtils.bypassBorder(player, entity.getLocation())) {
                    ServerHandler.debugMessage("Visitor", entityType, "Damage-Entities", "return", "border");
                    return;
                }
                // Allow-Player
                if (ConfigHandler.getRegionConfig().isVisitorDamageEntitiesPlayer()) {
                    if (entity instanceof Player) {
                        ServerHandler.debugMessage("Visitor", entityType, "Damage-Entities", "bypass", "Allow-Player=true");
                        return;
                    }
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVisitorDamageEntitiesMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorDamageEntities", player);
                }
                ServerHandler.debugMessage("Visitor", entityType, "Damage-Entities", "cancel");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorDropItems()) {
            Player player = e.getPlayer();
            String itemType = e.getItemDrop().getName();
            if (RegionUtils.bypassBorder(player, player.getLocation())) {
                ServerHandler.debugMessage("Visitor", itemType, "Drop-Items", "return", "border");
                return;
            }
            // Cancel
            if (ConfigHandler.getRegionConfig().isVisitorDropItemsMsg()) {
                Language.sendLangMessage("Message.RegionPlus.visitorDropItems", player);
            }
            ServerHandler.debugMessage("Visitor", itemType, "Drop-Items", "cancel");
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityPickupItem(EntityPickupItemEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorPickupItems()) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                String itemType = e.getItem().getName();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.debugMessage("Visitor", itemType, "Pickup-Items", "return", "border");
                    return;
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVisitorPickupItemsMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorPickupItems", player);
                }
                ServerHandler.debugMessage("Visitor", itemType, "Pickup-Items", "cancel");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorUseItems()) {
            if (!ConfigHandler.getRegionConfig().isVisitorItemsFishing()) {
                Player player = e.getPlayer();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.debugMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "return", "border");
                    return;
                }
                // Allow-ItemJoin
                if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                    if (!ConfigHandler.getRegionConfig().isVisitorItemJoin()) {
                        ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                        if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                            ServerHandler.debugMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "bypass", "Allow-ItemJoin=true");
                            return;
                        }
                    }
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVisitorUseItemsMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                }
                ServerHandler.debugMessage("Visitor", "FISHING_ROD", "Use-Items.Fishing", "cancel", "Allow-Fishing=false");
                e.setCancelled(true);
            }
        }
    }

    // This Event will cause error while starting server.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerBucket(PlayerBucketFillEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorUseItems()) {
            if (!ConfigHandler.getRegionConfig().isVisitorItemsBucket()) {
                Player player = e.getPlayer();
                String itemType = e.getItemStack().getType().name();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Bucket", "return", "border");
                    return;
                }
                // Allow-ItemJoin
                if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                    if (!ConfigHandler.getRegionConfig().isVisitorItemJoin()) {
                        ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                        if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                            ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Bucket", "bypass", "Allow-ItemJoin");
                            return;
                        }
                    }
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVisitorUseItemsMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                }
                ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Bucket", "cancel", "Allow-Bucket=false");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        if (ConfigHandler.getRegionConfig().isVisitorUseItems()) {
            if (!ConfigHandler.getRegionConfig().isVisitorItemsConsume()) {
                Player player = e.getPlayer();
                String itemType = e.getItem().getType().name();
                if (RegionUtils.bypassBorder(player, player.getLocation())) {
                    ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Consume", "return", "border");
                    return;
                }
                // Allow-ItemJoin
                if (ConfigHandler.getDepends().ItemJoinEnabled()) {
                    if (!ConfigHandler.getRegionConfig().isVisitorItemJoin()) {
                        ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
                        if (itemJoinAPI.isCustom(player.getInventory().getItemInMainHand())) {
                            ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Consume", "bypass", "Allow-ItemJoin=true");
                            return;
                        }
                    }
                }
                // Cancel
                if (ConfigHandler.getRegionConfig().isVisitorUseItemsMsg()) {
                    Language.sendLangMessage("Message.RegionPlus.visitorUseItems", player);
                }
                ServerHandler.debugMessage("Visitor", itemType, "Use-Items.Consume", "cancel", "Allow-Consume=false");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
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
}
