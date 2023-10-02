package me.redth.autotext.element

import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import me.redth.autotext.AutoText
import me.redth.autotext.config.OptionList

@Suppress("unstableapiusage")
class RemoveButton(private val keyTextEntryOption: KeyTextEntryOption) : BasicButton(32, SIZE_32, AutoText.MINUS_ICON, ALIGNMENT_CENTER, ColorPalette.PRIMARY_DESTRUCTIVE) {
    init {
        setClickAction {
            OptionList.nextToRemove = keyTextEntryOption
        }
    }
}