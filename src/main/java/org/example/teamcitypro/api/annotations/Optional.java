package org.example.teamcitypro.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 *  Fields with this annotation will not be filled with
 *  random or parametrized manner. Required the manual specification
 */
public @interface Optional {
}
