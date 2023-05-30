package nsu.fit.tsukanov.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Closure {
    /**
     * Closure name
     *
     * @return the closure Name
     */
    String name() default "";

    boolean hasName() default false;
}