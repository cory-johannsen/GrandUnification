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
 * A pass-through authentication/authorization module designed for testing.
 *
 * @author cory.a.johannsen@gmail.com
 */
public abstract class NoAuthShiroConfigurationModule extends ShiroWebModule {

    private final String HASH_ALGORITHM = "SHA-256";
    private final int HASH_ITERATIONS = 1000;

    /**
     * @param servletContext
     */
    public NoAuthShiroConfigurationModule(ServletContext servletContext) {
        super(servletContext);
    }

    protected void configureShiroWeb() {
        System.out.println("NoAuthShiroConfigurationModule.configureShiroWeb invoked.");

        // Bind the Realms
        bindRealm().to(NoAuthRealm.class);

        // Bind and expose the UserDAO
        bind(UserDAO.class).to(NoAuthUserDAO.class);
        expose(UserDAO.class);

        // Configure Shiro filter chains to support injection of security
        configureFilterChains();
    }

    protected abstract void configureFilterChains();
}
