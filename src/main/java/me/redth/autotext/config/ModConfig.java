package me.redth.autotext.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.CustomOption;
import cc.polyfrost.oneconfig.config.core.ConfigUtils;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.elements.BasicOption;
import cc.polyfrost.oneconfig.config.elements.OptionPage;
import com.google.common.collect.Lists;
import me.redth.autotext.AutoText;
import me.redth.autotext.element.OptionList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModConfig extends Config {
    @CustomOption
    protected static KeyTextEntry[] entries = new KeyTextEntry[0];

    public static transient List<KeyTextEntry> entryList = new ArrayList<>();

    public ModConfig() {
        super(new Mod(AutoText.NAME, ModType.UTIL_QOL), AutoText.MODID + ".json");
        initialize();
    }

    @Override
    protected BasicOption getCustomOption(Field field, CustomOption annotation, OptionPage page, Mod mod, boolean migrate) {
        OptionList option = new OptionList("entries", "", "General", "");
        ConfigUtils.getSubCategory(page, "General", "").options.add(option);
        return option;
    }

    @Override
    public void load() {
        super.load();
        entryList = Lists.newArrayList(entries);
    }

    @Override
    public void save() {
        entries = entryList.toArray(new KeyTextEntry[0]);
        super.save();
    }
}

