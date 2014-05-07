/**
 * LdapUserDAO.java
 *
 * Created Oct 29, 2012 at 9:41:18 AM by cory.johannsen@vendscreen.com
 */
package unification.security;

import com.google.inject.Inject;
import org.apache.shiro.realm.ldap.LdapContextFactory;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

import unification.configuration.Log;

/**
 * LdapUserDAO TODO: type description
 *
 * @author cory.a.johannsen@gmail.com
 */
public class LdapUserDAO implements UserDAO {

    private static final String USER_PREFIX = "uid=";
    private static final String USER_SUFFIX = ",ou=users,dc=javaunification,dc=org";
    private static final String GIVENNAME_ATTR_KEY = "givenname";
    private static final String SURNAME_ATTR_KEY = "sn";
    private static final String MAIL_ATTR_KEY = "mail";
    private static final String CN_ATTR_KEY = "cn";
    private static final String DISPLAYNAME_ATTR_KEY = "displayname";
    private final LdapContextFactory mContextFactory;
    @Log
    org.slf4j.Logger mLogger;

    /**
     *
     */
    @Inject
    public LdapUserDAO(LdapContextFactory contextFactory) {
        mContextFactory = contextFactory;
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
        LdapContext ldapContext = null;
        try {
            ldapContext = mContextFactory.getSystemLdapContext();
        } catch (NamingException ex) {
            throw new UserDAOException(ex);
        }
        Attributes attributes = null;
        try {
            attributes = ldapContext.getAttributes(USER_PREFIX
                    + username + USER_SUFFIX);
        } catch (NamingException ex) {
            throw new UserNotFoundException(username + " not found.");
        }
        Attribute givenNameAttr = attributes.get(GIVENNAME_ATTR_KEY);
        Attribute surnameAttr = attributes.get(SURNAME_ATTR_KEY);
        Attribute cnAttr = attributes.get(MAIL_ATTR_KEY);
        Attribute mailAttr = attributes.get(CN_ATTR_KEY);
        Attribute displayNameAttr = attributes.get(DISPLAYNAME_ATTR_KEY);

        try {
            LdapUser user = new LdapUser();
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
            user.setUsername(username);
            return user;
        } catch (NamingException ex) {
            throw new UserDAOException(ex);
        }
    }
}
