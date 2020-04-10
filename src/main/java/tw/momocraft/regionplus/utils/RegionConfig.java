package tw.momocraft.regionplus.utils;


import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.*;

public class RegionConfig {

    private boolean playerPreventFly;
    private String playerPreventFlyPerm;

    private boolean RPEnable;
    private boolean RPPotionDamage;
    private boolean RPFlyDisable;
    private boolean RPPainting;
    private boolean RPItemFrame;
    private boolean RPArmorStand;
    private boolean RPZombieDoor;
    private boolean RPEndermanPickup;

    private boolean pointsEnable;
    private boolean pointsSelectInfo;
    private Material pointsSelectTool;
    private boolean pointsMode;
    private boolean pointsIgnoreXYZ;
    private boolean resReturnXYZ;
    private boolean resAllAreas;
    private boolean resIgnoreWithin;
    private Long pointsDefault;
    private Map<String, Long> pointsMap;
    private Map<String, String> pointsDisplayMap;

    private boolean RFEnable;
    private boolean RFAutoCheck;
    private long RFAutoCheckDelay;
    private int RFMaxLimit;
    private int RFMaxInterval;
    private boolean RFMessage;
    private boolean RFBypassCustom;
    private boolean RFBypassPerms;
    private boolean RFDefaultRemove;
    private boolean RFDefaultRemoveOnly;
    private List<String> RFDefaultRemoveIgnore;
    private boolean RFDefaultUpdate;
    private List<String> RFDefaultUpdateIgnore;
    private boolean RFPermsRemove;
    private List<String> RFPermsRemoveIgnore;

    private boolean VEnable;
    private boolean VCreateRes;
    private boolean VCreateResMsg;
    private boolean VInteractBlock;
    private boolean VInteractBlockUse;
    private boolean VInteractBlockContainer;
    private boolean VInteractEntities;
    private boolean VInteractEntitiesNPC;
    private boolean VDamageEntities;
    private boolean VDamageEntitiesPlayer;
    private boolean VDropItems;
    private boolean VPickupItems;
    private boolean VUseItems;
    private boolean VItemsConsume;
    private boolean VItemsBucket;
    private boolean VItemsProjectile;
    private boolean VItemsFishing;
    private boolean VItemJoin;
    private boolean VInteractBlockMsg;
    private boolean VInteractEntitiesMsg;
    private boolean VDamageEntitiesMsg;
    private boolean VDropItemsMsg;
    private boolean VPickupItemsMsg;
    private boolean VUseItemsMsg;

    public RegionConfig() {
        setUp();
    }

    private void setUp() {
        playerPreventFly = ConfigHandler.getConfig("config.yml").getBoolean("Player.Prevent.Fly-Disable.Enable");
        playerPreventFlyPerm = ConfigHandler.getConfig("config.yml").getString("Player.Prevent.Fly-Disable.Permission");

        resAllAreas = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.All-Areas.Enable");
        resIgnoreWithin = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.All-Areas.Ignore-Within-Area");
        resReturnXYZ = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Return-XYZ");

        RPEnable = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enable");
        RPFlyDisable = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Fly-Disable");
        RPPotionDamage = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Potion-Damage");
        RPPainting = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Painting-Destroy");
        RPItemFrame = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Item-Frame-Destroy");
        RPArmorStand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Armor-Stand-Destroy");
        RPZombieDoor = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent. Zombie-Door-Destruction");
        RPEndermanPickup = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enderman-Pickup-Block");

        pointsEnable = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Enable");
        pointsSelectInfo = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Select-Info");
        pointsSelectTool = Residence.getInstance().getConfigManager().getSelectionTool().getMaterial();
        pointsMode = Residence.getInstance().getConfigManager().isSelectionIgnoreY();
        pointsIgnoreXYZ = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Check.Ignore-XYZ");
        pointsDefault = ConfigHandler.getConfig("config.yml").getLong("Residence.Points.Groups.Default.Limit");
        pointsMap = new HashMap<>();
        pointsDisplayMap = new HashMap<>();
        ConfigurationSection resPointsGroupsConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Residence.Points.Groups");
        if (resPointsGroupsConfig != null) {
            for (String key : resPointsGroupsConfig.getKeys(false)) {
                pointsMap.put(key.toLowerCase(), ConfigHandler.getConfig("config.yml").getLong("Residence.Points.Groups." + key + ".Limit"));
                pointsDisplayMap.put(key.toLowerCase(), ConfigHandler.getConfig("config.yml").getString("Residence.Points.Groups." + key + ".Display"));
            }
            pointsMap = Utils.sortByValue(pointsMap);
        }

        RFEnable = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Enable");
        RFAutoCheck = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Auto-Check.Enable");
        RFAutoCheckDelay = ConfigHandler.getConfig("config.yml").getLong("Residence.Flags-Editor.Settings.Auto-Check.Delay") * 20;
        RFMaxLimit = ConfigHandler.getConfig("config.yml").getInt("Residence.Flags-Editor.Settings.Max-Edit-Players.Limit");
        RFMaxInterval = ConfigHandler.getConfig("config.yml").getInt("Residence.Flags-Editor.Settings.Max-Edit-Players.Interval");
        RFMessage = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Max-Edit-Players.Message");
        RFBypassCustom = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Bypass-Missing-Custom-Flags");
        RFBypassPerms = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Check-Bypass-Permission");
        RFDefaultUpdate = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Default.Update.Enable");
        RFDefaultUpdateIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Default.Update.Ignore");
        RFDefaultRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Default.Remove.Enable");
        RFDefaultRemoveOnly = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Default.Remove.Only-No-Perms");
        RFDefaultRemoveIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Default.Remove.Ignore-List");
        RFPermsRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Permissions.Remove.Enable");
        RFPermsRemoveIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Permissions.Remove.Ignore-List");

        VEnable = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Enable");
        VCreateRes = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Create-Residence.Enable");
        VCreateResMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Create-Residence.Message");
        VInteractBlock = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Enable");
        VInteractBlockMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Message");
        VInteractBlockUse = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Allow-Use");
        VInteractBlockContainer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Allow-Container");
        VInteractEntities = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Enable");
        VInteractEntitiesMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Message");
        VInteractEntitiesNPC = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Allow-NPC");
        VDamageEntities = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Enable");
        VDamageEntitiesMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Message");
        VDamageEntitiesPlayer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Allow-Player");
        VDropItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Drop-Items.Enable");
        VDropItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Drop-Items.Message");
        VPickupItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Pickup-Items.Enable");
        VPickupItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Pickup-Items.Message");
        VUseItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Enable");
        VUseItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Message");
        VItemsConsume = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Consume");
        VItemsBucket = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Bucket");
        VItemsProjectile = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Projectile");
        VItemsFishing = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Fishing");
        VItemJoin = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-ItemJoin");
    }

    public boolean isPlayerPreventFly() {
        return playerPreventFly;
    }

    public String getPlayerPreventFlyPerm() {
        return playerPreventFlyPerm;
    }

    public boolean isRPEnable() {
        return RPEnable;
    }

    public boolean isRPFlyDisable() {
        return RPFlyDisable;
    }

    public boolean isRPPotionDamage() {
        return RPPotionDamage;
    }

    public boolean isRPItemFrame() {
        return RPItemFrame;
    }

    public boolean isRPPainting() {
        return RPPainting;
    }

    public boolean isRPArmorStand() {
        return RPArmorStand;
    }

    public boolean isRPZombieDoor() {
        return RPZombieDoor;
    }

    public boolean isRPEndermanPickup() {
        return RPEndermanPickup;
    }


    public boolean isPointsEnable() {
        return pointsEnable;
    }

    public boolean isPointsSelectInfo() {
        return pointsSelectInfo;
    }

    public Material getPointsSelectTool() {
        return pointsSelectTool;
    }

    public boolean isPointsMode() {
        return pointsMode;
    }

    public boolean isPointsIgnoreXYZ() {
        return pointsIgnoreXYZ;
    }

    public boolean isResReturnXYZ() {
        return resReturnXYZ;
    }

    public boolean isResAllAreas() {
        return resAllAreas;
    }

    public boolean isResIgnoreWithin() {
        return resIgnoreWithin;
    }

    public Long getPointsDefault() {
        return pointsDefault;
    }

    public Map<String, Long> getPointsMap() {
        return pointsMap;
    }

    public Map<String, String> getPointsDisplayMap() {
        return pointsDisplayMap;
    }


    public boolean isRFEnable() {
        return RFEnable;
    }

    public boolean isRFAutoCheck() {
        return RFAutoCheck;
    }

    public long getRFAutoCheckDelay() {
        return RFAutoCheckDelay;
    }

    public int getRFMaxLimit() {
        return RFMaxLimit;
    }

    public int getRFMaxInterval() {
        return RFMaxInterval;
    }

    public boolean isRFMessage() {
        return RFMessage;
    }

    public boolean isRFBypassCustom() {
        return RFBypassCustom;
    }

    public boolean isRFBypassPerms() {
        return RFBypassPerms;
    }

    public boolean isRFDefaultUpdate() {
        return RFDefaultUpdate;
    }

    public List<String> getRFDefaultUpdateIgnore() {
        return RFDefaultUpdateIgnore;
    }

    public boolean isRFDefaultRemove() {
        return RFDefaultRemove;
    }

    public boolean isRFDefaultRemoveOnly() {
        return RFDefaultRemoveOnly;
    }

    public List<String> getRFDefaultRemoveIgnore() {
        return RFDefaultRemoveIgnore;
    }


    public List<String> getRFPermsRemoveIgnore() {
        return RFPermsRemoveIgnore;
    }

    public boolean isRFPermsRemove() {
        return RFPermsRemove;
    }


    public boolean isVEnable() {
        return VEnable;
    }

    public boolean isVCreateRes() {
        return VCreateRes;
    }

    public boolean isVCreateResMsg() {
        return VCreateResMsg;
    }

    public boolean isVInteractBlock() {
        return VInteractBlock;
    }

    public boolean isVInteractEntities() {
        return VInteractEntities;
    }

    public boolean isVInteractBlockContainer() {
        return VInteractBlockContainer;
    }

    public boolean isVInteractBlockUse() {
        return VInteractBlockUse;
    }

    public boolean isVInteractEntitiesNPC() {
        return VInteractEntitiesNPC;
    }

    public boolean isVDamageEntities() {
        return VDamageEntities;
    }

    public boolean isVDamageEntitiesPlayer() {
        return VDamageEntitiesPlayer;
    }

    public boolean isVDropItems() {
        return VDropItems;
    }

    public boolean isVItemsBucket() {
        return VItemsBucket;
    }

    public boolean isVItemsConsume() {
        return VItemsConsume;
    }

    public boolean isVItemsFishing() {
        return VItemsFishing;
    }

    public boolean isVItemJoin() {
        return VItemJoin;
    }

    public boolean isVItemsProjectile() {
        return VItemsProjectile;
    }

    public boolean isVPickupItems() {
        return VPickupItems;
    }

    public boolean isVUseItems() {
        return VUseItems;
    }

    public boolean isVDamageEntitiesMsg() {
        return VDamageEntitiesMsg;
    }

    public boolean isVDropItemsMsg() {
        return VDropItemsMsg;
    }

    public boolean isVInteractBlockMsg() {
        return VInteractBlockMsg;
    }

    public boolean isVInteractEntitiesMsg() {
        return VInteractEntitiesMsg;
    }

    public boolean isVPickupItemsMsg() {
        return VPickupItemsMsg;
    }

    public boolean isVUseItemsMsg() {
        return VUseItemsMsg;
    }
}