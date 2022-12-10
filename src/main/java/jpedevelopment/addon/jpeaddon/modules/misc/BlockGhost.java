package jpedevelopment.addon.jpeaddon.modules.misc;

import jpedevelopment.addon.jpeaddon.JPEAddon;
import jpedevelopment.addon.jpeaddon.modules.JPEModule;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class BlockGhost extends JPEModule {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> antikick = sgGeneral.add(new BoolSetting.Builder()
        .name("anti-kick")
        .description("Stops you from being kicked")
        .defaultValue(false)
        .build());

    public BlockGhost() {
        super(JPEAddon.misc, "ghost-block-fly", "Volar Ante Bloques Fantasmas");
    }

    BlockPos pos = null;
    BlockState state = null;

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player.getBlockPos().add(0, -1, 0) != pos && pos != null && state != null)
            mc.world.setBlockState(pos, state);

        pos = mc.player.getBlockPos().add(0, -1, 0);
        state = mc.world.getBlockState(pos);

        if (!mc.options.sneakKey.isPressed() && mc.world.getBlockState(pos).getBlock() instanceof AirBlock && pos != null) {
            mc.world.setBlockState(pos, Blocks.BARRIER.getDefaultState());
        }

        if (mc.options.sneakKey.isPressed() && mc.world.getBlockState(pos).getBlock() instanceof AirBlock && pos != null) {
            mc.world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    @Override
    public void onDeactivate() {
        mc.world.setBlockState(pos, state);
        pos = null;
        BlockState state = null;
    }
}

