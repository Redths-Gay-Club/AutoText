package me.redth.autotext.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.CustomOption
import cc.polyfrost.oneconfig.config.annotations.Info
import cc.polyfrost.oneconfig.config.core.ConfigUtils
import cc.polyfrost.oneconfig.config.data.InfoType
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.elements.BasicOption
import cc.polyfrost.oneconfig.config.elements.OptionPage
import me.redth.autotext.AutoText
import me.redth.autotext.element.WrappedMacro
import me.redth.autotext.element.MacroListOption
import java.lang.reflect.Field

object ModConfig : Config(Mod(AutoText.NAME, ModType.UTIL_QOL), "${AutoText.MODID}.json") {
    @Info(
        text = "Chat messages are disabled on Hypixel, commands are still allowed",
        type = InfoType.WARNING,
        size = 2
    )
    private var warning = Runnable {  }

    @CustomOption
    private var entries: Array<Macro> = emptyArray()

    override fun getCustomOption(
        field: Field,
        annotation: CustomOption,
        page: OptionPage,
        mod: Mod,
        migrate: Boolean
    ): BasicOption {
        val option = MacroListOption
        ConfigUtils.getSubCategory(page, "General", "").options.add(option)
        return option
    }

    override fun load() {
        super.load()

        MacroListOption.wrappedMacros = entries.mapTo(mutableListOf()) { macro ->
            WrappedMacro(macro)
        }
    }

    override fun save() {
        entries = MacroListOption.wrappedMacros.map { wrapped ->
            wrapped.macro
        }.toTypedArray()

        super.save()
    }
}