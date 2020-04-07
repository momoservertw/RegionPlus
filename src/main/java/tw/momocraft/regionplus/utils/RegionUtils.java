package tw.momocraft.regionplus.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import tw.momocraft.regionplus.handlers.PermissionsHandler;

/*
clainmedResidence.getName() => simon04
claimedResidence.getPermissions();
claimedResidence.getOwner();
claimedResidence.getName();

for (String flag : perms.getFlags().keySet()) {
    ServerHandler.sendConsoleMessage(flag + "=" + perms.getFlags().get(flag));
}

perms.getPlayerFlags("Momocraft");
for (String key : permsPlayerFlags.keySet()) {
    ServerHandler.sendConsoleMessage(permsPlayerFlags.get(key).toString());
}

ServerHandler.sendConsoleMessage(perms.listPlayersFlags());
cola001§f[§2mobkilling §2tp §2usef]
Darren_houng§f[§2container §2hook §2shear §2mobkilling §2build §2use §2vehicledestroy §2tp §2leash §2animalkilling§f]
Momocraft§f[§4container §4ignite §2mobkilling §4shear §4build §2use §4vehicledestroy §2tp §4leash §2animalkilling§f]

 */

public class RegionUtils {

    public static boolean bypassBorder(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            String worldName = world.getName().toLowerCase();
            if (LocationAPI.getLocation(location, "Visitor.Border")) {
                return PermissionsHandler.hasPermission(player, "regionplus.bypass.visitor.*") &&
                        PermissionsHandler.hasPermission(player, "regionplus.bypass.visitor." + worldName);
            }
        }
        return true;
    }

    public static boolean isCanUseEntity(String blockType) {
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

    public static boolean isContainer(String blockType) {
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
