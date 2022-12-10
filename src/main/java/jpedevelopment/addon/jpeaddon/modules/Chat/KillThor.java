package jpedevelopment.addon.jpeaddon.modules.Chat;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import jpedevelopment.addon.jpeaddon.utils.entity.PlayerDeathEvent;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.Vec3d;

public class KillThor extends JPEModule {
    public KillThor() {
        super(JPEAddon.chat, "kill-effects", "you are thor.");
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        if (event.isTarget()) {
            Vec3d pos = event.getPlayer().getPos();
            LightningEntity bolt = new LightningEntity(EntityType.LIGHTNING_BOLT, mc.world);
            bolt.setPosition(pos);
            mc.world.addEntity(93, bolt);
        }
    }
}
