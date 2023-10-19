package me.redth.autotext.element

import cc.polyfrost.oneconfig.config.core.OneKeyBind
import cc.polyfrost.oneconfig.gui.OneConfigGui
import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.internal.assets.SVGs
import cc.polyfrost.oneconfig.libs.universal.UKeyboard
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette

@Suppress("UnstableAPIUsage")
class MacroKeyBindButton(
    private val keyBind: OneKeyBind
) : BasicButton(256, 32, SVGs.KEYSTROKE, ALIGNMENT_JUSTIFIED, ColorPalette.SECONDARY) {
    private val allKeysReleased get() = keyBind.size != 0 && !keyBind.isActive
//    private val listening get() = isToggled
//
//    private fun stopListening() {
//        isToggled = false
//
//    }

    init {
        setToggleable(true)
    }

    override fun update(x: Float, y: Float, inputHandler: InputHandler) {
        super.update(x, y, inputHandler)

        if (isToggled) whileListening()
        text = getDisplayText()
    }

    override fun setToggled(toggled: Boolean) {
        super.setToggled(toggled)
        OneConfigGui.INSTANCE.allowClose = !toggled
    }

    private fun whileListening() {
        when {
            isClicked -> keyBind.clearKeys()
            allKeysReleased -> isToggled = false
        }
    }

    private fun getDisplayText() = keyBind.display.ifEmpty {
        if (isToggled) "Recording... (ESC to clear)"
        else "None"
    }

    fun isKeyTyped(keyCode: Int): Boolean {
        if (!isToggled) return false

        when (keyCode) {
            UKeyboard.KEY_ESCAPE -> {
                keyBind.clearKeys()
                isToggled = false
            }

            else -> keyBind.addKey(keyCode)
        }

        return true
    }
}