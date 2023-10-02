package me.redth.autotext.element

import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import me.redth.autotext.AutoText
import me.redth.autotext.config.KeyTextEntry
import me.redth.autotext.config.OptionList

@Suppress("unstableapiusage")
class AddButton : BasicButton(32, SIZE_32, AutoText.PLUS_ICON, ALIGNMENT_CENTER, ColorPalette.PRIMARY) {
    init {
        setClickAction {
            OptionList.list.add(KeyTextEntryOption(KeyTextEntry()))
        }
    }
}