package jpedevelopment.addon.jpeaddon.modules.misc;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import meteordevelopment.meteorclient.events.world.CollisionShapeEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.block.Material;
import net.minecraft.util.shape.VoxelShapes;

public class NoFire extends JPEModule {
    public NoFire() {
        super(JPEAddon.misc, "no-fire", "moses in fire");
    }

    @EventHandler
    public void onCollisionShape(CollisionShapeEvent e) {
        if (e.state.getMaterial() == Material.FIRE) {
            e.shape = VoxelShapes.fullCube();
        }
    }
}
