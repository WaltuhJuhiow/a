package com.autotap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class AutoTapMod implements ClientModInitializer {

    public static boolean enabled   = false;
    public static boolean wTapMode  = true;
    public static int tapTimer = 0;

    private static final int WTAP_RELEASE_TICKS = 1;
    private static final int STAP_PRESS_TICKS   = 1;

    private static KeyBinding toggleKey;
    private static KeyBinding switchModeKey;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category category = new KeyBinding.Category(
                Identifier.of("autotap", "custom_category")
        );

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autotap.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                category
        ));

        switchModeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autotap.switchmode",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private void onClientTick(MinecraftClient client) {
        if (client.player == null) return;

        while (toggleKey.wasPressed()) {
            String state = enabled ? "§aON" : "§cOFF";
            String mode  = wTapMode ? "§eW-Tap" : "§eS-Tap";
            client.player.sendMessage(Text.literal("§6[AutoTap] §r" + mode + " " + state), true);
        }

        while (switchModeKey.wasPressed()) {
            tapTimer = 0;
            String mode = wTapMode ? "§eW-Tap" : "§eS-Tap";
            client.player.sendMessage(Text.literal("§6[AutoTap] §rMode switched to " + mode), true);
        }

        if (tapTimer > 0) tapTimer--;
    }

    public static void triggerTap() {
        tapTimer = wTapMode ? WTAP_RELEASE_TICKS : STAP_PRESS_TICKS;
    }
}
