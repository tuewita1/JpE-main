package jpedevelopment.addon.jpeaddon.modules.Chat;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import jpedevelopment.addon.jpeaddon.utils.ModuleH.ArmorU;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;

public class AlertArmor extends JPEModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> threshold = sgGeneral.add(new DoubleSetting.Builder().name("durability").description("How low an armor piece needs to be to alert you.").defaultValue(2).min(1).sliderMin(1).sliderMax(100).max(100).build());

    public AlertArmor() {
        super(JPEAddon.chat, "alert-armor", "use AutoXp Module!");
    }

    private boolean alertedHelm;
    private boolean alertedChest;
    private boolean alertedLegs;
    private boolean alertedBoots;

    @Override
    public void onActivate() {
        alertedHelm = false;
        alertedChest = false;
        alertedLegs = false;
        alertedBoots = false;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        Iterable<ItemStack> armorPieces = mc.player.getArmorItems();
        for (ItemStack armorPiece : armorPieces){
            if (ArmorU.checkThreshold(armorPiece, threshold.get())) {
                if (ArmorU.isHelm(armorPiece) && !alertedHelm) {
                    warning("Your helmet is low!");
                    alertedHelm = true;
                }
                if (ArmorU.isChest(armorPiece) && !alertedChest) {
                    warning("Your chestplate is low!");
                    alertedChest = true;
                }
                if (ArmorU.isLegs(armorPiece) && !alertedLegs) {
                    warning("Your leggings are low!");
                    alertedLegs = true;
                }
                if (ArmorU.isBoots(armorPiece) && !alertedBoots) {
                    warning("Your boots are low!");
                    alertedBoots = true;
                }
            }
            if (!ArmorU.checkThreshold(armorPiece, threshold.get())) {
                if (ArmorU.isHelm(armorPiece) && alertedHelm) alertedHelm = false;
                if (ArmorU.isChest(armorPiece) && alertedChest) alertedChest = false;
                if (ArmorU.isLegs(armorPiece) && alertedLegs) alertedLegs = false;
                if (ArmorU.isBoots(armorPiece) && alertedBoots) alertedBoots = false;
            }
        }
    }
}

