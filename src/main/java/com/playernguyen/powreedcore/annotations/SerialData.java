package com.playernguyen.powreedcore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
public @interface SerialData {

    /**
     * The key value to be deserialized the data
     *
     * @return key value to be to the data
     */
    String key();

}
