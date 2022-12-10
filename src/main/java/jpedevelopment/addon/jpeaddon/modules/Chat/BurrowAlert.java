package jpedevelopment.addon.jpeaddon.modules.Chat;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import jpedevelopment.addon.jpeaddon.utils.ModuleH.AutoH;
import jpedevelopment.addon.jpeaddon.utils.ModuleH.Wrap;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class BurrowAlert extends JPEModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder().name("range").description("How far away from you to check for burrowed players.").defaultValue(2).min(0).sliderMax(10).build());

    public BurrowAlert() {
        super(JPEAddon.chat, "burrow-alert", "Alerts you when players are burrowed.");
    }

    private int burrowMsgWait;
    public static List<PlayerEntity> burrowedPlayers = new ArrayList<>();

    @Override
    public void onActivate() {
        burrowMsgWait = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (isValid(player)) {
                burrowedPlayers.add(player);
                warning(player.getEntityName() + " is burrowed!");
            }
            if (burrowedPlayers.contains(player) && !AutoH.isBurrowed(player, true)) {
                burrowedPlayers.remove(player);
                warning(player.getEntityName() + " is no longer burrowed.");
            }
        }
    }

    private boolean isValid(PlayerEntity p) {
        if (p == mc.player) return false;
        return mc.player.distanceTo(p) <= range.get() && !burrowedPlayers.contains(p) && AutoH.isBurrowed(p, true) && !Wrap.isPlayerMoving(p);
    }



}

