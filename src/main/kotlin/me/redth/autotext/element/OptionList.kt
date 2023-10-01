package me.redth.autotext.element

import cc.polyfrost.oneconfig.config.elements.BasicOption
import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.gui.elements.IFocusable
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import me.redth.autotext.AutoText
import me.redth.autotext.config.KeyTextEntry

@Suppress("unstableapiusage")
object OptionList : BasicOption(null, null, "", "", "General", "", 2), IFocusable {
    private val addButton = BasicButton(32, BasicButton.SIZE_32, AutoText.PLUS_ICON, BasicButton.ALIGNMENT_CENTER, ColorPalette.PRIMARY).apply { setClickAction { newEntry() } }
    var list: MutableList<KeyTextEntryOption> = ArrayList()
    var nextToRemove: KeyTextEntryOption? = null

    override fun getHeight() = list.size * 48 + 32

    override fun draw(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        var y2 = y

        for (option in list) {
            option.draw(vg, x, y2, inputHandler)
            y2 += 48
        }

        addButton.draw(vg, x.toFloat(), y2.toFloat(), inputHandler)

        nextToRemove?.let {
            list.remove(it)
            nextToRemove = null
        }
    }

    override fun keyTyped(key: Char, keyCode: Int) {
        list.any { it.keyTyped(key, keyCode) }
    }

    override fun hasFocus() = list.any { it.hasFocus() }

    private fun newEntry() = list.add(KeyTextEntryOption(KeyTextEntry()))
}