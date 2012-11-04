/**
 * LdapRealm.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.inject.Inject;

/**
 * UnificationLdapRealm 
 * Extension of JndiLdapRealm that loads the user's authorization data
 * from the LDAP repository.
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public class UnificationLdapRealm extends JndiLdapRealm {

    private static final String ROLE_SEARCH_BASE_DN = "ou=roles,dc=unification,dc=org";
    private static final String USER_PREFIX = "uid=";
    private static final String USER_SUFFIX = ",ou=users,dc=unification,dc=org";
    private static final String ROLE_PREFIX = "cn=";

    private final Logger mLogger;

    /**
     * 
     */
    @Inject
    public UnificationLdapRealm(Logger logger) {
        super();
        mLogger = logger;
        setUserDnTemplate("uid={0}" + USER_SUFFIX);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.shiro.realm.ldap.JndiLdapRealm#queryForAuthorizationInfo(org
     * .apache.shiro.subject.PrincipalCollection,
     * org.apache.shiro.realm.ldap.LdapContextFactory)
     */
    @Override
    protected AuthorizationInfo queryForAuthorizationInfo(
            PrincipalCollection principals,
            LdapContextFactory ldapContextFactory) throws NamingException {
        DirContext context = ldapContextFactory.getSystemLdapContext();
        String primaryPrincipal = principals.getPrimaryPrincipal().toString();
        mLogger.log(Level.INFO, "Querying for authorization info for "
                + primaryPrincipal);
        Attributes attributes = new BasicAttributes();
        attributes.put("uniqueMember", USER_PREFIX + primaryPrincipal
                + USER_SUFFIX);
        String[] returnAttributes = { "cn" };
        NamingEnumeration<SearchResult> searchResults = context.search(
                ROLE_SEARCH_BASE_DN, attributes, returnAttributes);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        while (searchResults.hasMore()) {
            SearchResult searchResult = searchResults.next();
            mLogger.log(Level.INFO, "found search result " + searchResult);
            String name = searchResult.getName();
            if (name.startsWith(ROLE_PREFIX)) {
                name = name.substring(ROLE_PREFIX.length());
            }
            authorizationInfo.addRole(name);
        }
        searchResults.close();
        return authorizationInfo;
    }

}
