package com.github.mantranminh.icarus.annotations;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(value = 2)
public @interface Caching {
    String prefix() default "";

    /**
     * default 900000 ms
     * @return
     */
    long ttl() default 900000;
    int argLimit() default 2;

}
