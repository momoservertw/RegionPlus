package tw.momocraft.regionplus.handlers;


public class UtilsHandler {

    private static DependHandler dependence;

    public static void setupFirst(boolean reload) {
        if (!reload)
            dependence = new DependHandler();
    }

    public static DependHandler getDepend() {
        return dependence;
    }


}
