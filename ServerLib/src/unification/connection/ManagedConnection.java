/**
 * ManagedConnection.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.connection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ManagedConnection 
 * Custom method-level annotation that indicates the annotated
 * method requires a JDBC connection that whose lifecycle is managed by the AOP
 * container.
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ManagedConnection {

}
