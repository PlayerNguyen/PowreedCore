package com.playernguyen.powreedcore.settings;

import com.playernguyen.powreedcore.configs.PowreedConfigurationEntryInterface;

public enum PowreedConfigurationSettingEntry implements PowreedConfigurationEntryInterface {

    DEVELOPER_MODE("developer-mode", false),

    TEST_MYSQL_HOST("test.mysql.host", "localhost"),
    TEST_MYSQL_PORT("test.mysql.port", "3306"),
    TEST_MYSQL_USERNAME("test.mysql.user", "root"),
    TEST_MYSQL_PASSWORD("test.mysql.password", ""),
    TEST_MYSQL_DATABASE("test.mysql.database", "powreed"),
    TEST_MYSQL_URL_PARAMETER("test.mysql.url-parameter", "useSSL=false"),

    ;


    private final String path;
    private final Object initial;

    PowreedConfigurationSettingEntry(String path, Object initial) {
        this.path = path;
        this.initial = initial;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public Object initial() {
        return initial;
    }
}
