package jpedevelopment.addon.jpeaddon.modules.combat;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.utils.misc.Vec3;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

import java.util.Arrays;

public class CriticalAura extends JPEModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Object2BooleanMap<EntityType<?>>> targets = sgGeneral.add(new EntityTypeListSetting.Builder()
        .name("targets")
        .description("Targets to attack.")
        .defaultValue(EntityType.PLAYER)
        .onlyAttackable()
        .build()
    );

    private final Setting<Boolean> onJump = sgGeneral.add(new BoolSetting.Builder()
        .name("on-jump")
        .description("Attack on jump.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> fallRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("fall-range")
        .description("Fall range.")
        .defaultValue(0.4)
        .range(0.1, 1)
        .sliderRange(0.1, 1)
        .visible(onJump::get)
        .build()
    );

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("Range.")
        .defaultValue(3.5)
        .range(1, 10)
        .sliderRange(1, 128)
        .build()
    );

    private final Setting<Double> lookRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("look-range")
        .description("Look range.")
        .defaultValue(5)
        .range(1, 10)
        .sliderRange(1, 128)
        .build()
    );

    private final Vec3 vec3d1 = new Vec3();
    public CriticalAura() {
        super(JPEAddon.combat, "CriticalAura", "Legit TriggerBot");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player.isDead() || mc.world == null) return;
        if (onJump.get() && !(mc.player.fallDistance > fallRange.get())) return;
        if ((int) (mc.player.getAttackCooldownProgress(0.0F) * 17.0F) < 16) return;
        for (Entity entity : mc.world.getEntities()) {
            if (Arrays.asList(targets.get().keySet().toArray()).contains(entity.getType())) {
                if (mc.player.distanceTo(entity) <= range.get() && ((LivingEntity)entity).getHealth() > 0 && entity != mc.player) {
                    mc.interactionManager.attackEntity(mc.player, entity);
                    mc.player.swingHand(Hand.MAIN_HAND);
                    return;
                }
            }
        }
    }
}

