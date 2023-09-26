package me.redth.autotext.config;

import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;

public class KeyTextEntry {
    public OneKeyBind keyBind = new OneKeyBind(UKeyboard.KEY_NONE);
    public String text = "";

    private transient boolean lastPressed;

    public boolean isPressed() {
        return lastPressed != keyBind.isActive() && (lastPressed = keyBind.isActive());
    }
}
