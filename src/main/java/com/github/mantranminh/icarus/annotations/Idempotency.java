package com.github.mantranminh.icarus.annotations;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(value = 1)
public @interface Idempotency {
    long ttl() default 900000;

    int kIndex() default 0;
}
