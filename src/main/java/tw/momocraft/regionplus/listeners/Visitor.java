package tw.momocraft.regionplus.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class Visitor implements Listener {

    // Use-Item: Allow-Consume
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVisitorItemsConsume(PlayerItemConsumeEvent e) {
        if (!ConfigHandler.getConfigPath().isVisUseItem())
            return;
        Player player = e.getPlayer();
        String playerName = player.getName();
        // Allow-Consume
        if (ConfigHandler.getConfigPath().isVisItemConsume()) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Consume", "return", "Allow",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), loc, ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Consume", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Consume", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Use-Item: Consume", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Allow-ItemJoin
        if (ConfigHandler.getConfigPath().isVisItemJoin()) {
            if (CorePlusAPI.getCond().isCustomItem(player.getInventory().getItemInMainHand())) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Use-Item: Consume", "bypass", "ItemJoin",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisUseItemMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVisItemUse(), player);
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Use-Item: Consume", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Use-Item: Allow-Bucket
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVisitorItemsBucket(PlayerBucketFillEvent e) {
        if (!ConfigHandler.getConfigPath().isVisUseItem())
            return;
        Player player = e.getPlayer();
        String playerName = player.getName();
        // Allow-Bucket
        if (ConfigHandler.getConfigPath().isVisItemBucket()) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Bucket", "return", "Allow",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), loc,
                ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Bucket", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Bucket", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Use-Item: Bucket", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;

            }
        }
        // Allow-ItemJoin
        if (ConfigHandler.getConfigPath().isVisItemJoin()) {
            if (CorePlusAPI.getCond().isCustomItem(player.getInventory().getItemInMainHand())) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Use-Item: Bucket", "bypass", "ItemJoin",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisUseItemMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVisItemUse(), player);
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Use-Item: Bucket", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Use-Item: Fishing
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVisitorItemsFishing(PlayerFishEvent e) {
        if (!ConfigHandler.getConfigPath().isVisUseItem())
            return;
        // Allow-Fishing
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (ConfigHandler.getConfigPath().isVisItemFishing()) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Fishing", "return", "Allow",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), loc,
                ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Fishing", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Fishing", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Use-Item: Fishing", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;

            }
        }
        // Allow-ItemJoin
        if (ConfigHandler.getConfigPath().isVisItemJoin()) {
            if (CorePlusAPI.getCond().isCustomItem(player.getInventory().getItemInMainHand())) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Use-Item: Fishing", "bypass", "ItemJoin",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisUseItemMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVisItemUse(), player);
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Use-Item: Fishing", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Use-Item: Allow-Projectile
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVisitorItemsProjectile(ProjectileLaunchEvent e) {
        if (!ConfigHandler.getConfigPath().isVisUseItem())
            return;
        if (!(e.getEntity().getShooter() instanceof Player))
            return;
        // Allow-Projectile
        Player player = (Player) e.getEntity().getShooter();
        String playerName = player.getName();
        if (ConfigHandler.getConfigPath().isVisItemProjectile()) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Fishing", "return", "Allow",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), loc,
                ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Projectile", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Use-Item: Projectile", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Use-Item: Projectile", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Allow-ItemJoin
        if (ConfigHandler.getConfigPath().isVisItemJoin()) {
            if (CorePlusAPI.getCond().isCustomItem(player.getInventory().getItemInMainHand())) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Use-Item: Projectile", "bypass", "ItemJoin",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisUseItemMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVisItemUse(), player);
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Use-Item: Projectile", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Interact-Entity
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVisitorInteractEntities(PlayerInteractEntityEvent e) {
        if (!ConfigHandler.getConfigPath().isVisInterEnt())
            return;
        Player player = e.getPlayer();
        String playerName = player.getName();
        Entity entity = e.getRightClicked();
        Location loc = entity.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), loc,
                ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Interact-Entity", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Interact-Entity", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Interact-Entity", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Allow-NPC
        if (ConfigHandler.getConfigPath().isVisInterEntNPC()) {
            if (entity.hasMetadata("NPC")) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Interact-Entity: NPC", "bypass",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisInterEntMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVisInteractEntity(), player);
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Pickup-Item", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Interact-Block
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!ConfigHandler.getConfigPath().isVisInterBlock())
            return;
        Player player = e.getPlayer();
        String playerName = player.getName();
        Action action = e.getAction();
        if (action.equals(Action.PHYSICAL)) {
            // Location
            String blockType = e.getMaterial().name();
            Location loc = e.getPlayer().getLocation();
            if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), player.getLocation(),
                    ConfigHandler.getConfigPath().getVisLocList(), false)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Interact-Block", "return", "Location",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            // Bypass Permission
            if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Interact-Block", "bypass", "Permission",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            // Allow Residence Bypass
            if (ConfigHandler.getConfigPath().isVisResBypass()) {
                if (CorePlusAPI.getCond().isInResidence(loc)) {
                    CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                            "Visitor", playerName, "Interact-Block", "bypass", "Residence Bypass",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
            }
            // Allow-Use
            if (ConfigHandler.getConfigPath().isVisInterBlockUse()) {
                if (CorePlusAPI.getCond().isCanUse(blockType)) {
                    CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                            "Visitor", playerName, "Interact-Block: Use", "return",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
            }
            // Cancel
            if (ConfigHandler.getConfigPath().isVisInterBlockMsg())
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgVisInteractBlock(), player);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Interact-Block", "cancel", blockType,
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        if (action.equals(Action.LEFT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = e.getClickedBlock();
            if (block == null)
                return;
            Location loc = block.getLocation();
            // Location
            if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), player.getLocation(),
                    ConfigHandler.getConfigPath().getVisLocList(), false)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Interact-Block", "return", "Location",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            // Bypass Permission
            if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Interact-Block", "bypass", "Permission",
                        new Throwable().getStackTrace()[0]);
                return;
            }
            // Allow Residence Bypass
            if (ConfigHandler.getConfigPath().isVisResBypass()) {
                if (CorePlusAPI.getCond().isInResidence(loc)) {
                    CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                            "Visitor", playerName, "Interact-Block", "bypass", "Residence Bypass",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
            }
            String blockType = block.getType().name();
            // Allow-Use
            if (ConfigHandler.getConfigPath().isVisInterBlockUse()) {
                if (CorePlusAPI.getCond().isCanUse(blockType)) {
                    CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                            "Visitor", playerName, "Interact-Block: Use", "return",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
            }
            // Allow-Container
            if (ConfigHandler.getConfigPath().isVisInterBlockCont()) {
                if (CorePlusAPI.getCond().isContainer(blockType)) {
                    CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                            "Visitor", playerName, "Interact-Block: Container", "return",
                            new Throwable().getStackTrace()[0]);
                    return;
                }
            }
            // Cancel
            if (ConfigHandler.getConfigPath().isVisInterBlockMsg())
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgVisInteractBlock(), player);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Interact-Block", "cancel", blockType,
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
        }
    }

    // Drop-Item
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVisitorDropItems(PlayerDropItemEvent e) {
        if (!ConfigHandler.getConfigPath().isVisDropItem())
            return;
        Player player = e.getPlayer();
        String playerName = player.getName();
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), loc,
                ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Drop-Item", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Drop-Item", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Drop-Item", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisDropItemMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVisDropItem(), player);
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Drop-Item", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    // Pickup-Item
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVisitorPickupItems(EntityPickupItemEvent e) {
        if (!ConfigHandler.getConfigPath().isVisitor())
            return;
        if (!ConfigHandler.getConfigPath().isVisPickupItem())
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        Player player = (Player) e.getEntity();
        String playerName = player.getName();
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), loc,
                ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Pickup-Item", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Pickup-Item", "bypass", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Pickup-Item", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisPickupItemMsg())
            CorePlusAPI.getMsg().sendPlayerMsg(ConfigHandler.getPrefix(), player,
                    ConfigHandler.getConfigPath().getMsgVisPickupItem());
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Pickup-Item", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVisitorDamageEntities(EntityDamageByEntityEvent e) {
        if (!ConfigHandler.getConfigPath().isVisitor())
            return;
        if (!ConfigHandler.getConfigPath().isVisDamageEnt())
            return;
        if (!(e.getDamager() instanceof Player))
            return;
        Player player = (Player) e.getEntity();
        String playerName = player.getName();
        Entity entity = e.getEntity();
        Location loc = entity.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), player.getLocation(),
                ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Damage-Entity", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Damage-Entity", "return", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Damage-Entity", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;

            }
        }
        // Allow-Player
        if (ConfigHandler.getConfigPath().isVisDamageEntPlayer()) {
            if (entity instanceof Player) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Damage-Entity: Player", "bypass", entity.getName(),
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisDamageEntMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVisDamageEntity(), player);
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Damage-Entity", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        if (!ConfigHandler.getConfigPath().isVisitor())
            return;
        if (!ConfigHandler.getConfigPath().isVisDeathDrop())
            return;
        Player player = e.getEntity();
        String playerName = player.getName();
        Location loc = player.getLocation();
        // Location
        if (!CorePlusAPI.getCond().checkLocation(ConfigHandler.getPluginName(), player.getLocation(),
                ConfigHandler.getConfigPath().getVisLocList(), false)) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Death-Drop", "return", "Location",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Bypass Permission
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.visitor")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                    "Visitor", playerName, "Death-Drop", "return", "Permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Allow Residence Bypass
        if (ConfigHandler.getConfigPath().isVisResBypass()) {
            if (CorePlusAPI.getCond().isInResidence(loc)) {
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                        "Visitor", playerName, "Death-Drop", "bypass", "Residence Bypass",
                        new Throwable().getStackTrace()[0]);
                return;
            }
        }
        // Cancel
        if (ConfigHandler.getConfigPath().isVisDeathDropMsg())
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVisDeathDrop(), player);
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPluginName(),
                "Visitor", playerName, "Death-Drop", "cancel", "Final",
                new Throwable().getStackTrace()[0]);
        e.setKeepInventory(true);
    }
}
