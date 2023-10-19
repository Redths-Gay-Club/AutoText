package me.redth.autotext.element

import cc.polyfrost.oneconfig.config.elements.BasicOption
import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.gui.elements.IFocusable
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import me.redth.autotext.AutoText
import me.redth.autotext.config.Macro

@Suppress("UnstableAPIUsage")
object MacroListOption : BasicOption(null, null, "", "", "General", "", 2), IFocusable {
    private val addButton = BasicButton(32, 32, AutoText.PLUS_ICON, BasicButton.ALIGNMENT_CENTER, ColorPalette.PRIMARY)
    var wrappedMacros: MutableList<WrappedMacro> = ArrayList()
    var willBeRemoved: WrappedMacro? = null

    init {
        addButton.setClickAction {
            wrappedMacros.add(WrappedMacro(Macro()))
        }
    }

    override fun getHeight() = wrappedMacros.size * 48 + 32

    override fun draw(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        var nextY = y

        for (option in wrappedMacros) {
            option.draw(vg, x.toFloat(), nextY.toFloat(), inputHandler)
            nextY += 48
        }

        addButton.draw(vg, x.toFloat(), nextY.toFloat(), inputHandler)

        checkWillBeRemoved()
    }

    private fun checkWillBeRemoved() {
        val macro = willBeRemoved ?: return
        wrappedMacros.remove(macro)
        willBeRemoved = null
    }

    override fun keyTyped(key: Char, keyCode: Int) {
        wrappedMacros.any { it.keyTyped(key, keyCode) }
    }

    override fun hasFocus() = wrappedMacros.any { it.hasFocus() }

}