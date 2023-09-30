package me.redth.autotext.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.CustomOption
import cc.polyfrost.oneconfig.config.annotations.Exclude
import cc.polyfrost.oneconfig.config.core.ConfigUtils
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.elements.BasicOption
import cc.polyfrost.oneconfig.config.elements.OptionPage
import me.redth.autotext.AutoText
import me.redth.autotext.element.OptionList
import java.lang.reflect.Field

object ModConfig : Config(Mod(AutoText.NAME, ModType.UTIL_QOL), "${AutoText.MODID}.json") {
    @CustomOption
    private var entries = emptyArray<KeyTextEntry>()

    @Exclude
    var entryList: MutableList<KeyTextEntry> = ArrayList()

    init {
        initialize()
    }

    override fun getCustomOption(field: Field, annotation: CustomOption, page: OptionPage, mod: Mod, migrate: Boolean): BasicOption {
        val option = OptionList("entries", "", "General", "")
        ConfigUtils.getSubCategory(page, "General", "").options.add(option)
        return option
    }

    override fun load() {
        super.load()
        entryList = entries.toMutableList()
    }

    override fun save() {
        entries = entryList.toTypedArray()
        super.save()
    }
}