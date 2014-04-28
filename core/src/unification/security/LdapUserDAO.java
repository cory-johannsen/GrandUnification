/**
 * LdapUserDAO.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

import org.apache.shiro.realm.ldap.LdapContextFactory;

import com.google.inject.Inject;

/**
 * LdapUserDAO 
 * Concrete implementation of UserDAO that constructs and populates user information 
 * from an LDAP repository.  
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public class LdapUserDAO implements UserDAO {
    
    private static final String USER_PREFIX = "uid=";
    private static final String USER_SUFFIX = ",ou=users,dc=unification,dc=org";

    private static final String GIVENNAME_ATTR_KEY = "givenname";
    private static final String SURNAME_ATTR_KEY = "sn";
    private static final String MAIL_ATTR_KEY = "mail";
    private static final String CN_ATTR_KEY = "cn";
    private static final String DISPLAYNAME_ATTR_KEY = "displayname";

    private final Logger mLogger;
    private final LdapContextFactory mContextFactory;

    /**
     * 
     */
    @Inject
    public LdapUserDAO(Logger logger, LdapContextFactory contextFactory) {
        mLogger = logger;
        mContextFactory = contextFactory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vendscreen.service.security.UserDAO#loadUser(java.lang.String)
     */
    public User loadUser(String username) throws UserNotFoundException,
            UserDAOException {
        mLogger.log(Level.INFO, "Querying for extended user info for "
                + username);
        LdapContext ldapContext = null;
        try {
            ldapContext = mContextFactory.getSystemLdapContext();
        }
        catch (NamingException ex) {
            throw new UserDAOException(ex);
        }
        Attributes attributes = null;
        try {
            attributes = ldapContext.getAttributes(USER_PREFIX
                    + username + USER_SUFFIX);
        }
        catch (NamingException ex) {
            throw new UserNotFoundException(username + " not found.");
        }
        Attribute givenNameAttr = attributes.get(GIVENNAME_ATTR_KEY);
        Attribute surnameAttr = attributes.get(SURNAME_ATTR_KEY);
        Attribute cnAttr = attributes.get(MAIL_ATTR_KEY);
        Attribute mailAttr = attributes.get(CN_ATTR_KEY);
        Attribute displayNameAttr = attributes.get(DISPLAYNAME_ATTR_KEY);

        try {
            LdapUser user = new LdapUser();
            user.setUsername(username);
            if (givenNameAttr != null) {
                user.setGivenName(givenNameAttr.get().toString());
            }
            if (surnameAttr != null) {
                user.setSurname(surnameAttr.get().toString());
            }
            if (cnAttr != null) {
                user.setCommonName(cnAttr.get().toString());
            }
            if (mailAttr != null) {
                user.setEmail(mailAttr.get().toString());
            }
            if (displayNameAttr != null) {
                user.setDisplayName(displayNameAttr.get().toString());
            }
            return user;
        }
        catch (NamingException ex) {
            throw new UserDAOException(ex);
        }
    }
}
