package me.redth.autotext.config

import cc.polyfrost.oneconfig.config.core.OneKeyBind
import cc.polyfrost.oneconfig.libs.universal.UKeyboard

data class KeyTextEntry(var keyBind: OneKeyBind = OneKeyBind(UKeyboard.KEY_NONE), var text: String = "")