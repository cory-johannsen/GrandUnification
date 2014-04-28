/**
 * LoginResource.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import unification.security.User;
import unification.security.UserDAO;
import unification.security.UserDAO.UserDAOException;

import com.google.inject.Inject;

/**
 * LoginResource TODO: type description
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
@Path("/service/login")
public class LoginResource {

    private Logger mLogger;
    private UserDAO mUserDAO;

    /**
     * 
     */
    @Inject
    public LoginResource(Logger logger, UserDAO userDAO) {
        mLogger = logger;
        mUserDAO = userDAO;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public synchronized LoginResponse login(LoginRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(
                request.getUsername(), request.getPassword());
        if (SecurityUtils.getSubject().isAuthenticated()) {
            SecurityUtils.getSubject().logout();
        }
        try {
            SecurityUtils.getSubject().login(token);
        }
        catch (AuthenticationException ex) {
            // mLogger.log(Level.INFO,
            // "Exception authenticating user " + request.getUsername());
            return new LoginResponse("Authentication Failed.");
        }
        Subject subject = SecurityUtils.getSubject();
        mLogger.log(Level.INFO, "User " + request.getUsername()
                + " successfully authenticated.");
        
        User user = null;
        try {
            user = mUserDAO.loadUser(subject.getPrincipals().getPrimaryPrincipal().toString());
        }
        catch (UserDAOException ex) {
            mLogger.log(Level.SEVERE, "Failed to load user for username " + request.getUsername(), ex);
            return new LoginResponse("Unable to retrieve user.");
        }
        
        return new LoginResponse(user, subject.getSession().getTimeout());
    }

}
