package com.ucsdextandroid1.snapmap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by rjaylward on 2019-05-04
 *
 * We created this custom annotation which we can use to tell Gson to ignore a specific field when
 * turning a java object into json.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RemoveFromJson {
}
