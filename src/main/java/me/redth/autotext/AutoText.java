package me.redth.autotext;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.KeyInputEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.renderer.asset.SVG;
import me.redth.autotext.config.ModConfig;
import me.redth.autotext.config.KeyTextEntry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = AutoText.MODID, name = AutoText.NAME, version = AutoText.VERSION)
public class AutoText {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    public static final SVG PLUS_ICON = new SVG("/assets/autotext/icons/plus.svg");
    public static final SVG MINUS_ICON = new SVG("/assets/autotext/icons/minus.svg");

    @Mod.Instance(MODID)
    public static AutoText INSTANCE;

    private static final Minecraft mc = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        new ModConfig();
        EventManager.INSTANCE.register(this);
    }

    @Subscribe
    public void onKeyInput(KeyInputEvent e) {
        for (KeyTextEntry keyTextEntry : ModConfig.entryList) {
            if (!keyTextEntry.isPressed()) continue;
            mc.thePlayer.sendChatMessage(keyTextEntry.text);
        }
    }
}
