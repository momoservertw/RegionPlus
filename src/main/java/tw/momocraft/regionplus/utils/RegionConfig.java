package tw.momocraft.regionplus.utils;


import tw.momocraft.regionplus.handlers.ConfigHandler;

public class RegionConfig {


    private boolean resBlockPainting;
    private boolean resBlockItemFrame;
    private boolean resBlockArmorStand;
    private boolean resBlockDoor;
    private boolean resBlockEnderman;
    private String resPointsMode;
    private boolean resPointsIgnoreXYZ;
    private long resPointsDefault;
    private boolean resFlagAutoCheck;
    private long resFlagAutoCheckDelay;
    private boolean resFlagEdit;
    private boolean resFlagDefaultUpdate;
    private boolean resFlagDefaultRemove;
    private boolean resFlagPermissionRemove;
    private boolean visitorEnable;
    private boolean visitorInteractBlock;
    private boolean visitorInteractBlockUse;
    private boolean visitorInteractBlockContainer;
    private boolean visitorInteractEntity;

    public RegionConfig() {
        setUp();
    }

    private void setUp() {
        resBlockPainting = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Block.Painting-Destroy");
        resBlockItemFrame = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Block.Item-Frame-Destroy");
        resBlockArmorStand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Armor-Stand-Destroy");
        resBlockDoor = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Block.Zombie-Door-Destruction");
        resBlockEnderman = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Block.Enderman-Pick-Block");
        resPointsMode = ConfigHandler.getConfig("config.yml").getString("Residence.Points.Check.Mode");
        resPointsIgnoreXYZ = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Check.Ignore-XYZ");
        resPointsDefault = ConfigHandler.getConfig("config.yml").getLong("Residence.Points.Default-Limit");
        resFlagAutoCheck = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Auto-Check.Enable");
        resFlagAutoCheckDelay = ConfigHandler.getConfig("config.yml").getLong("ResidenceFlag-Editor.Auto-Check.Delay") * 20;
        resFlagEdit = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Enable");
        resFlagDefaultUpdate = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Default.Update");
        resFlagDefaultRemove = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Default.Remove-No-Perms");
        resFlagPermissionRemove = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Permission.Remove-No-Perms");
        visitorEnable = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Enable");
        visitorInteractBlock = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Enable");
        visitorInteractBlockUse = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Allow-Use");
        visitorInteractBlockContainer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Allow-Container");
        visitorInteractEntity = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Enable");
        visitorInteractEntity = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Enable");
    }

    public boolean isResBlockItemFrame() {
        return resBlockItemFrame;
    }

    public boolean isResBlockPainting() {
        return resBlockPainting;
    }

    public boolean isResBlockArmorStand() {
        return resBlockArmorStand;
    }

    public boolean isResBlockDoor() {
        return resBlockDoor;
    }

    public String getResPointsMode() {
        return resPointsMode;
    }

    public boolean isResPointsIgnoreXYZ() {
        return resPointsIgnoreXYZ;
    }

    public long getResPointsDefault() {
        return resPointsDefault;
    }

    public boolean isResBlockEnderman() {
        return resBlockEnderman;
    }

    public boolean isResFlagAutoCheck() {
        return resFlagAutoCheck;
    }

    public long getResFlagAutoCheckDelay() {
        return resFlagAutoCheckDelay;
    }

    public boolean isResFlagEdit() {
        return resFlagEdit;
    }

    public boolean isResFlagDefaultUpdate() {
        return resFlagDefaultUpdate;
    }

    public boolean isResFlagDefaultRemove() {
        return resFlagDefaultRemove;
    }

    public boolean isResFlagPermissionRemove() {
        return resFlagPermissionRemove;
    }

    public boolean isVisitorEnable() {
        return visitorEnable;
    }

    public boolean isVisitorInteractBlock() {
        return visitorInteractBlock;
    }

    public boolean isVisitorInteractEntity() {
        return visitorInteractEntity;
    }

    public boolean isVisitorInteractBlockContainer() {
        return visitorInteractBlockContainer;
    }

    public boolean isVisitorInteractBlockUse() {
        return visitorInteractBlockUse;
    }
}