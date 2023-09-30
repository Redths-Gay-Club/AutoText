package me.redth.autotext.config

import cc.polyfrost.oneconfig.config.annotations.Exclude
import cc.polyfrost.oneconfig.config.core.OneKeyBind
import cc.polyfrost.oneconfig.libs.universal.UKeyboard

class KeyTextEntry {
    var keyBind = OneKeyBind(UKeyboard.KEY_NONE)
    var text = ""

    @Exclude
    private var lastPressed = false

    fun isPressed(): Boolean {
        return lastPressed != keyBind.isActive && keyBind.isActive.also { lastPressed = it }
    }
}