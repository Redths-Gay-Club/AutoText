package me.redth.autotext.element

import cc.polyfrost.oneconfig.gui.elements.text.TextInputField
import cc.polyfrost.oneconfig.internal.assets.SVGs
import cc.polyfrost.oneconfig.utils.InputHandler
import me.redth.autotext.config.KeyTextEntry

@Suppress("unstableapiusage")
class KeyTextField(private val keyTextEntry: KeyTextEntry) : TextInputField(640, 32, "", false, false, SVGs.TEXT_INPUT) {

    override fun update(x: Float, y: Float, inputHandler: InputHandler) {
        super.update(x, y, inputHandler)
        input = keyTextEntry.text
    }

    fun isKeyTyped(key: Char, keyCode: Int): Boolean {
        if (!isToggled) return false

        keyTyped(key, keyCode)
        keyTextEntry.text = input

        return true
    }
}