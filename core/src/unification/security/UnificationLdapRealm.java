/**
 * LdapRealm.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.security;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.inject.Inject;
import org.slf4j.Logger;
import unification.configuration.Log;

/**
 * UnificationLdapRealm 
 * Extension of JndiLdapRealm that loads the user's authorization data
 * from the LDAP repository.
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public class UnificationLdapRealm extends JndiLdapRealm {

    private static final String USER_PREFIX = "uid=";
    private static final String USER_SUFFIX = ",ou=users,dc=javaunification,dc=org";
    private static final String ROLE_SEARCH_BASE_DN = "ou=roles,dc=javaunification,dc=org";
    private static final String ROLE_PREFIX = "cn=";

    @Log
    Logger logger;

    /**
     * Default no-arg constructor
     */
    @Inject
    public UnificationLdapRealm() {
        super();
        setUserDnTemplate(USER_PREFIX + "{0}" + USER_SUFFIX);
    }

    /**
     * @param context
     * @param attributes
     * @param returnAttributes
     * @return
     * @throws NamingException Executes an LdapQuery to retrieve authorization information
     */
    protected SimpleAuthorizationInfo doLdapQuery(DirContext context, Attributes attributes,
                                                  String[] returnAttributes) throws NamingException {
        NamingEnumeration<SearchResult> searchResults = context.search(ROLE_SEARCH_BASE_DN, attributes,
                returnAttributes);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        while (searchResults.hasMore()) {
            SearchResult searchResult = searchResults.next();
            logger.info("found search result " + searchResult);
            String name = searchResult.getName();
            if (name.startsWith(ROLE_PREFIX)) {
                name = name.substring(ROLE_PREFIX.length());
            }
            authorizationInfo.addRole(name);
        }
        searchResults.close();
        return authorizationInfo;
    }

    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token,
                                                            LdapContextFactory ldapContextFactory)
            throws NamingException {

        Object principal = token.getPrincipal();
        Object credentials = token.getCredentials();

        //logger.debug("Authenticating user '{}' through LDAP", principal);

        principal = getLdapPrincipal(token);

        LdapContext ctx = null;
        try {
            ctx = ldapContextFactory.getLdapContext(principal, credentials);
            //context was opened successfully, which means their credentials were valid.  Return the AuthenticationInfo:
            return createAuthenticationInfo(token, principal, credentials, ctx);
        }
        catch (Exception ex) {
            //logger.debug("Caught exception: ", ex);
            throw ex;
        }
        finally {
            LdapUtils.closeContext(ctx);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.shiro.realm.ldap.JndiLdapRealm#doGetAuthenticationInfo(org.apache
     * .shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("Token: " + token);
        AuthenticationInfo authcInfo = super.doGetAuthenticationInfo(token);
        System.out.println("AuthenticationInfo: " + authcInfo);
        return authcInfo;
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
        logger.info("Querying for authorization info for " + primaryPrincipal);
        Attributes attributes = new BasicAttributes();
        attributes.put("uniqueMember", USER_PREFIX + primaryPrincipal + USER_SUFFIX);
        String[] returnAttributes = {"cn"};
        SimpleAuthorizationInfo authorizationInfo = doLdapQuery(context, attributes, returnAttributes);
        return authorizationInfo;
    }

}
