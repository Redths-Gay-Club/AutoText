package me.redth.autotext.element

import cc.polyfrost.oneconfig.gui.OneConfigGui
import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.gui.elements.text.TextInputField
import cc.polyfrost.oneconfig.internal.assets.SVGs
import cc.polyfrost.oneconfig.libs.universal.UKeyboard
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import me.redth.autotext.AutoText.MINUS_ICON
import me.redth.autotext.config.KeyTextEntry

@Suppress("unstableapiusage")
class KeyTextEntryOption(val keyTextEntry: KeyTextEntry, private val parent: OptionList) {
    private val textField = TextInputField(640, BasicButton.SIZE_32, "", false, false, SVGs.TEXT_INPUT)
    private val keyBindButton = BasicButton(256, BasicButton.SIZE_32, "", SVGs.KEYSTROKE, null, BasicButton.ALIGNMENT_JUSTIFIED, ColorPalette.SECONDARY)
    private val removeButton = BasicButton(32, BasicButton.SIZE_32, MINUS_ICON, BasicButton.ALIGNMENT_CENTER, ColorPalette.PRIMARY_DESTRUCTIVE)
    private var clicked = false

    init {
        keyBindButton.setToggleable(true)
        removeButton.setClickAction { remove() }
    }

    fun draw(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        drawTextField(vg, x, y, inputHandler)
        drawKeyBindButton(vg, x, y, inputHandler)
        drawRemoveButton(vg, x, y, inputHandler)
    }

    fun keyTyped(key: Char, keyCode: Int): Boolean {
        return textFieldTyped(key, keyCode) || keyBindTyped(key, keyCode)
    }

    private fun drawTextField(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        textField.input = keyTextEntry.text
        textField.draw(vg, x.toFloat(), y.toFloat(), inputHandler)
    }

    private fun drawKeyBindButton(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        val keyBind = keyTextEntry.keyBind
        var text = keyBind.display
        if (keyBindButton.isToggled) {
            if (text == "") text = "Recording... (ESC to clear)"
            if (!clicked) {
                keyBind.clearKeys()
                clicked = true
            } else if (keyBind.size == 0 || keyBind.isActive) {
                OneConfigGui.INSTANCE.allowClose = false
            } else {
                keyBindButton.isToggled = false
                clicked = false
                OneConfigGui.INSTANCE.allowClose = true
            }
        } else if (text == "") text = "None"
        keyBindButton.text = text
        keyBindButton.draw(vg, (x + 672).toFloat(), y.toFloat(), inputHandler)
    }

    private fun drawRemoveButton(vg: Long, x: Int, y: Int, inputHandler: InputHandler) {
        removeButton.draw(vg, (x + 960).toFloat(), y.toFloat(), inputHandler)
    }

    private fun textFieldTyped(key: Char, keyCode: Int): Boolean {
        if (!textField.isToggled) return false
        textField.keyTyped(key, keyCode)
        keyTextEntry.text = textField.input
        return true
    }

    private fun keyBindTyped(key: Char, keyCode: Int): Boolean {
        if (!keyBindButton.isToggled) return false
        if (keyCode == UKeyboard.KEY_ESCAPE) {
            keyTextEntry.keyBind.clearKeys()
            keyBindButton.isToggled = false
            OneConfigGui.INSTANCE.allowClose = true
            clicked = false
        } else keyTextEntry.keyBind.addKey(keyCode)
        return true
    }

    fun hasFocus(): Boolean {
        return textField.isToggled || keyBindButton.isToggled
    }

    private fun remove() {
        parent.removeOption(this)
    }
}