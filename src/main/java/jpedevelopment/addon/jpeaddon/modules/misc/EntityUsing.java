package jpedevelopment.addon.jpeaddon.modules.misc;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

public class EntityUsing extends JPEModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Object2BooleanMap<EntityType<?>>> entities = sgGeneral.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Entities to use item on.")
        .defaultValue(EntityType.SHEEP)
        .onlyAttackable()
        .build()
    );

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("Range.")
        .min(0)
        .defaultValue(4.5)
        .build()
    );

    private final Setting<Hand> hand = sgGeneral.add(new EnumSetting.Builder<Hand>()
        .name("hand")
        .description("The hand to use.")
        .defaultValue(Hand.MAIN_HAND)
        .build()
    );

    private final Setting<Boolean> swingHand = sgGeneral.add(new BoolSetting.Builder()
        .name("swing-hand")
        .description("Swing hand client-side.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignoreBabies = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-babies")
        .description("Ignore baby entities.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> oneTime = sgGeneral.add(new BoolSetting.Builder()
        .name("one-time")
        .description("Use item on every entity only one time.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Sends rotation packets to the server when mining.")
        .defaultValue(true)
        .build()
    );

    public EntityUsing() {
        super(JPEAddon.misc, "entity-using", "Right clicks on entities with item in your hand.");
    }

    private final List<Entity> used = new ArrayList<>();

    @Override
    public void onActivate() {
        used.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof LivingEntity)
                || !(entities.get().containsKey(entity.getType()))
                || mc.player.getMainHandStack().isEmpty()
                || oneTime.get() && used.contains(entity)
                || mc.player.distanceTo(entity) > range.get()
                || ignoreBabies.get() && ((LivingEntity) entity).isBaby()) continue;

            Rotations.rotate(Rotations.getYaw(entity), Rotations.getPitch(entity), -100, null);
            if (rotate.get()) mc.interactionManager.interactEntity(mc.player, entity, hand.get());
            if (swingHand.get()) mc.player.swingHand(hand.get());
            if (oneTime.get()) used.add(entity);

            return;
        }
    }
}
