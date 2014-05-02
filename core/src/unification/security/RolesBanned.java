/**
 * RolesBanned.java
 * Created Apr 26, 2013 by chris.johannsen@vendscreen.com
 * @copyright VendScreen 2013
 */
package unification.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cory.a.johannsen@gmail.com
 *         Any entity that has any one of the roles listed
 *         will be disallowed to execute the associated
 *         method call.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RolesBanned {
    String[] value();
}
