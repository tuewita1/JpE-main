package jpedevelopment.addon.jpeaddon.modules.render;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;

public class NoScreen extends JPEModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Boolean> endScreen = sgGeneral.add(new BoolSetting.Builder()
        .name("end-screen")
        .description("Removes the end screen after finishing the game.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> demoScreen = sgGeneral.add(new BoolSetting.Builder()
        .name("demo-screen")
        .description("Removes the demo screen.")
        .defaultValue(true)
        .build()
    );

    // Constructor

    public NoScreen() {
        super(JPEAddon.renderer, "no-screen", "Removes certain screens in the game.");
    }

    // Getter

    public boolean cancelEndScreen() {
        return this.endScreen.get();
    }

    public boolean cancelDemoScreen() {
        return this.demoScreen.get();
    }
}

