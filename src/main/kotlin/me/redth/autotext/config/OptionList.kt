package me.redth.autotext.config

import cc.polyfrost.oneconfig.config.elements.BasicOption
import cc.polyfrost.oneconfig.gui.elements.IFocusable
import cc.polyfrost.oneconfig.utils.InputHandler
import me.redth.autotext.element.AddButton
import me.redth.autotext.element.KeyTextEntryOption

@Suppress("unstableapiusage")
object OptionList : BasicOption(null, null, "", "", "General", "", 2), IFocusable {
    private val addButton = AddButton()
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
}