package me.redth.autotext.element

import cc.polyfrost.oneconfig.utils.InputHandler
import me.redth.autotext.config.KeyTextEntry

@Suppress("unstableapiusage")
class KeyTextEntryOption(val keyTextEntry: KeyTextEntry) {
    private val textField = KeyTextField(keyTextEntry)
    private val keyBindButton = KeyBindButton(keyTextEntry.keyBind)
    private val removeButton = RemoveButton(this)
    private var lastPressed = false

    fun draw(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        textField.draw(vg, x.toFloat(), y.toFloat(), inputHandler)
        keyBindButton.draw(vg, (x + 672).toFloat(), y.toFloat(), inputHandler)
        removeButton.draw(vg, (x + 960).toFloat(), y.toFloat(), inputHandler)
    }

    fun keyTyped(key: Char, keyCode: Int) = textField.isKeyTyped(key, keyCode) || keyBindButton.isKeyTyped(keyCode)

    fun hasFocus() = textField.isToggled || keyBindButton.isToggled

    fun justPressed(): Boolean {
        val nowPressed = keyTextEntry.keyBind.isActive

        if (lastPressed == nowPressed) return false

        lastPressed = nowPressed

        return nowPressed
    }

    fun KeyTextEntry.justPressed(): Boolean {
        val nowPressed = keyBind.isActive

        if (lastPressed == nowPressed) return false

        lastPressed = nowPressed

        return nowPressed
    }
}