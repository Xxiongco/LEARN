package com.panda.spring_boot.my.annotaion;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Hello {

    @AliasFor("zh")
    String title() default "";

    @AliasFor("title")
    String zh() default "";

    String en() default "";

}
