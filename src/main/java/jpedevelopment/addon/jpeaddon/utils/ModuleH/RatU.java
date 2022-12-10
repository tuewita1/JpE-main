package jpedevelopment.addon.jpeaddon.utils.ModuleH;

import jpedevelopment.addon.jpeaddon.modules.combat.BedPlus;
import jpedevelopment.addon.jpeaddon.modules.Chat.PoP;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.misc.MeteorStarscript;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.starscript.utils.StarscriptError;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RatU {

    private static final Random RANDOM = new Random();
    public static List<String> currentTargets = new ArrayList<>();

    public static void sendAutoEz(String playerName) {
        increaseKC();
        MeteorStarscript.ss.set("killed", playerName);
        PoP popCounter = Modules.get().get(PoP.class);
        if (popCounter.ezScripts.isEmpty()) {
            ChatUtils.warning("Your auto ez message list is empty!");
            return;
        }

        var script = popCounter.ezScripts.get(RANDOM.nextInt(popCounter.ezScripts.size()));

        try {
            StringBuilder stringBuilder = new StringBuilder(MeteorStarscript.ss.run(script).toString());
            if (popCounter.killStr.get()) stringBuilder.append(" | Killstreak: ").append(StS.killStreak);
            if (popCounter.suffix.get() && popCounter.suffixScript != null) stringBuilder.append(MeteorStarscript.ss.run(popCounter.suffixScript).toString());

            String ezMessage = stringBuilder.toString();
            ChatUtils.sendPlayerMsg(ezMessage);
            if (popCounter.pmEz.get()) Wrap.messagePlayer(playerName, StringH.stripName(playerName, ezMessage));
        } catch (StarscriptError error) {
            MeteorStarscript.printChatError(error);
        }
    }

    public static void increaseKC() {
        StS.kills++;
        StS.killStreak++;
    }

    public static void updateTargets() {
        currentTargets.clear();
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(Modules.get().get(BedPlus.class));
        for (Module module : modules) currentTargets.add(module.getInfoString());
    }
}
