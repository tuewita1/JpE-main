package jpedevelopment.addon.jpeaddon.utils.entity;

public class teleport {
    private static final teleport INSTANCE = new teleport();

    public double x, y, z;

    public static teleport get(double x, double y, double z) {
        INSTANCE.x = x;
        INSTANCE.y = y;
        INSTANCE.z = z;
        return INSTANCE;
    }
}
