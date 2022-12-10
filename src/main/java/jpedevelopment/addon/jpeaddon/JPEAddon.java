package jpedevelopment.addon.jpeaddon;


import jpedevelopment.addon.jpeaddon.commands.Vclipplus;
import jpedevelopment.addon.jpeaddon.modules.Chat.*;
import jpedevelopment.addon.jpeaddon.modules.combat.*;
import jpedevelopment.addon.jpeaddon.modules.combat.AutoCrystal.AutoCrystal;
import jpedevelopment.addon.jpeaddon.modules.exploits.ItemFrameDupe;
import jpedevelopment.addon.jpeaddon.modules.exploits.ShulkerDupe;
import jpedevelopment.addon.jpeaddon.modules.misc.TradingPlus;
import jpedevelopment.addon.jpeaddon.modules.misc.*;
import jpedevelopment.addon.jpeaddon.modules.render.HoleEsp;
import jpedevelopment.addon.jpeaddon.modules.render.NoScreen;
import jpedevelopment.addon.jpeaddon.modules.render.SkeletonEsp;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JPEAddon extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger(JPEAddon.class);

    public static final String MOD_ID = "jpeaddon";
    public static final String ADDON = "JPE";

    public static final String VERSION = "v.1.0.0";
    public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), MOD_ID);
    public static final File RECORDINGS = new File(FOLDER, "recordings");

    public static final Category combat = new Category("Cpvp+", Items.TNT.getDefaultStack());
    public static final Category exploits = new Category("Dupe", Items.EMERALD.getDefaultStack());
    public static final Category misc = new Category("Util", Items.GOLD_BLOCK.getDefaultStack());
    public static final Category chat = new Category("Chat", Items.BOOK.getDefaultStack());
    public static final Category renderer = new Category("Renderer", Items.PLAYER_HEAD.getDefaultStack());

    @Override
    public void onInitialize() {
        LOG.info("Initializing JPE Addon... Welcome!");
    //Render
        Modules.get().add(new SkeletonEsp());
        Modules.get().add(new HoleEsp());
        Modules.get().add(new NoScreen());
    //Combat
        Modules.get().add(new AutoCrystal());
        Modules.get().add(new CriticalAura());
        Modules.get().add(new MineInstant());
        Modules.get().add(new OneTap());
        Modules.get().add(new BedPlus());
        Modules.get().add(new BreakSpeed());
        Modules.get().add(new PacketMine());
        Modules.get().add(new SurroundV2());
        Modules.get().add(new AnchorPlus());
        Modules.get().add(new SelfTrapV2());
    //Misc
        Modules.get().add(new BlockGhost());
        Modules.get().add(new NoNet());
        Modules.get().add(new NoFire());
        Modules.get().add(new MineSafe());
        Modules.get().add(new EntityUsing());
        Modules.get().add(new AutoLight());
        Modules.get().add(new EntityFlight());
        Modules.get().add(new TradingPlus());
    //Dupe
        Modules.get().add(new ShulkerDupe());
        Modules.get().add(new ItemFrameDupe());
    //Chat
        Modules.get().add(new BurrowAlert());
        Modules.get().add(new PoP());
        Modules.get().add(new AlertArmor());
        Modules.get().add(new PlayerDistance());

    //Commands
        Commands.get().add(new Vclipplus());

}

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(exploits);
        Modules.registerCategory(renderer);
        Modules.registerCategory(combat);
        Modules.registerCategory(misc);
        Modules.registerCategory(chat);
    }

    @Override
    public String getPackage() {
        return "jpedevelopment.addon.jpeaddon";
    }
}
