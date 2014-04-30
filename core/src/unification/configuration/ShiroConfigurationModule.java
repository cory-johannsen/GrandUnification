/**
 * GuiceConfigurationModule.java 
 * Created by cory.a.johannsen@gmail.com
 */
package unification.configuration;

import javax.servlet.ServletContext;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;

import unification.security.LdapUserDAO;
import unification.security.UnificationLdapRealm;
import unification.security.UserDAO;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * ShiroConfigurationModule
 * GUICE/Shiro configuration module for the system.  Binds JDBC connection injector,
 * LDAP context factory injector, JNDI LDAP security realm, domain objects and DAOs,
 * Connection management interceptor, and Shiro security filters. 
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public abstract class ShiroConfigurationModule extends ShiroWebModule {

    /**
     * @param servletContext
     */
    public ShiroConfigurationModule(ServletContext servletContext) {
        super(servletContext);
    }
    
    @Provides
    @Singleton
    public LdapContextFactory provideContextFactory() {
        final JndiLdapContextFactory contextFactory = new JndiLdapContextFactory();  
        contextFactory.setUrl("ldap://localhost:389/");  

        return contextFactory;
    }

    @Provides
    @Singleton
    @Inject
    public JndiLdapRealm provideLdapRealm(LdapContextFactory contextFactory, CacheManager cacheManager, UnificationLdapRealm realm) {
        realm.setContextFactory(contextFactory);
        realm.setCacheManager(cacheManager);
        realm.setCachingEnabled(true);
        return realm;
    }

    protected void configureShiroWeb() {
        // Bind and expose an authorization cache manager
        bind(CacheManager.class).to(MemoryConstrainedCacheManager.class);  
        expose(CacheManager.class);
        // Bind and expose the UnificationLdapRealm implementation
        bind(UnificationLdapRealm.class);
        expose(UnificationLdapRealm.class);
        // Bind the JndiLdapRealm as the security realm for Shiro
        bindRealm().to(JndiLdapRealm.class);
        // Bind and expose the UserDAO
        bind(UserDAO.class).to(LdapUserDAO.class);
        expose(UserDAO.class);
        

        // Configure Shiro filter chains to support injection of security
        configureFilterChains();
    }
    
    protected abstract void configureFilterChains();
}
