package com.fastrest.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate method parameters on POJO endpoints the the
 * {@link ServerEndpoint} has been defined with a {@link ServerEndpoint#value()}
 * that uses a URI template.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface EntityParam {
}
