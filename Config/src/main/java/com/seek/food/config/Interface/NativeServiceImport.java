package com.seek.food.config.Interface;

import com.seek.food.config.AutoConfig.NativeServiceSubConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(NativeServiceSubConfig.class)
public @interface NativeServiceImport {
}
