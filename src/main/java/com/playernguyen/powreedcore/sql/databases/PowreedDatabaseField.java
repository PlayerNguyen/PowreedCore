package com.playernguyen.powreedcore.sql.databases;

import org.jetbrains.annotations.Nullable;

/**
 * Field represent data in SQL. It can be any type of value.
 *
 * @param <T> The data type compatible with the database you are using.
 */
public class PowreedDatabaseField<T extends PowreedDatabaseType> {

    private final String name;
    private final PowreedDatabaseType type;
    private final int size;
    private final boolean autoIncrement;
    private final Object defaultValue;
    private final String customParameter;

    public PowreedDatabaseField(String name,
                                T type,
                                int size,
                                boolean autoIncrement,
                                @Nullable Object defaultValue,
                                @Nullable String customParameter) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.autoIncrement = autoIncrement;
        this.defaultValue = defaultValue;
        if (this.autoIncrement && this.defaultValue != null) {
            throw new IllegalStateException("You cannot put auto-increment and default value together.");
        }
        this.customParameter = customParameter;
    }

    public PowreedDatabaseField(String name, PowreedDatabaseType type, int size, boolean autoIncrement) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.autoIncrement = autoIncrement;
        this.defaultValue = null;
        this.customParameter = null;
    }

    public PowreedDatabaseField(String name,
                                T type,
                                int size,
                                boolean autoIncrement,
                                @Nullable String customParameter) {
        this(name, type, size, autoIncrement, null, customParameter);
    }

    public PowreedDatabaseField(String name, T type, int size) {
        this(name, type, size, false, null, null);
    }

    /**
     * The name of this field
     *
     * @return name of this field
     */
    public String getName() {
        return name;
    }

    /**
     * The size of this field. Default value will be the largest value
     *
     * @return the size of field.
     */
    public int getSize() {
        return size;
    }

    /**
     * The type of that value
     *
     * @return the type which will be declared
     */
    public PowreedDatabaseType getType() {
        return type;
    }

    /**
     * Whether have a auto increment value or not
     *
     * @return the boolean state of auto increment
     */
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * The default value which will be set when the row was initiate without value
     *
     * @return default value which will be set when the row was initiate without value
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * The custom parameter to put into the query
     * @return custom parameter
     */
    public String getCustomParameter() {
        return customParameter;
    }

    /**
     * Generate the query and make it as a String. <br>
     *     The query format is <i>{name} {type}({size}) {auto increment} {default value} {custom value}</i>
     *
     * @return
     */
    public String toQuery() {
        StringBuilder builder = new StringBuilder();

        builder.append(getName()).append(" ");
        builder.append(getType().getDefinition()).append("(").append(getSize()).append(")");
        // Auto increment put
        if (isAutoIncrement()) {
            builder.append(type.getAutoIncrement()).append(" ");
        }
        // put the default value
        if (getDefaultValue() != null) {
            builder.append(String.format("DEFAULT '%s'", getDefaultValue())).append(" ");
        }
        // Custom params
        if (getCustomParameter() != null) {
            builder.append(getCustomParameter()).append(" ");
        }

        return builder.toString().trim();
    }
}
