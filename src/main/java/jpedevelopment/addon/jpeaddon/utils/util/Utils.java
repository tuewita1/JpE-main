package jpedevelopment.addon.jpeaddon.utils.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class Utils {
    public static boolean isCollidesEntity(Box b) {
        if (mc.world == null) return false;
        for (Entity entity : mc.world.getEntities()) if (b.intersects(entity.getBoundingBox())) return true;
        return false;
    }

    public static boolean isCollidesEntity(BlockPos b) {
        return isCollidesEntity(new Box(b));
    }
}
