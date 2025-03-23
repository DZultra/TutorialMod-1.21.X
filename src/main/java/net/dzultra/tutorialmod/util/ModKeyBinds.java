package net.dzultra.tutorialmod.util;

import net.dzultra.tutorialmod.TutorialMod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {

    public static KeyBinding registerKeyBind(String translationKey, InputUtil.Type type, int code, String category) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(translationKey, type, code, category));
    }

    public static void registerModKeyBinds() {
        TutorialMod.LOGGER.info("Registering Mod KeyBinds for " + TutorialMod.MOD_ID);

        KeyBinding KeyboardKeyBind = registerKeyBind("key.tutorialmod.keyboard_key_bind", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "key.tutorialmod.keyboard_keys_category");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyboardKeyBind.wasPressed()) {
                client.player.sendMessage(Text.literal("Key T was pressed!"), false);
            }
        });

    }
}
