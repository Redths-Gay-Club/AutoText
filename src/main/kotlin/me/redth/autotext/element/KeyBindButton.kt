package me.redth.autotext.element

import cc.polyfrost.oneconfig.config.core.OneKeyBind
import cc.polyfrost.oneconfig.gui.OneConfigGui
import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.internal.assets.SVGs
import cc.polyfrost.oneconfig.libs.universal.UKeyboard
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette

@Suppress("unstableapiusage")
class KeyBindButton(private val keyBind: OneKeyBind) : BasicButton(256, SIZE_32, SVGs.KEYSTROKE, ALIGNMENT_JUSTIFIED, ColorPalette.SECONDARY) {
    init {
        setToggleable(true)
    }

    override fun update(x: Float, y: Float, inputHandler: InputHandler) {
        super.update(x, y, inputHandler)

        if (isToggled) whileListening()

        text = keyBind.display.ifEmpty {
            if (isToggled) "Recording... (ESC to clear)" else "None"
        }
    }

    private fun whileListening() {
        if (isClicked) {
            keyBind.clearKeys()
        } else if (keyBind.releasedAllKey()) {
            isToggled = false
        }
    }

    fun isKeyTyped(keyCode: Int): Boolean {
        if (!isToggled) return false

        if (keyCode == UKeyboard.KEY_ESCAPE) {
            keyBind.clearKeys()
            isToggled = false
        } else {
            keyBind.addKey(keyCode)
        }

        return true
    }

    override fun setToggled(toggled: Boolean) {
        super.setToggled(toggled)
        OneConfigGui.INSTANCE.allowClose = !toggled
    }

    private fun OneKeyBind.releasedAllKey() = size != 0 && !isActive
}