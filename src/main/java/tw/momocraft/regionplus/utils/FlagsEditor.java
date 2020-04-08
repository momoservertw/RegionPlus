package tw.momocraft.regionplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.Arrays;
import java.util.List;

public class FlagsEditor {
    private boolean run = false;
    private boolean restart = false;
    private boolean end = false;
    private int maxLimit;
    private int startAt;
    private int last;
    private List<OfflinePlayer> playerList;
    private int playerSize;
    private List<OfflinePlayer> editList;

    public void setUp() {
        run = true;
        restart = true;
        end = false;
        maxLimit = ConfigHandler.getRegionConfig().getRFMaxLimit();
        startAt = 0;
        last = 0;
        playerList = Arrays.asList(Bukkit.getOfflinePlayers());
        playerSize = playerList.size();
        if (playerSize < maxLimit) {
            editList = playerList;
        } else {
            editList = playerList.subList(startAt, maxLimit);
        }
    }

    public void resetUp() {
        last = playerSize - startAt;
        editList = playerList.subList(startAt, last >= maxLimit ? startAt += maxLimit : playerSize);
        if (editList.size() < maxLimit) {
            restart = false;
        }
    }

    public boolean isRun() {
        return run;
    }

    public boolean isRestart() {
        return restart;
    }

    public boolean isEnd() {
        return end;
    }

    public int getPlayerSize() {
        return playerSize;
    }

    public int getLast() {
        return last;
    }

    public List<OfflinePlayer> getEditList() {
        return editList;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
