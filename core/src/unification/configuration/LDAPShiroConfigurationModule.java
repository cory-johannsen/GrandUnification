/**
 * GuiceConfigurationModule.java Created by cory.johannsen@vendscreen.com
 */
package unification.configuration;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import unification.security.*;

import javax.servlet.ServletContext;

/**
 * VendScreenAPIConfigurationModule GUICE/Shiro configuration module for the
 * VendScreenAPI. Binds JDBC connection injector, LDAP context factory injector,
 * JNDI LDAP security realm, VendScreen domain objects and DAOs, Connection
 * management interceptor, and Shiro security filters.
 *
 * @author cory.a.johannsen@gmail.com
 */
public abstract class LDAPShiroConfigurationModule extends ShiroWebModule {

    private final String HASH_ALGORITHM = "SHA-256";
    private final int HASH_ITERATIONS = 1000;

    /**
     * @param servletContext
     */
    public LDAPShiroConfigurationModule(ServletContext servletContext) {
        super(servletContext);
    }

    @Provides
    @Singleton
    @Inject
    public LdapContextFactory provideContextFactory(
            @Named("LDAP_URL") String ldapUrl,
            @Named("LDAP_ADMIN_DN") String adminDN,
            @Named("LDAP_ADMIN_PW") String adminPW) {
        System.out.println("LDAPShiroConfigurationModule.provideContextFactory invoked.");
        final JndiLdapContextFactory contextFactory = new JndiLdapContextFactory();
        contextFactory.setUrl(ldapUrl);
        contextFactory.setSystemUsername(adminDN);
        contextFactory.setSystemPassword(adminPW);
        return contextFactory;
    }

    @Provides
    @Singleton
    @Inject
    public UnificationLdapRealm provideUserLdapRealm(
            LdapContextFactory contextFactory, CacheManager cacheManager,
            HashedCredentialsMatcher matcher) {
        System.out.println("LDAPShiroConfigurationModule.provideUserLdapRealm invoked.");
        UnificationLdapRealm realm = new UnificationLdapRealm();
        realm.setContextFactory(contextFactory);
        realm.setCacheManager(cacheManager);
        realm.setAuthenticationTokenClass(UsernamePasswordToken.class);
        realm.setCachingEnabled(true);
        return realm;
    }


    @Provides
    @Singleton
    @Inject
    public AuthenticatingRealm provideSharedSecretRealm(CacheManager cacheManger,
                                                        SharedSecretAuthenticatingRealm realm, HashedCredentialsMatcher matcher) {
        System.out.println("LDAPShiroConfigurationModule.provideAuthenticatingRealm invoked.");
        realm.setCachingEnabled(true);
        realm.setCacheManager(cacheManger);
        realm.setAuthenticationTokenClass(SharedSecretAuthenticationToken.class);
        matcher.setHashAlgorithmName(HASH_ALGORITHM);
        matcher.setHashIterations(HASH_ITERATIONS);

        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    protected void configureShiroWeb() {
        System.out.println("LDAPShiroConfigurationModule.configureShiroWeb invoked.");
        // Bind and expose an authorization cache manager
        bind(CacheManager.class).to(MemoryConstrainedCacheManager.class);
        expose(CacheManager.class);

        // Bind the Realms
        bindRealm().to(UnificationLdapRealm.class);
        bindRealm().to(SharedSecretAuthenticatingRealm.class);

        // Bind and expose the UserDAO
        bind(UserDAO.class).to(LdapUserDAO.class);
        expose(UserDAO.class);

        // Configure Shiro filter chains to support injection of security
        configureFilterChains();
    }

    protected abstract void configureFilterChains();
}
