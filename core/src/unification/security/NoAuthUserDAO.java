/**
 * LdapUserDAO.java
 *
 * Created Oct 29, 2012 at 9:41:18 AM by cory.johannsen@vendscreen.com
 */
package unification.security;

import com.google.inject.Inject;
import unification.configuration.Log;
import org.slf4j.Logger;

/**
 * LdapUserDAO TODO: type description
 *
 * @author cory.a.johannsen@gmail.com
 */
public class NoAuthUserDAO implements UserDAO {

    @Log
    org.slf4j.Logger mLogger;

    /**
     *
     */
    @Inject
    public NoAuthUserDAO(Logger logger) {
        mLogger = logger;
    }

    /*
     * (non-Javadoc)
     * 
     * @see UserDAO#loadUser(java.lang.String)
     */
    public User loadUser(String username) throws UserNotFoundException,
            UserDAOException {
        mLogger.info("Querying for extended user info for "
                + username);
        LdapUser user = new LdapUser();
        user.setGivenName(username);
        user.setSurname(username);
        user.setCommonName(username);
        user.setEmail(username + "@javaunification.com");
        user.setDisplayName(username);
        user.setUsername(username);
        return user;
    }
}
