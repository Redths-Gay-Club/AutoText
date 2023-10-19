package me.redth.autotext.config

import cc.polyfrost.oneconfig.config.core.OneKeyBind

data class Macro(
    var enabled: Boolean = true,
    var keyBind: OneKeyBind = OneKeyBind(),
    var text: String = "",
)