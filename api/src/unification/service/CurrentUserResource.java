/**
 * CurrentUserResource.java
 *
 * Created Nov 9, 2012 at 7:01:06 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;

import unification.security.User;
import unification.security.UserDAO;
import unification.security.UserDAO.UserDAOException;
import unification.security.UserDAO.UserNotFoundException;

import com.google.inject.Inject;

/**
 * CurrentUserResource - TODO: type description
 *
 * @author cjohannsen
 *
 */
@Path("/{api_version}/currentuser")
@RequiresAuthentication
public class CurrentUserResource {

    private Logger mLogger;
    private UserDAO mUserDAO;
    
    /**
     * @param logger
     */
    @Inject
    public CurrentUserResource(Logger logger, UserDAO userDAO) {
        mLogger = logger;
        mUserDAO = userDAO;
    }
    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public CurrentUserResponse getCurrentUser(@PathParam("api_version") String apiVersion) {
        Subject subject  = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = null;
            try {
                user = mUserDAO.loadUser(subject.getPrincipal().toString());
            }
            catch (UserNotFoundException ex) {
                mLogger.log(Level.INFO, "User not found", ex);
                return new CurrentUserResponse("User not found.");
            }
            catch (UserDAOException ex) {
                mLogger.log(Level.INFO, "User DAO exception", ex);
                return new CurrentUserResponse("User DAO Exception.");
            }
            return new CurrentUserResponse(user);
        }
        return new CurrentUserResponse("Not authenticated.");
    }
}
