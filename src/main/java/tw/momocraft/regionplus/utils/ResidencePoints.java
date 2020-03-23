package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResidencePoints {
    public static long getPointsLimit(Player player) {
        List<Long> sizeList = new ArrayList<>();
        String permission;
        for (PermissionAttachmentInfo pa : player.getEffectivePermissions()) {
            permission = pa.getPermission();
            if (pa.getPermission().startsWith("regionplus..points.limit.")) {
                sizeList.add(Long.valueOf(permission.replaceFirst("regionplus.points.limit.", "")));
            }
        }
        if (sizeList.isEmpty()) {
            return ConfigHandler.getRegionConfig().getResPointsDefault();
        }
        if (Collections.max(sizeList) == null) {
            return ConfigHandler.getRegionConfig().getResPointsDefault();
        }
        return Collections.max(sizeList);
    }

    public static long getPointsUsed(Player player) {
        if (player != null) {
            List<ClaimedResidence> resList = ResidenceApi.getPlayerManager().getResidencePlayer(player.getName()).getResList();
            long pointsUsed = 0;
            for (ClaimedResidence res : resList) {
                pointsUsed += res.getXZSize();
            }
            return pointsUsed;
        }
        return 0;
    }

    public static long getPointsRemainder(Player player) {
        if (player != null) {
            long pointsLimit = getPointsLimit(player);
            long pointsUsed = getPointsUsed(player);
            return pointsLimit - pointsUsed;
        }
        return 0;
    }
}
