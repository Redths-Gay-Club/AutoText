package me.redth.autotext

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.KeyInputEvent
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.renderer.asset.SVG
import me.redth.autotext.config.ModConfig
import me.redth.autotext.config.OptionList
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = AutoText.MODID, name = AutoText.NAME, version = AutoText.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object AutoText {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"
    val PLUS_ICON = SVG("/assets/autotext/icons/plus.svg")
    val MINUS_ICON = SVG("/assets/autotext/icons/minus.svg")
    private val mc = Minecraft.getMinecraft()

    @Mod.EventHandler
    fun onInit(e: FMLInitializationEvent) {
        ModConfig.initialize()
        EventManager.INSTANCE.register(this)
    }

    @Subscribe
    fun onKeyInput(e: KeyInputEvent) {
        for (keyTextEntry in OptionList.list) {
            if (keyTextEntry.justPressed())
                mc.thePlayer.sendChatMessage(keyTextEntry.keyTextEntry.text)
        }
    }
}