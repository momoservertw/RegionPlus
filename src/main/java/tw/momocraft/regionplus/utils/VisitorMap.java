package tw.momocraft.regionplus.utils;

import tw.momocraft.regionplus.utils.locationutils.LocationMap;

import java.util.List;

public class VisitorMap {

    private long priority;

    private boolean resOwn;

    private boolean resCreate;
    private boolean resCreateMsg;
    private boolean interBlock;
    private boolean interBlockUse;
    private boolean interBlockCont;
    private boolean interEnt;
    private boolean interEntNPC;
    private boolean damageEnt;
    private boolean damageEntPlayer;
    private boolean dropItems;
    private boolean useItems;
    private boolean useItemsMsg;
    private boolean pickupItems;
    private boolean itemsConsume;
    private boolean itemsBucket;
    private boolean itemsProjectile;
    private boolean itemsFishing;
    private boolean itemJoin;
    private boolean interBlockMsg;
    private boolean interEntMsg;
    private boolean damageEntMsg;
    private boolean dropItemsMsg;
    private boolean pickupItemsMsg;

    private List<LocationMap> locMaps;

    public long getPriority() {
        return priority;
    }

    public boolean isResOwn() {
        return resOwn;
    }

    public boolean isResCreate() {
        return resCreate;
    }

    public boolean isResCreateMsg() {
        return resCreateMsg;
    }

    public boolean isUseItems() {
        return useItems;
    }

    public boolean isUseItemsMsg() {
        return useItemsMsg;
    }

    public boolean isItemsConsume() {
        return itemsConsume;
    }

    public boolean isItemsBucket() {
        return itemsBucket;
    }

    public boolean isItemsProjectile() {
        return itemsProjectile;
    }

    public boolean isItemsFishing() {
        return itemsFishing;
    }

    public boolean isItemJoin() {
        return itemJoin;
    }

    public boolean isInterEnt() {
        return interEnt;
    }

    public boolean isInterEntNPC() {
        return interEntNPC;
    }

    public boolean isInterBlock() {
        return interBlock;
    }

    public boolean isInterBlockUse() {
        return interBlockUse;
    }

    public boolean isInterBlockCont() {
        return interBlockCont;
    }

    public boolean isDamageEnt() {
        return damageEnt;
    }

    public boolean isDamageEntMsg() {
        return damageEntMsg;
    }

    public boolean isDamageEntPlayer() {
        return damageEntPlayer;
    }

    public boolean isInterBlockMsg() {
        return interBlockMsg;
    }

    public boolean isInterEntMsg() {
        return interEntMsg;
    }

    public boolean isDropItems() {
        return dropItems;
    }

    public boolean isDropItemsMsg() {
        return dropItemsMsg;
    }

    public boolean isPickupItems() {
        return pickupItems;
    }

    public boolean isPickupItemsMsg() {
        return pickupItemsMsg;
    }

    public List<LocationMap> getLocMaps() {
        return locMaps;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }


    public void setValue(String type, String input, boolean value) {
        if (input != null) {
            value = Boolean.parseBoolean(input);
        }
        switch (type) {
            case "damageEnt":
                this.damageEnt = value;
                break;
            case "damageEntMsg":
                this.damageEntMsg = value;
                break;
            case "damageEntPlayer":
                this.damageEntPlayer = value;
                break;
            case "dropItems":
                this.dropItems = value;
                break;
            case "dropItemsMsg":
                this.dropItemsMsg = value;
                break;
            case "interBlock":
                this.interBlock = value;
                break;
            case "interBlockCont":
                this.interBlockCont = value;
                break;
            case "interBlockMsg":
                this.interBlockMsg = value;
                break;
            case "interBlockUse":
                this.interBlockUse = value;
                break;
            case "interEnt":
                this.interEnt = value;
                break;
            case "interEntMsg":
                this.interEntMsg = value;
                break;
            case "interEntNPC":
                this.interEntNPC = value;
                break;
            case "itemsBucket":
                this.itemsBucket = value;
                break;
            case "itemJoin":
                this.itemJoin = value;
                break;
            case "itemsConsume":
                this.itemsConsume = value;
                break;
            case "itemsFishing":
                this.itemsFishing = value;
                break;
            case "itemsProjectile":
                this.itemsProjectile = value;
                break;
            case "pickupItems":
                this.pickupItems = value;
                break;
            case "pickupItemsMsg":
                this.pickupItemsMsg = value;
                break;
            case "resCreate":
                this.resCreate = value;
                break;
            case "resCreateMsg":
                this.resCreateMsg = value;
                break;
            case "resOwn":
                this.resOwn = value;
                break;
            case "useItems":
                this.useItems = value;
                break;
            case "useItemsMsg":
                this.useItemsMsg = value;
                break;

        }
    }

    public void setLocMaps(List<LocationMap> locMaps) {
        this.locMaps = locMaps;
    }
}
