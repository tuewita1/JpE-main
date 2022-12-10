package jpedevelopment.addon.jpeaddon.modules.combat;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import meteordevelopment.meteorclient.events.world.PlaySoundEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class BreakSpeed extends JPEModule {
    private final int[] countsLastSecond = new int[20];
    private int countThisTick;
    private int i;

    public BreakSpeed() {
        super(JPEAddon.combat, "crystal-break-speed", "Measures how many crystals explode per second.");
    }

    @Override
    public void onActivate() {
        countThisTick = 0;

        Arrays.fill(countsLastSecond, 0);
        i = 0;
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        if (event.sound.getId().equals(SoundEvents.ENTITY_GENERIC_EXPLODE.getId())) {
            countThisTick++;
        }
    }

    @EventHandler
    private void onPostTick(TickEvent.Post event) {
        i++;
        if (i >= countsLastSecond.length) i = 0;

        countsLastSecond[i] = countThisTick;
        countThisTick = 0;

        int total = 0;
        for (int j : countsLastSecond) total += j;

    }
}


