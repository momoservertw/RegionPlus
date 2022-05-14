package tw.momocraft.regionplus.utils;


import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.*;

public class ConfigPath {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         Message Variables                       //
    //  ============================================== //
    private String msgTitle;
    private String msgHelp;
    private String msgReload;
    private String msgVersion;
    private String msgCmdPoints;
    private String msgCmdPointsOther;
    private String msgCmdPointsLevel;
    private String msgCmdResUpdateFlags;
    private String msgCmdResUpdateMessages;
    private String msgCmdResReturnIgnoreY;

    private String msgPoints;
    private String msgTargetPoints;
    private String msgPointsSelect;
    private String msgSizeChangeFailed;
    private String msgResReturnMoney;

    private String msgVisResCreate;
    private String msgVisResGet;
    private String msgVisResGetTarget;
    private String msgVisItemUse;
    private String msgVisInteractBlock;
    private String msgVisInteractEntity;
    private String msgVisDamageEntity;
    private String msgVisDropItem;
    private String msgVisPickupItem;
    private String msgVisDeathDrop;

    //  ============================================== //
    //         General Variables                       //
    //  ============================================== //

    //  ============================================== //
    //         World-Control Variables                 //
    //  ============================================== //
    private boolean worldPreventExplode;
    private List<String> worldPreventExplodeIgnore;
    private boolean worldPreventDroppedItemExplosion;


    //  ============================================== //
    //         Residence Variables                     //
    //  ============================================== //
    private boolean resPrevent;
    private boolean resPreventPotion;
    private boolean resPreventPainting;
    private boolean resPreventItemFrame;
    private boolean resPreventArmorStand;
    private boolean resPreventEnderCrystal;
    private boolean resPreventZombieDoor;
    private boolean resPreventEndermanPick;
    private boolean resPreventBlockDamage;

    private boolean resSurvivalMechanics;
    private boolean resVehicles;

    private boolean points;
    private boolean pointsSelectInfo;
    private Material pointsSelectTool;
    private boolean pointsLimitedYCalculate;
    private boolean pointsPreventAreasSizeChange;
    private boolean pointsPreventLimitedYSizeChange;
    private boolean resIgnoreY;
    private Map<String, Integer> pointsMap = new HashMap<>();
    private final Map<String, String> pointsDisplayMap = new HashMap<>();

    private boolean resUpdateFlagsBypassCustom;
    private List<String> resUpdateFlagsByPassRes;
    private List<String> resUpdateFlagsByPassResOwners;
    private boolean resUpdateFlagsRemove;
    private List<String> resUpdateFlagsRemoveIgnore;
    private boolean resUpdateFlagsUpdate;
    private List<String> resUpdateFlagsUpdateIgnore;
    private boolean resUpdateFlagsPlayerRemove;
    private List<String> resUpdateFlagsPlayerRemoveIgnore;

    private List<String> resUpdateMsgBypassRes;
    private List<String> resUpdateMsgBypassResOwners;

    //  ============================================== //
    //         Visitor Variables                       //
    //  ============================================== //
    private boolean visitor;
    private boolean visResBypass;
    private boolean visResCreate;
    private boolean visResGet;
    private boolean visUseItem;
    private boolean visUseItemMsg;
    private boolean visItemConsume;
    private boolean visItemBucket;
    private boolean visItemFishing;
    private boolean visItemProjectile;
    private boolean visItemJoin;
    private boolean visInterBlock;
    private boolean visInterBlockMsg;
    private boolean visInterBlockUse;
    private boolean visInterBlockCont;
    private boolean visInterEnt;
    private boolean visInterEntMsg;
    private boolean visInterEntNPC;
    private boolean visDamageEnt;
    private boolean visDamageEntMsg;
    private boolean visDamageEntPlayer;
    private boolean visDropItem;
    private boolean visDropItemMsg;
    private boolean visPickupItem;
    private boolean visPickupItemMsg;
    private boolean visDeathDrop;
    private boolean visDeathDropMsg;
    private List<String> visLocList;


    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setMsg();
        setWorldControl();
        setResidence();
        setVisitor();

        sendSetupMsg();
    }

    private void sendSetupMsg() {
        List<String> list = new ArrayList<>(RegionPlus.getInstance().getDescription().getDepend());
        list.addAll(RegionPlus.getInstance().getDescription().getSoftDepend());
        CorePlusAPI.getMsg().sendHookMsg(ConfigHandler.getPluginPrefix(), "plugins", list);

        String string =
                "climb" + " "
                        + "crawl" + " "
                        + "mobkick" + " "
                        + "roofhang" + " "
                        + "slide" + " "
                        + "swim" + " "
                        + "crawl" + " "
                        + "wallkick";
        CorePlusAPI.getMsg().sendHookMsg(ConfigHandler.getPluginPrefix(), "Residence flags", Arrays.asList(string.split("\\s*")));

    }

    //  ============================================== //
    //         Message Setter                          //
    //  ============================================== //
    private void setMsg() {
        msgTitle = ConfigHandler.getConfig("config.yml").getString("Message.Commands.title");
        msgHelp = ConfigHandler.getConfig("config.yml").getString("Message.Commands.help");
        msgReload = ConfigHandler.getConfig("config.yml").getString("Message.Commands.reload");
        msgVersion = ConfigHandler.getConfig("config.yml").getString("Message.Commands.version");
        msgCmdPoints = ConfigHandler.getConfig("config.yml").getString("Message.Commands.points");
        msgCmdPointsOther = ConfigHandler.getConfig("config.yml").getString("Message.Commands.pointsOther");
        msgCmdPointsLevel = ConfigHandler.getConfig("config.yml").getString("Message.Commands.pointsLevel");
        msgCmdResUpdateFlags = ConfigHandler.getConfig("config.yml").getString("Message.Commands.updateFlags");
        msgCmdResUpdateMessages = ConfigHandler.getConfig("config.yml").getString("Message.Commands.updateMessages");
        msgCmdResReturnIgnoreY = ConfigHandler.getConfig("config.yml").getString("Message.Commands.resReturnIgnoreY");

        msgPoints = ConfigHandler.getConfig("config.yml").getString("Message.points");
        msgTargetPoints = ConfigHandler.getConfig("config.yml").getString("Message.targetPoints");
        msgPointsSelect = ConfigHandler.getConfig("config.yml").getString("Message.pointsSelect");
        msgSizeChangeFailed = ConfigHandler.getConfig("config.yml").getString("Message.sizeChangeFailed");
        msgResReturnMoney = ConfigHandler.getConfig("config.yml").getString("Message.resReturnMoney");

        msgVisResCreate = ConfigHandler.getConfig("config.yml").getString("Message.visitorResidenceCreate");
        msgVisResGet = ConfigHandler.getConfig("config.yml").getString("Message.visitorResidenceGet");
        msgVisResGetTarget = ConfigHandler.getConfig("config.yml").getString("Message.visitorResidenceGetTarget");
        msgVisItemUse = ConfigHandler.getConfig("config.yml").getString("Message.visitorUseItem");
        msgVisInteractBlock = ConfigHandler.getConfig("config.yml").getString("Message.visitorInteractBlock");
        msgVisInteractEntity = ConfigHandler.getConfig("config.yml").getString("Message.visitorInteractEntity");
        msgVisDamageEntity = ConfigHandler.getConfig("config.yml").getString("Message.visitorDamageEntity");
        msgVisDropItem = ConfigHandler.getConfig("config.yml").getString("Message.visitorDropItem");
        msgVisPickupItem = ConfigHandler.getConfig("config.yml").getString("Message.visitorPickupItem");
        msgVisDeathDrop = ConfigHandler.getConfig("config.yml").getString("Message.visitorDeathDrop");
    }

    //  ============================================== //
    //         World Setter                            //
    //  ============================================== //
    private void setWorldControl() {
        if (!ConfigHandler.getConfig("config.yml").getBoolean("World-Control.Enable"))
            return;
        if (ConfigHandler.getConfig("config.yml").getBoolean("World.Prevent.Enable")) {
            worldPreventExplode = ConfigHandler.getConfig("config.yml").getBoolean("World.Prevent.Block-Damage");
            worldPreventExplodeIgnore = ConfigHandler.getConfig("config.yml").getStringList("World.Prevent.Block-Damage.Ignore-List");
            worldPreventDroppedItemExplosion = ConfigHandler.getConfig("config.yml").getBoolean("World.Prevent.Dropped-Item-Damage.Explosion");
        }
    }

    //  ============================================== //
    //         Residence Setter                        //
    //  ============================================== //
    private void setResidence() {
        if (!CorePlusAPI.getDepend().ResidenceEnabled())
            return;
        if (!ConfigHandler.getConfig("config.yml").getBoolean("Residence.Enable") ||
                !CorePlusAPI.getDepend().ResidenceEnabled())
            return;
        resIgnoreY = Residence.getInstance().getConfigManager().isSelectionIgnoreY();
        resSurvivalMechanics = ConfigHandler.getConfig("config.yml").getBoolean("Residence.SurvivalMechanics.Enable");
        resVehicles = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Vehicles.Enable");

        resPrevent = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enable");
        if (resPrevent) {
            resPreventPotion = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Potion-Damage");
            resPreventZombieDoor = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent. Zombie-Door-Destruction");
            resPreventEndermanPick = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enderman-Pickup-Block");
            resPreventPainting = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Painting-Destroy");
            resPreventItemFrame = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Item-Frame-Destroy");
            resPreventArmorStand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Armor-Stand-Destroy");
            resPreventEnderCrystal = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Ender-Crystal-Destroy");
            resPreventBlockDamage = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Block-Damage");
        }

        points = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Enable");
        if (points) {
            pointsSelectInfo = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Settings.Select-Info");
            pointsSelectTool = CorePlusAPI.getPlayer().getResSelectionTool();
            pointsLimitedYCalculate = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Settings.Limited-Y-Calculate");
            pointsPreventAreasSizeChange = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Settings.Prevent.Multiple-Areas-Size-Change");
            pointsPreventLimitedYSizeChange = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Settings.Prevent.Limited-Y-Size-Change");
            ConfigurationSection resPointsGroupsConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Residence.Points.Groups");
            if (resPointsGroupsConfig != null) {
                for (String group : resPointsGroupsConfig.getKeys(false)) {
                    pointsDisplayMap.put(group.toLowerCase(), ConfigHandler.getConfig("config.yml").getString("Residence.Points.Groups." + group + ".Display", group));
                    pointsMap.put(group.toLowerCase(), ConfigHandler.getConfig("config.yml").getInt("Residence.Points.Groups." + group + ".Limit"));
                }
                pointsMap = CorePlusAPI.getUtils().sortByValue(pointsMap);
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("Residence.Update-Flags.Enable")) {
            resUpdateFlagsBypassCustom = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Update-Flags.Settings.Bypass.Missing-Custom-Flags");
            resUpdateFlagsByPassRes = ConfigHandler.getConfig("config.yml").getStringList("Residence.Update-Flags.Settings.Bypass.Residence-List");
            resUpdateFlagsByPassResOwners = ConfigHandler.getConfig("config.yml").getStringList("Residence.Update-Flags.Settings.Bypass.Residence-Owners");
            resUpdateFlagsRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Update-Flags.Environment.Remove.Enable");
            resUpdateFlagsRemoveIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Update-Flags.Environment.Remove.Ignore-List");
            resUpdateFlagsUpdate = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Update-Flags.Environment.Update.Enable");
            resUpdateFlagsUpdateIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Update-Flags.Environment.Update.Ignore-List");
            resUpdateFlagsPlayerRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Update-Flags.Player.Remove.Enable");
            resUpdateFlagsPlayerRemoveIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Update-Flags.Player.Remove.Ignore-List");
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("Residence.Update-Messages.Enable")) {
            resUpdateMsgBypassRes = ConfigHandler.getConfig("config.yml").getStringList("Residence.Update-Messages.Settings.Bypass.Residence-List");
            resUpdateMsgBypassResOwners = ConfigHandler.getConfig("config.yml").getStringList("Residence.Update-Messages.Settings.Bypass.Residence-Owners");
        }
    }

    //  ============================================== //
    //         Visitor Setter                          //
    //  ============================================== //
    private void setVisitor() {
        visitor = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Enable");
        if (!visitor)
            return;
        if (ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Enable")) {
            visResBypass = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Bypass");
            visResCreate = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Allow-Create");
            visResGet = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Allow-Get");
        }
        visUseItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Enable");
        visUseItemMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Message");
        visItemConsume = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Consume");
        visItemBucket = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Bucket");
        visItemProjectile = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Projectile");
        visItemFishing = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Fishing");
        visItemJoin = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-ItemJoin");

        visInterBlock = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Enable");
        visInterBlockMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Message");
        visInterBlockUse = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Allow-Use");
        visInterBlockCont = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Allow-Container");

        visInterEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Entity.Enable");
        visInterEntMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Entity.Message");
        visInterEntNPC = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Entity.Allow-NPC");

        visDamageEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Damage-Entity.Enable");
        visDamageEntMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Damage-Entity.Message");
        visDamageEntPlayer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Damage-Entity.Allow-Player");

        visDropItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Drop-Item.Enable");
        visDropItemMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Drop-Item.Message");
        visPickupItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Pickup-Item.Enable");
        visPickupItemMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Pickup-Item.Message");
        visDeathDrop = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Death-Drop.Enable");
        visDeathDropMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Death-Drop.Message");

        visLocList = ConfigHandler.getConfig("config.yml").getStringList("Visitor.Location");
    }

    //  ============================================== //
    //         Message Getter                          //
    //  ============================================== //
    public String getMsgTitle() {
        return msgTitle;
    }

    public String getMsgHelp() {
        return msgHelp;
    }

    public String getMsgReload() {
        return msgReload;
    }

    public String getMsgVersion() {
        return msgVersion;
    }

    public String getMsgCmdPoints() {
        return msgCmdPoints;
    }

    public String getMsgCmdPointsOther() {
        return msgCmdPointsOther;
    }

    public String getMsgCmdPointsLevel() {
        return msgCmdPointsLevel;
    }

    public String getMsgCmdResUpdateFlags() {
        return msgCmdResUpdateFlags;
    }

    public String getMsgCmdResUpdateMessages() {
        return msgCmdResUpdateMessages;
    }

    public String getMsgCmdResReturnIgnoreY() {
        return msgCmdResReturnIgnoreY;
    }


    public String getMsgPoints() {
        return msgPoints;
    }

    public String getMsgTargetPoints() {
        return msgTargetPoints;
    }

    public String getMsgPointsSelect() {
        return msgPointsSelect;
    }

    public String getMsgSizeChangeFailed() {
        return msgSizeChangeFailed;
    }

    public String getMsgResReturnMoney() {
        return msgResReturnMoney;
    }

    public String getMsgVisResCreate() {
        return msgVisResCreate;
    }

    public String getMsgVisResGet() {
        return msgVisResGet;
    }

    public String getMsgVisResGetTarget() {
        return msgVisResGetTarget;
    }

    public String getMsgVisItemUse() {
        return msgVisItemUse;
    }

    public String getMsgVisInteractBlock() {
        return msgVisInteractBlock;
    }

    public String getMsgVisInteractEntity() {
        return msgVisInteractEntity;
    }

    public String getMsgVisDamageEntity() {
        return msgVisDamageEntity;
    }

    public String getMsgVisDropItem() {
        return msgVisDropItem;
    }

    public String getMsgVisPickupItem() {
        return msgVisPickupItem;
    }

    public String getMsgVisDeathDrop() {
        return msgVisDeathDrop;
    }

    //  ============================================== //
    //         General Getter                          //
    //  ============================================== //


    //  ============================================== //
    //         World Getter                            //
    //  ============================================== //
    public boolean isWorldPreventExplode() {
        return worldPreventExplode;
    }

    public List<String> getWorldPreventExplodeIgnore() {
        return worldPreventExplodeIgnore;
    }

    public boolean isWorldPreventDroppedItemExplosion() {
        return worldPreventDroppedItemExplosion;
    }

    //  ============================================== //
    //         Residence Getter                        //
    //  ============================================== //

    public boolean isResPrevent() {
        return resPrevent;
    }

    public boolean isResPreventPotion() {
        return resPreventPotion;
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

    public boolean isResPreventEndermanPick() {
        return resPreventEndermanPick;
    }

    public boolean isResPreventEnderCrystal() {
        return resPreventEnderCrystal;
    }

    public boolean isResPreventBlockDamage() {
        return resPreventBlockDamage;
    }


    public boolean isResSurvivalMechanics() {
        return resSurvivalMechanics;
    }

    public boolean isResVehicles() {
        return resVehicles;
    }


    public boolean isPoints() {
        return points;
    }

    public boolean isPointsSelectInfo() {
        return pointsSelectInfo;
    }

    public Material getPointsSelectTool() {
        return pointsSelectTool;
    }

    public boolean isPointsLimitedYCalculate() {
        return pointsLimitedYCalculate;
    }

    public boolean isPointsPreventAreasSizeChange() {
        return pointsPreventAreasSizeChange;
    }

    public boolean isPointsPreventLimitedYSizeChange() {
        return pointsPreventLimitedYSizeChange;
    }

    public boolean isResIgnoreY() {
        return resIgnoreY;
    }

    public Map<String, Integer> getPointsMap() {
        return pointsMap;
    }

    public Map<String, String> getPointsDisplayMap() {
        return pointsDisplayMap;
    }


    public boolean isresUpdateFlagsBypassCustom() {
        return resUpdateFlagsBypassCustom;
    }

    public List<String> getresUpdateFlagsByPassRes() {
        return resUpdateFlagsByPassRes;
    }

    public List<String> getresUpdateFlagsByPassResOwners() {
        return resUpdateFlagsByPassResOwners;
    }

    public boolean isresUpdateFlagsUpdate() {
        return resUpdateFlagsUpdate;
    }

    public List<String> getresUpdateFlagsUpdateIgnore() {
        return resUpdateFlagsUpdateIgnore;
    }

    public boolean isresUpdateFlagsRemove() {
        return resUpdateFlagsRemove;
    }

    public List<String> getresUpdateFlagsRemoveIgnore() {
        return resUpdateFlagsRemoveIgnore;
    }

    public List<String> getresUpdateFlagsPlayerRemoveIgnore() {
        return resUpdateFlagsPlayerRemoveIgnore;
    }

    public boolean isresUpdateFlagsPlayerRemove() {
        return resUpdateFlagsPlayerRemove;
    }

    public List<String> getResUpdateMsgBypassRes() {
        return resUpdateMsgBypassRes;
    }

    public List<String> getResUpdateMsgBypassResOwners() {
        return resUpdateMsgBypassResOwners;
    }


    //  ============================================== //
    //         Visitor Getter                          //
    //  ============================================== //
    public boolean isVisitor() {
        return visitor;
    }

    public boolean isVisResBypass() {
        return visResBypass;
    }

    public boolean isVisResCreate() {
        return visResCreate;
    }

    public boolean isVisResGet() {
        return visResGet;
    }

    public boolean isVisUseItem() {
        return visUseItem;
    }

    public boolean isVisItemConsume() {
        return visItemConsume;
    }

    public boolean isVisItemBucket() {
        return visItemBucket;
    }

    public boolean isVisItemFishing() {
        return visItemFishing;
    }

    public boolean isVisItemProjectile() {
        return visItemProjectile;
    }

    public boolean isVisItemJoin() {
        return visItemJoin;
    }

    public boolean isVisInterBlock() {
        return visInterBlock;
    }

    public boolean isVisInterBlockUse() {
        return visInterBlockUse;
    }

    public boolean isVisInterBlockCont() {
        return visInterBlockCont;
    }

    public boolean isVisInterEnt() {
        return visInterEnt;
    }

    public boolean isVisInterEntNPC() {
        return visInterEntNPC;
    }

    public boolean isVisDamageEnt() {
        return visDamageEnt;
    }

    public boolean isVisDamageEntPlayer() {
        return visDamageEntPlayer;
    }

    public boolean isVisDropItem() {
        return visDropItem;
    }

    public boolean isVisPickupItem() {
        return visPickupItem;
    }

    public boolean isVisPickupItemMsg() {
        return visPickupItemMsg;
    }


    public boolean isVisDeathDrop() {
        return visDeathDrop;
    }

    public boolean isVisUseItemMsg() {
        return visUseItemMsg;
    }

    public boolean isVisInterBlockMsg() {
        return visInterBlockMsg;
    }

    public boolean isVisInterEntMsg() {
        return visInterEntMsg;
    }

    public boolean isVisDamageEntMsg() {
        return visDamageEntMsg;
    }

    public boolean isVisDropItemMsg() {
        return visDropItemMsg;
    }

    public boolean isVisDeathDropMsg() {
        return visDeathDropMsg;
    }

    public List<String> getVisLocList() {
        return visLocList;
    }
}