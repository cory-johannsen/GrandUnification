/**
 * LoginResource.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.google.inject.Inject;

/**
 * LoginResource TODO: type description
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
@Path("/service/logout")
public class LogoutResource {

    private Logger mLogger;

    /**
     * 
     */
    @Inject
    public LogoutResource(Logger logger) {
        mLogger = logger;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public synchronized LogoutResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            mLogger.log(Level.INFO, "User " + subject.getPrincipal() 
                    + " logging out.");
            subject.logout();
        }
        return new LogoutResponse(null);
    }

}
