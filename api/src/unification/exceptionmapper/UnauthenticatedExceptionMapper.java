/**
 * UnauthenticatedExceptionMapper.java
 *
 * Created Jan 4, 2013 at 3:24:41 PM by cory.a.johannsen@gmail.com
 */
package unification.exceptionmapper;

import com.google.inject.Singleton;
import org.apache.shiro.authz.UnauthenticatedException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * UnauthenticatedExceptionMapper TODO: type description
 *
 * @author cory.a.johannsen@gmail.com
 */
@Provider
@Singleton
public class UnauthenticatedExceptionMapper implements ExceptionMapper<UnauthenticatedException> {

    /*
     * (non-Javadoc)
     * 
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(UnauthenticatedException ex) {
        return Response.status(403).type(MediaType.APPLICATION_JSON)
                .entity(String.format("{\"error\":\"%s\"}", ex.getMessage())).build();
    }

}
