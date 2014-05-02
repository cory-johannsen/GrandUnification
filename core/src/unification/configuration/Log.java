package unification.configuration;

import javax.inject.Scope;
import java.lang.annotation.*;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: cjohannsen
 * Date: 5/2/14
 * Time: 10:19 AM
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Log {
}

