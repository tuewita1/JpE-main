package jpedevelopment.addon.jpeaddon.modules.Chat;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import meteordevelopment.meteorclient.events.entity.EntityAddedEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class PlayerDistance extends JPEModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> sound = sgGeneral.add(new BoolSetting.Builder()
        .name("sound-play")
        .description("play sound and render entity distance")
        .defaultValue(true)
        .build());

    public PlayerDistance() {
        super(JPEAddon.chat, "player-alert", "Alerts you when a player enters your render distance");
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent event) {
        if (!(event.entity instanceof PlayerEntity) || event.entity == mc.player) return;
        BlockPos pos = event.entity.getBlockPos();
        info(event.entity.getEntityName() + " is at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
        if (sound.get())
            mc.world.playSoundFromEntity(mc.player, mc.player, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 2.0F, 1.0F);
    }
}
