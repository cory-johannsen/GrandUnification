/**
 * OutOfTransactionException.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.connection;

/**
 * OutOfTransactionException
 * Runtime exception used to indicate that an method invocation tagged as requiring
 * a managed connection fails due to a connection lifecycle error.
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
public class ManagedConnectionException extends RuntimeException {

    /**
     * 
     */
    public ManagedConnectionException() {
    }

    /**
     * @param message
     */
    public ManagedConnectionException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ManagedConnectionException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ManagedConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
