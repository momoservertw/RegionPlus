package tw.momocraft.regionplus.utils;


import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.HashMap;

public class RegionConfig {

    private boolean playerPreventFly;
    private String playerPreventFlyPerm;

    private boolean resPreventEnable;
    private boolean resPreventPotionDamage;
    private boolean resPreventFlyDisable;
    private boolean resPreventPainting;
    private boolean resPreventItemFrame;
    private boolean resPreventArmorStand;
    private boolean resPreventZombieDoor;
    private boolean resPreventEndermanPickup;

    private boolean resPointsEnable;
    private boolean resPointsSelectInfo;
    private Material resPointsSelectTool;
    private String resPointsMode;
    private boolean resPointsIgnoreXYZ;
    private boolean resPointsReturnXYZ;
    private boolean resPointsAllAreas;
    private boolean resPointsIgnoreWithin;
    private long resPointsDefault;
    private ConfigurationSection resPointsGroupsConfig;
    private HashMap<String, Long> resPointsLimitMap;
    private HashMap<String, String> resPointsDisplayMap;

    private boolean resFlagEdit;
    private int resFlagEditMax;
    private boolean resFlagTimeOut;
    private boolean resFlagAutoCheck;
    private long resFlagAutoCheckDelay;
    private boolean resFlagEditUpdate;
    private boolean resFlagEditRemove;
    private boolean resFlagEditRemovePerm;

    private boolean visitorEnable;
    private boolean visitorCreateRes;
    private boolean visitorCreateResMsg;
    private boolean visitorInteractBlock;
    private boolean visitorInteractBlockUse;
    private boolean visitorInteractBlockContainer;
    private boolean visitorInteractEntities;
    private boolean visitorInteractEntitiesNPC;
    private boolean visitorDamageEntities;
    private boolean visitorDamageEntitiesPlayer;
    private boolean visitorDropItems;
    private boolean visitorPickupItems;
    private boolean visitorUseItems;
    private boolean visitorItemsConsume;
    private boolean visitorItemsBucket;
    private boolean visitorItemsProjectile;
    private boolean visitorItemsFishing;
    private boolean visitorItemJoin;
    private boolean visitorInteractBlockMsg;
    private boolean visitorInteractEntitiesMsg;
    private boolean visitorDamageEntitiesMsg;
    private boolean visitorDropItemsMsg;
    private boolean visitorPickupItemsMsg;
    private boolean visitorUseItemsMsg;

    public RegionConfig() {
        setUp();
    }

    private void setUp() {
        playerPreventFly = ConfigHandler.getConfig("config.yml").getBoolean("Player.Prevent.Fly-Disable.Enable");
        playerPreventFlyPerm = ConfigHandler.getConfig("config.yml").getString("Player.Prevent.Fly-Disable.Permission");

        resPreventEnable = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enable");
        resPreventFlyDisable = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Fly-Disable");
        resPreventPotionDamage = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Potion-Damage");
        resPreventPainting = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Painting-Destroy");
        resPreventItemFrame = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Item-Frame-Destroy");
        resPreventArmorStand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Armor-Stand-Destroy");
        resPreventZombieDoor = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent. Zombie-Door-Destruction");
        resPreventEndermanPickup = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enderman-Pickup-Block");

        resPointsEnable = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Enable");
        resPointsSelectInfo = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Select-Info");
        resPointsSelectTool = Residence.getInstance().getWorldEditTool().getMaterial();
        resPointsMode = ConfigHandler.getConfig("config.yml").getString("Residence.Points.Check.Mode");
        resPointsIgnoreXYZ = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Check.Ignore-XYZ");
        resPointsReturnXYZ = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Check.Return-XYZ");
        resPointsAllAreas = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Check.All-Areas.Enable");
        resPointsIgnoreWithin = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Check.All-Areas.Ignore-Within-Area");
        resPointsDefault = ConfigHandler.getConfig("config.yml").getLong("Residence.Points.Groups.Default.Limit");
        resPointsGroupsConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Residence.Points.Groups");
        for (String title : resPointsGroupsConfig.getKeys(false)) {
            resPointsLimitMap.put(title, ConfigHandler.getConfig("config.yml").getLong("Residence.Points.Groups." + title + ".Limit"));
            resPointsDisplayMap.put(title, ConfigHandler.getConfig("config.yml").getString("Residence.Points.Groups." + title + ".Display"));
        }

        resFlagEdit = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Enable");
        resFlagEditMax = ConfigHandler.getConfig("config.yml").getInt("Residence.Flags-Editor.Max-Edit-Players");
        resFlagTimeOut = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Timeout-Warning");
        resFlagEditUpdate = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Default.Update");
        resFlagAutoCheck = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Auto-Check.Enable");
        resFlagAutoCheckDelay = ConfigHandler.getConfig("config.yml").getLong("Residence.Flags-Editor.Auto-Check.Delay") * 20;
        resFlagEditRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Default.Remove-No-Perms");
        resFlagEditRemovePerm = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Permissions.Remove-No-Perms");

        visitorEnable = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Enable");
        visitorCreateRes = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Create-Residence.Enable");
        visitorCreateResMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Create-Residence.Message");
        visitorInteractBlock = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Enable");
        visitorInteractBlockMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Message");
        visitorInteractBlockUse = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Allow-Use");
        visitorInteractBlockContainer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Allow-Container");
        visitorInteractEntities = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Enable");
        visitorInteractEntitiesMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Message");
        visitorInteractEntitiesNPC = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Allow-NPC");
        visitorDamageEntities = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Enable");
        visitorDamageEntitiesMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Message");
        visitorDamageEntitiesPlayer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Allow-Player");
        visitorDropItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Drop-Items.Enable");
        visitorDropItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Drop-Items.Message");
        visitorPickupItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Pickup-Items.Enable");
        visitorPickupItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Pickup-Items.Message");
        visitorUseItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Enable");
        visitorUseItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Message");
        visitorItemsConsume = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Consume");
        visitorItemsBucket = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Bucket");
        visitorItemsProjectile = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Projectile");
        visitorItemsFishing = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Fishing");
        visitorItemJoin = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-ItemJoin");

    }

    public boolean isPlayerPreventFly() {
        return playerPreventFly;
    }

    public String getPlayerPreventFlyPerm() {
        return playerPreventFlyPerm;
    }

    public boolean isResPreventEnable() {
        return resPreventEnable;
    }

    public boolean isResPreventFlyDisable() {
        return resPreventFlyDisable;
    }

    public boolean isResPreventPotionDamage() {
        return resPreventPotionDamage;
    }

    public boolean isResPreventItemFrame() {
        return resPreventItemFrame;
    }

    public boolean isResPreventPainting() {
        return resPreventPainting;
    }

    public boolean isResPreventArmorStand() {
        return resPreventArmorStand;
    }

    public boolean isResPreventZombieDoor() {
        return resPreventZombieDoor;
    }

    public boolean isResPreventEndermanPickup() {
        return resPreventEndermanPickup;
    }


    public boolean isResPointsEnable() {
        return resPointsEnable;
    }

    public boolean isResPointsSelectInfo() {
        return resPointsSelectInfo;
    }

    public Material getResPointsSelectTool() {
        return resPointsSelectTool;
    }

    public String getResPointsMode() {
        return resPointsMode;
    }

    public boolean isResPointsIgnoreXYZ() {
        return resPointsIgnoreXYZ;
    }

    public boolean isResPointsReturnXYZ() {
        return resPointsReturnXYZ;
    }

    public boolean isResPointsAllAreas() {
        return resPointsAllAreas;
    }

    public boolean isResPointsIgnoreWithin() {
        return resPointsIgnoreWithin;
    }

    public long getResPointsDefault() {
        return resPointsDefault;
    }

    public HashMap<String, Long> getResPointsLimitMap() {
        return resPointsLimitMap;
    }

    public HashMap<String, String> getResPointsDisplayMap() {
        return resPointsDisplayMap;
    }


    public boolean isResFlagEdit() {
        return resFlagEdit;
    }

    public int getResFlagEditMax() {
        return resFlagEditMax;
    }

    public boolean isResFlagTimeOut() {
        return resFlagTimeOut;
    }

    public boolean isResFlagAutoCheck() {
        return resFlagAutoCheck;
    }

    public long getResFlagAutoCheckDelay() {
        return resFlagAutoCheckDelay;
    }

    public boolean isResFlagEditUpdate() {
        return resFlagEditUpdate;
    }

    public boolean isResFlagEditRemove() {
        return resFlagEditRemove;
    }

    public boolean isResFlagEditRemovePerm() {
        return resFlagEditRemovePerm;
    }


    public boolean isVisitorEnable() {
        return visitorEnable;
    }

    public boolean isVisitorCreateRes() {
        return visitorCreateRes;
    }

    public boolean isVisitorCreateResMsg() {
        return visitorCreateResMsg;
    }

    public boolean isVisitorInteractBlock() {
        return visitorInteractBlock;
    }

    public boolean isVisitorInteractEntities() {
        return visitorInteractEntities;
    }

    public boolean isVisitorInteractBlockContainer() {
        return visitorInteractBlockContainer;
    }

    public boolean isVisitorInteractBlockUse() {
        return visitorInteractBlockUse;
    }

    public boolean isVisitorInteractEntitiesNPC() {
        return visitorInteractEntitiesNPC;
    }

    public boolean isVisitorDamageEntities() {
        return visitorDamageEntities;
    }

    public boolean isVisitorDamageEntitiesPlayer() {
        return visitorDamageEntitiesPlayer;
    }

    public boolean isVisitorDropItems() {
        return visitorDropItems;
    }

    public boolean isVisitorItemsBucket() {
        return visitorItemsBucket;
    }

    public boolean isVisitorItemsConsume() {
        return visitorItemsConsume;
    }

    public boolean isVisitorItemsFishing() {
        return visitorItemsFishing;
    }

    public boolean isVisitorItemJoin() {
        return visitorItemJoin;
    }

    public boolean isVisitorItemsProjectile() {
        return visitorItemsProjectile;
    }

    public boolean isVisitorPickupItems() {
        return visitorPickupItems;
    }

    public boolean isVisitorUseItems() {
        return visitorUseItems;
    }

    public boolean isVisitorDamageEntitiesMsg() {
        return visitorDamageEntitiesMsg;
    }

    public boolean isVisitorDropItemsMsg() {
        return visitorDropItemsMsg;
    }

    public boolean isVisitorInteractBlockMsg() {
        return visitorInteractBlockMsg;
    }

    public boolean isVisitorInteractEntitiesMsg() {
        return visitorInteractEntitiesMsg;
    }

    public boolean isVisitorPickupItemsMsg() {
        return visitorPickupItemsMsg;
    }

    public boolean isVisitorUseItemsMsg() {
        return visitorUseItemsMsg;
    }

    public void setResPointsLimitMap(HashMap<String, Long> resPointsLimitMap) {
        this.resPointsLimitMap = resPointsLimitMap;
    }


}