/**
 * ConnectionInterceptor.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.connection;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * ConnectionInterceptor Custom method interceptor used to manage the lifecycle
 * of JDBC connections.
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public class ManagedConnectionInterceptor implements MethodInterceptor,
        Provider<Connection> {
    // Normally, field injection is discouraged. However, inside an interceptor,
    // you can't use constructor injection, so field injection must be used
    // instead.
    @Inject
    public DataSource mDataSource;

    @Inject
    public Logger mLogger;

    private ThreadLocal<Connection> mThreadLocalConnection;

    /**
     * 
     */
    public ManagedConnectionInterceptor() {
        // mLogger.log(Level.INFO,
        // "Instantiating ManagedConnectionInterceptor.");
        mThreadLocalConnection = new ThreadLocal<Connection>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
     * .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        mLogger.log(Level.INFO, "Intercepting " + methodInvocation.getMethod()
                + " for connection management.");
        Connection connection = mThreadLocalConnection.get();

        // the thread local connection can only be set by the interceptor.
        // if it is not null the interceptor was already invoked. It
        // is safe to continue using the connection, so the invocation
        // can proceed.
        if (connection != null) {
            mLogger.log(Level.INFO,
                    "Connection is not-null.  Proceding with embedded invocation.");
            return methodInvocation.proceed();
        }

        // The thread local connection is null, the connection must be obtained
        // from the datasource and initialized. The invocation can then proceed,
        // and after it finished the connection must be appropriately closed.
        // if we fail to get the connection, no problem, the target method will
        // not get invoked at all
        mLogger.log(Level.INFO,
                "Connection is null.  Obtaining new connection from data source...");
        connection = mDataSource.getConnection();

        // force auto commit to false
        connection.setAutoCommit(false);

        // set Thread Local connection
        mThreadLocalConnection.set(connection);

        // Invoke the method. If the invocation fails, roll back the
        // transaction. Otherwise, commit it. Regardless of the
        // invocation outcome, close the connection.
        try {
            mLogger.log(Level.INFO,
                    "Connection is configured, proceding with invocation...");
            Object returnValue = methodInvocation.proceed();
            mLogger.log(Level.INFO,
                    "Invocation complete, committing transaction...");
            connection.commit();
            mLogger.log(Level.INFO, "Transaction committed successfully.");
            return returnValue;
        }
        catch (Throwable thr) {
            mLogger.log(Level.INFO,
                    "Exception thrown.  Rolling back transaction...");
            connection.rollback();
            throw thr;
        }
        finally {
            mLogger.log(Level.INFO,
                    "Invocation terminated.  Closing connection.");
            mThreadLocalConnection.set(null);
            connection.close();
        }
    }

    public Connection get() throws ManagedConnectionException {
        Connection conn = mThreadLocalConnection.get();
        // if the interceptor fails to obtain a connection,
        // throw an exception to force the invocation to abort
        if (conn == null) {
            throw new ManagedConnectionException(
                    "Thread local connection in null.  Method interception failed.  " +
                    "This can occurr if the client method was no annotated with @ManagedConnection");
        }
        return conn;
    }

}
