/**
 * SessionValidationResource.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;

import com.google.inject.Inject;

/**
 * SessionValidationResource TODO: type description
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
@Path("/service/session/validate")
public class SessionValidationResource {

    private final Logger mLogger;

    /**
     * 
     */
    @Inject
    public SessionValidationResource(Logger logger) {
        mLogger = logger;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public SessionValidationResponse validate() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return new SessionValidationResponse(null);
        }
        return new SessionValidationResponse("Invalid session.");
    }
}
