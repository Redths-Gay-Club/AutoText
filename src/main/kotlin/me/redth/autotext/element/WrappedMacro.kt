package me.redth.autotext.element

import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import me.redth.autotext.AutoText
import me.redth.autotext.config.Macro

@Suppress("UnstableAPIUsage")
class WrappedMacro(
    val macro: Macro
) {
    private val removeButton = BasicButton(32, 32, AutoText.MINUS_ICON, BasicButton.ALIGNMENT_CENTER, ColorPalette.PRIMARY_DESTRUCTIVE)
    private val checkbox = MacroCheckbox(macro)
    private val textField = MacroTextField(macro)
    private val keyBindButton = MacroKeyBindButton(macro.keyBind)
    private var lastPressed = false

    init {
        removeButton.setClickAction {
            MacroListOption.willBeRemoved = this
        }
    }

    fun draw(vg: Long, x: Float, y: Float, inputHandler: InputHandler) {
        removeButton.draw(vg, x, y, inputHandler)
        checkbox.draw(vg, x + 58, y, inputHandler)
        textField.draw(vg, x + 96, y, inputHandler)
        keyBindButton.draw(vg, x + 736, y, inputHandler)
    }

    fun keyTyped(key: Char, keyCode: Int) = textField.isKeyTyped(key, keyCode) || keyBindButton.isKeyTyped(keyCode)

    fun hasFocus() = textField.isToggled || keyBindButton.isToggled

    fun firstPressed(): Boolean {
        val nowPressed = macro.keyBind.isActive
        if (lastPressed == nowPressed) return false
        lastPressed = nowPressed
        return nowPressed
    }
}