package tw.momocraft.entityplus.handlers;

import tw.momocraft.coreplus.utils.Dependence;

public class UtilsHandler {

    private static Dependence dependence;

    public static void setup() {
        dependence = new Dependence();
    }

    public static Dependence getDepend() {
        return dependence;
    }


}
