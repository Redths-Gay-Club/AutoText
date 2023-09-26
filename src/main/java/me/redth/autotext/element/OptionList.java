package me.redth.autotext.element;

import cc.polyfrost.oneconfig.config.elements.BasicOption;
import cc.polyfrost.oneconfig.gui.elements.BasicButton;
import cc.polyfrost.oneconfig.gui.elements.IFocusable;
import cc.polyfrost.oneconfig.utils.InputHandler;
import cc.polyfrost.oneconfig.utils.color.ColorPalette;
import me.redth.autotext.AutoText;
import me.redth.autotext.config.ModConfig;
import me.redth.autotext.config.KeyTextEntry;

import java.util.ArrayList;
import java.util.List;

public class OptionList extends BasicOption implements IFocusable {
    private final List<KeyTextEntryOption> list = new ArrayList<>();
    private KeyTextEntryOption nextToRemove;
    private final BasicButton addButton = new BasicButton(32, BasicButton.SIZE_32, AutoText.PLUS_ICON, BasicButton.ALIGNMENT_CENTER, ColorPalette.PRIMARY);

    public OptionList(String name, String description, String category, String subcategory) {
        super(null, null, name, description, category, subcategory, 2);
        addButton.setClickAction(this::newEntry);
        for (KeyTextEntry keyTextEntry : ModConfig.entryList)
            createOption(keyTextEntry);
    }

    @Override
    public int getHeight() {
        return list.size() * (32 + 16) + 32;
    }

    @Override
    public void draw(long vg, int x, int y, InputHandler inputHandler) {
        for (KeyTextEntryOption option : list) {
            option.draw(vg, x, y, inputHandler);
            y += 48;
        }
        addButton.draw(vg, x, y, inputHandler);

        if (nextToRemove != null) { // prevent concurrent exception
            list.remove(nextToRemove);
            ModConfig.entryList.remove(nextToRemove.keyTextEntry);
            nextToRemove = null;
        }
    }

    @Override
    public void keyTyped(char key, int keyCode) {
        for (KeyTextEntryOption option : list) {
            if (option.keyTyped(key, keyCode)) break;
        }
    }

    public void newEntry() {
        KeyTextEntry keyTextEntry = new KeyTextEntry();
        ModConfig.entryList.add(keyTextEntry);
        createOption(keyTextEntry);
    }

    public void createOption(KeyTextEntry keyTextEntry) {
        list.add(new KeyTextEntryOption(keyTextEntry, this));
    }

    public void removeOption(KeyTextEntryOption keyTextEntryOption) {
        nextToRemove = keyTextEntryOption;
    }

    @Override
    public boolean hasFocus() {
        for (KeyTextEntryOption option : list) {
            if (option.hasFocus()) return true;
        }
        return false;
    }
}
