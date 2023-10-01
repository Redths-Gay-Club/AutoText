package me.redth.autotext.element

import cc.polyfrost.oneconfig.gui.OneConfigGui
import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.gui.elements.text.TextInputField
import cc.polyfrost.oneconfig.internal.assets.SVGs
import cc.polyfrost.oneconfig.libs.universal.UKeyboard
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import me.redth.autotext.AutoText
import me.redth.autotext.config.KeyTextEntry

@Suppress("unstableapiusage")
class KeyTextEntryOption(val keyTextEntry: KeyTextEntry) {
    private val textField = TextInputField(640, BasicButton.SIZE_32, "", false, false, SVGs.TEXT_INPUT)
    private val keyBindButton = BasicButton(256, BasicButton.SIZE_32, SVGs.KEYSTROKE, BasicButton.ALIGNMENT_JUSTIFIED, ColorPalette.SECONDARY).apply { setToggleable(true) }
    private val removeButton = BasicButton(32, BasicButton.SIZE_32, AutoText.MINUS_ICON, BasicButton.ALIGNMENT_CENTER, ColorPalette.PRIMARY_DESTRUCTIVE).apply { setClickAction { remove() } }
    private var lastPressed = false

    fun draw(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        drawTextField(vg, x, y, inputHandler)
        drawKeyBindButton(vg, x + 672, y, inputHandler)
        drawRemoveButton(vg, x + 960, y, inputHandler)
    }

    fun keyTyped(key: Char, keyCode: Int) = textFieldTyped(key, keyCode) || keyBindTyped(keyCode)

    private fun drawTextField(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        textField.input = keyTextEntry.text
        textField.draw(vg, x.toFloat(), y.toFloat(), inputHandler)
    }

    private fun textFieldTyped(key: Char, keyCode: Int): Boolean = if (textField.isToggled) {
        textField.keyTyped(key, keyCode)
        keyTextEntry.text = textField.input
        true
    } else false

    private fun drawKeyBindButton(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        val keyBind = keyTextEntry.keyBind

        if (keyBindButton.isToggled) { // is listening
            if (keyBindButton.isClicked) {
                keyBind.clearKeys()
                OneConfigGui.INSTANCE.allowClose = false
            } else if (keyBind.size != 0 && !keyBind.isActive) { // released all keys
                keyBindButton.isToggled = false
                OneConfigGui.INSTANCE.allowClose = true
            }
            keyBindButton.text = keyBind.display.ifEmpty { "Recording... (ESC to clear)" }
        } else {
            keyBindButton.text = keyBind.display.ifEmpty { "None" }
        }

        keyBindButton.draw(vg, x.toFloat(), y.toFloat(), inputHandler)
    }

    private fun keyBindTyped(keyCode: Int) = if (keyBindButton.isToggled) {
        if (keyCode == UKeyboard.KEY_ESCAPE) {
            keyTextEntry.keyBind.clearKeys()
            keyBindButton.isToggled = false
            OneConfigGui.INSTANCE.allowClose = true
        } else {
            keyTextEntry.keyBind.addKey(keyCode)
        }
        true
    } else false

    private fun drawRemoveButton(vg: Long, x: Int, y: Int, inputHandler: InputHandler) = removeButton.draw(vg, x.toFloat(), y.toFloat(), inputHandler)

    fun hasFocus() = textField.isToggled || keyBindButton.isToggled

    private fun remove() {
        OptionList.nextToRemove = this
    }

    fun justPressed() = keyTextEntry.keyBind.isActive.let {
        if (lastPressed != it) {
            lastPressed = it
            it
        } else false
    }
}