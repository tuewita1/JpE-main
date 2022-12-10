package jpedevelopment.addon.jpeaddon.modules.misc;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.Chat.BurrowAlert;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import jpedevelopment.addon.jpeaddon.utils.ModuleH.RatU;
import jpedevelopment.addon.jpeaddon.utils.ModuleH.StS;
import jpedevelopment.addon.jpeaddon.utils.ModuleH.Wrap;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.DeathScreen;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AutoSpawn extends JPEModule {
    private final SettingGroup sgRekit = settings.createGroup("Rekit");
    private final SettingGroup sgExcuse = settings.createGroup("AutoExcuse");
    private final SettingGroup sgHS = settings.createGroup("HighScore");

    private final Setting<Boolean> rekit = sgRekit.add(new BoolSetting.Builder().name("rekit").description("Rekit after dying on pvp servers.").defaultValue(false).build());
    private final Setting<String> kitName = sgRekit.add(new StringSetting.Builder().name("kit-name").description("The name of your kit.").defaultValue("default").build());

    private final Setting<Boolean> excuse = sgExcuse.add(new BoolSetting.Builder().name("excuse").description("Send an excuse to global chat after death.").defaultValue(false).build());
    private final Setting<Boolean> randomize = sgExcuse.add(new BoolSetting.Builder().name("randomize").description("Randomizes the excuse message.").defaultValue(false).build());
    private final Setting<List<String>> messages = sgExcuse.add(new StringListSetting.Builder().name("excuse-messages").description("Messages to use for AutoExcuse").defaultValue(Collections.emptyList()).build());

    private final Setting<Boolean> alertHS = sgHS.add(new BoolSetting.Builder().name("alert").description("Alerts you client side when you reach a new highscore.").defaultValue(false).build());
    private final Setting<Boolean> announceHS = sgHS.add(new BoolSetting.Builder().name("announce").description("Announce when you reach a new highscore.").defaultValue(false).build());



    private boolean shouldRekit = false;
    private boolean shouldExcuse = false;
    private boolean shouldHS = false;
    private int excuseWait = 50;
    private int rekitWait = 50;
    private int messageI = 0;

    public AutoSpawn() {
        super(JPEAddon.misc, "auto-respawn", "Automatically respawns after death.");
    }

    @EventHandler
    private void onOpenScreenEvent(OpenScreenEvent event) {
        if (!(event.screen instanceof DeathScreen)) return;
        mc.player.requestRespawn();
        if (rekit.get()) shouldRekit = true;
        if (excuse.get()) shouldExcuse = true;
        StS.deaths++;
        //clear these when we die
        BurrowAlert.burrowedPlayers.clear();
        RatU.currentTargets.clear();
        if (StS.killStreak > StS.highscore) {
            shouldHS = true;
            StS.highscore = StS.killStreak;
        }
        StS.killStreak = 0;
        event.cancel();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (Wrap.isLagging()) return;
        if (shouldRekit && rekitWait <= 1) {
            if (shouldHS) {
                if (alertHS.get()) info("You reached a new highscore of " + StS.highscore + "!");
                if (announceHS.get()) ChatUtils.sendPlayerMsg("I reached a new highscore of " + StS.highscore + " thanks to JpE!");
                shouldHS = false;
            }
            info("Rekitting with kit " + kitName.get());
            ChatUtils.sendPlayerMsg("/kit " + kitName.get());
            shouldRekit = false;
            shouldHS = false;
            rekitWait = 50;
            return;
        } else { rekitWait--; }
        if (shouldExcuse && excuseWait <= 1) {
            ChatUtils.sendPlayerMsg(getExcuseMessage());
            shouldExcuse = false;
            excuseWait = 50;
        } else { excuseWait--; }
    }

    private String getExcuseMessage() {
        String excuseMessage;
        if (messages.get().isEmpty()) {
            error("Your excuse message list is empty!");
            return "Lag";
        } else {
            if (randomize.get()) {
                excuseMessage = messages.get().get(new Random().nextInt(messages.get().size()));
            } else {
                if (messageI >= messages.get().size()) messageI = 0;
                int i = messageI++;
                excuseMessage = messages.get().get(i);
            }
        }
        return excuseMessage;
    }


}


