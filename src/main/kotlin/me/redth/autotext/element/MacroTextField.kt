package me.redth.autotext.element

import cc.polyfrost.oneconfig.gui.elements.text.TextInputField
import cc.polyfrost.oneconfig.internal.assets.SVGs
import cc.polyfrost.oneconfig.utils.InputHandler
import me.redth.autotext.config.Macro

@Suppress("UnstableAPIUsage")
class MacroTextField(
    private val macro: Macro
) : TextInputField(608, 32, "", false, false, SVGs.TEXT_INPUT) {

    override fun update(x: Float, y: Float, inputHandler: InputHandler) {
        super.update(x, y, inputHandler)
        input = macro.text
    }

    fun isKeyTyped(key: Char, keyCode: Int): Boolean {
        if (!isToggled) return false
        keyTyped(key, keyCode)
        macro.text = input
        return true
    }
}