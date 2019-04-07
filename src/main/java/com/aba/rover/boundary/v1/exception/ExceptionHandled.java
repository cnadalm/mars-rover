package com.aba.rover.boundary.v1.exception;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 * Handle exceptions thrown without control. Useful to catch unchecked
 * exceptions.
 *
 * @see ExceptionInterceptor
 */
@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface ExceptionHandled {
}
