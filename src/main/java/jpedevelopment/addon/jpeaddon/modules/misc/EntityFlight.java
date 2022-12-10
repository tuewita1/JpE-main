package jpedevelopment.addon.jpeaddon.modules.misc;

import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import meteordevelopment.meteorclient.events.entity.LivingEntityMoveEvent;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.Vec3d;

public class EntityFlight extends JPEModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .description("Horizontal speed in blocks per second.")
        .defaultValue(10)
        .min(0)
        .sliderMax(50)
        .build()
    );

    private final Setting<Double> verticalSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("vertical-speed")
        .description("Vertical speed in blocks per second.")
        .defaultValue(6)
        .min(0)
        .sliderMax(20)
        .build()
    );

    private final Setting<Double> fallSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("fall-speed")
        .description("How fast you fall in blocks per second.")
        .defaultValue(0.1)
        .min(0)
        .build()
    );

    // Constructor

    public EntityFlight() {
        super(Categories.Movement, "entity-flight", "Allows you to fly with any entity.");
    }

    // Living Entity Move Event

    @EventHandler
    private void onLivingEntityMove(LivingEntityMoveEvent event) {
        if (event.entity.getPrimaryPassenger() == mc.player) {
            event.entity.setYaw(mc.player.getYaw());

            // Horizontal Movement

            Vec3d vel = PlayerUtils.getHorizontalVelocity(speed.get());
            double velX = vel.getX();
            double velY = 0;
            double velZ = vel.getZ();

            // Vertical Movement

            if (mc.options.jumpKey.isPressed()) velY += verticalSpeed.get() / 20;
            if (mc.options.sprintKey.isPressed()) velY -= verticalSpeed.get() / 20;
            else velY -= fallSpeed.get() / 20;

            // Apply Velocity

            ((IVec3d) event.entity.getVelocity()).set(velX, velY, velZ);
        }
    }
}

