package com.playernguyen.powreedcore.settings;

import com.playernguyen.powreedcore.configs.PowreedConfigurationAbstract;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PowreedConfigurationSetting extends PowreedConfigurationAbstract<PowreedConfigurationSettingEntry> {


    private static final String FILE_NAME = "plugin.yml";

    public PowreedConfigurationSetting(@NotNull Plugin plugin) throws IOException {
        super(plugin, PowreedConfigurationSettingEntry.values(), FILE_NAME, null);
    }

    @Override
    protected void init() {

    }
}
