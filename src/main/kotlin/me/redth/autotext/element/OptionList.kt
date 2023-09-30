package me.redth.autotext.element

import cc.polyfrost.oneconfig.config.elements.BasicOption
import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.gui.elements.IFocusable
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import me.redth.autotext.AutoText
import me.redth.autotext.config.KeyTextEntry
import me.redth.autotext.config.ModConfig

@Suppress("unstableapiusage")
class OptionList(name: String, description: String, category: String, subcategory: String) : BasicOption(null, null, name, description, category, subcategory, 2), IFocusable {
    private val addButton = BasicButton(32, BasicButton.SIZE_32, AutoText.PLUS_ICON, BasicButton.ALIGNMENT_CENTER, ColorPalette.PRIMARY)
    private val list: MutableList<KeyTextEntryOption> = ArrayList()
    private var nextToRemove: KeyTextEntryOption? = null

    init {
        addButton.setClickAction { newEntry() }
        for (keyTextEntry in ModConfig.entryList) createOption(keyTextEntry)
    }

    override fun getHeight(): Int {
        return list.size * 48 + 32
    }

    override fun draw(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        var y2 = y
        for (option in list) {
            option.draw(vg, x, y2, inputHandler)
            y2 += 48
        }

        addButton.draw(vg, x.toFloat(), y2.toFloat(), inputHandler)

        nextToRemove?.let {
            list.remove(it)
            ModConfig.entryList.remove(it.keyTextEntry)
            nextToRemove = null
        }
    }

    override fun keyTyped(key: Char, keyCode: Int) {
        list.any { it.keyTyped(key, keyCode) }
    }

    private fun newEntry() {
        KeyTextEntry().let {
            ModConfig.entryList.add(it)
            createOption(it)
        }
    }

    private fun createOption(keyTextEntry: KeyTextEntry) {
        list.add(KeyTextEntryOption(keyTextEntry, this))
    }

    fun removeOption(keyTextEntryOption: KeyTextEntryOption?) {
        nextToRemove = keyTextEntryOption
    }

    override fun hasFocus(): Boolean {
        return list.any { it.hasFocus() }
    }
}