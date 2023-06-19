package com.github.mantranminh.icarus.annotations;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public @interface RaceCondition {
    long waitTime() default 10000;
    long leaseTime() default 15000;
    int argLimit() default 2;
}
