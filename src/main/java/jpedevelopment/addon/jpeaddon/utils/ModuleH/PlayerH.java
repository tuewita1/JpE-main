package jpedevelopment.addon.jpeaddon.utils.ModuleH;

import jpedevelopment.addon.jpeaddon.modules.Chat.PoP;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerH {

    public static int getPops(PlayerEntity p) {
        PoP popCounter = Modules.get().get(PoP.class);
        if (popCounter == null) return 0;
        if (!popCounter.isActive()) return 0;
        if (!popCounter.totemPops.containsKey(p.getUuid())) return 0;
        return popCounter.totemPops.getOrDefault(p.getUuid(), 0);
    }

}

