package me.redth.autotext.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.CustomOption
import cc.polyfrost.oneconfig.config.core.ConfigUtils
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.elements.OptionPage
import me.redth.autotext.AutoText
import me.redth.autotext.element.KeyTextEntryOption
import java.lang.reflect.Field

object ModConfig : Config(Mod(AutoText.NAME, ModType.UTIL_QOL), "${AutoText.MODID}.json") {
    @CustomOption
    private var entries = emptyArray<KeyTextEntry>()

    override fun getCustomOption(field: Field, annotation: CustomOption, page: OptionPage, mod: Mod, migrate: Boolean) = OptionList.also {
        ConfigUtils.getSubCategory(page, "General", "").options.add(it)
    }

    override fun load() {
        super.load()
        OptionList.list = entries.map { KeyTextEntryOption(it) }.toMutableList()
    }

    override fun save() {
        entries = OptionList.list.map { it.keyTextEntry }.toTypedArray()
        super.save()
    }
}