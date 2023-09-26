package me.redth.autotext.element;

import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.gui.OneConfigGui;
import cc.polyfrost.oneconfig.gui.elements.BasicButton;
import cc.polyfrost.oneconfig.gui.elements.text.TextInputField;
import cc.polyfrost.oneconfig.internal.assets.SVGs;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import cc.polyfrost.oneconfig.utils.InputHandler;
import cc.polyfrost.oneconfig.utils.color.ColorPalette;
import com.google.common.base.Strings;
import me.redth.autotext.AutoText;
import me.redth.autotext.config.KeyTextEntry;

public class KeyTextEntryOption {
    public final KeyTextEntry keyTextEntry;
    private final OptionList parent;
    private final TextInputField textField = new TextInputField(640, BasicButton.SIZE_32, "", false, false, SVGs.TEXT_INPUT);
    private final BasicButton keyBindButton = new BasicButton(256, BasicButton.SIZE_32, "", SVGs.KEYSTROKE, null, BasicButton.ALIGNMENT_JUSTIFIED, ColorPalette.SECONDARY);
    private final BasicButton removeButton = new BasicButton(32, BasicButton.SIZE_32, AutoText.MINUS_ICON, BasicButton.ALIGNMENT_CENTER, ColorPalette.PRIMARY_DESTRUCTIVE);
    private boolean clicked = false;

    public KeyTextEntryOption(KeyTextEntry keyTextEntry, OptionList parent) {
        this.keyTextEntry = keyTextEntry;
        this.parent = parent;
        keyBindButton.setToggleable(true);
        removeButton.setClickAction(this::remove);
    }

    public void draw(long vg, int x, int y, InputHandler inputHandler) {
        drawTextField(vg, x, y, inputHandler);
        drawKeyBindButton(vg, x, y, inputHandler);
        drawRemoveButton(vg, x, y, inputHandler);
    }

    public boolean keyTyped(char key, int keyCode) {
        return textFieldTyped(key, keyCode) || keyBindTyped(key, keyCode);
    }

    public void drawTextField(long vg, int x, int y, InputHandler inputHandler) {
        textField.setInput(Strings.nullToEmpty(keyTextEntry.text));
        textField.draw(vg, x, y, inputHandler);
    }

    public void drawKeyBindButton(long vg, int x, int y, InputHandler inputHandler) {
        OneKeyBind keyBind = keyTextEntry.keyBind;
        String text = keyBind.getDisplay();
        if (keyBindButton.isToggled()) {
            if (text.equals("")) text = "Recording... (ESC to clear)";
            if (!clicked) {
                keyBind.clearKeys();
                clicked = true;
            } else if (keyBind.getSize() == 0 || keyBind.isActive()) {
                OneConfigGui.INSTANCE.allowClose = false;
            } else {
                keyBindButton.setToggled(false);
                clicked = false;
                OneConfigGui.INSTANCE.allowClose = true;
            }
        } else if (text.equals("")) text = "None";
        keyBindButton.setText(text);
        keyBindButton.draw(vg, x + 672, y, inputHandler);
    }

    public void drawRemoveButton(long vg, int x, int y, InputHandler inputHandler) {
        removeButton.draw(vg, x + 960, y, inputHandler);
    }

    public boolean textFieldTyped(char key, int keyCode) {
        if (!textField.isToggled()) return false;
        textField.keyTyped(key, keyCode);
        keyTextEntry.text = textField.getInput();
        return true;
    }

    public boolean keyBindTyped(char key, int keyCode) {
        if (!keyBindButton.isToggled()) return false;
        if (keyCode == UKeyboard.KEY_ESCAPE) {
            keyTextEntry.keyBind.clearKeys();
            keyBindButton.setToggled(false);
            OneConfigGui.INSTANCE.allowClose = true;
            clicked = false;
        } else keyTextEntry.keyBind.addKey(keyCode);
        return true;
    }

    public boolean hasFocus() {
        return textField.isToggled() || keyBindButton.isToggled();
    }

    public void remove() {
        parent.removeOption(this);
    }
}
