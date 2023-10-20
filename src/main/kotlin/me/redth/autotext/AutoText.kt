package me.redth.autotext

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.KeyInputEvent
import cc.polyfrost.oneconfig.gui.OneConfigGui
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.UChat
import cc.polyfrost.oneconfig.renderer.asset.SVG
import cc.polyfrost.oneconfig.utils.Notifications
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils
import me.redth.autotext.config.ModConfig
import me.redth.autotext.element.MacroListOption
import me.redth.autotext.element.WrappedMacro
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = AutoText.MODID, name = AutoText.NAME, version = AutoText.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object AutoText {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"
    val PLUS_ICON = SVG("/assets/autotext/icons/plus.svg")
    val MINUS_ICON = SVG("/assets/autotext/icons/minus.svg")

    @Mod.EventHandler
    fun onInit(e: FMLInitializationEvent) {
        ModConfig.initialize()
        EventManager.INSTANCE.register(this)
    }

    @Subscribe
    fun onKeyInput(event: KeyInputEvent) {
        for (wrapped in MacroListOption.wrappedMacros) {
            wrapped.onKeyInput()
        }
    }

    private fun WrappedMacro.onKeyInput() {
        if (!macro.enabled) return
        if (!firstPressed()) return
        val text = macro.text
        val isCommand = text.startsWith("/")

        if (HypixelUtils.INSTANCE.isHypixel && !isCommand) {
            Notifications.INSTANCE.send("AutoText", "Auto chat messages are disabled on Hypixel")
            return
        }

        UChat.say(text)
    }
}