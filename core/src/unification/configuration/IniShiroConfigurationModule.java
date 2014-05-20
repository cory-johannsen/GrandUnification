package unification.configuration;

import com.google.inject.Provides;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unification.security.NoAuthRealm;
import unification.security.NoAuthUserDAO;
import unification.security.UserDAO;

import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 * User: cjohannsen
 * Date: 5/19/14
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class IniShiroConfigurationModule extends ShiroWebModule {

    private static final Logger log = LoggerFactory.getLogger(IniShiroConfigurationModule.class);

    protected IniShiroConfigurationModule(ServletContext servletContext) {
        super(servletContext);
    }

    @Override
    protected void configureShiroWeb() {
        log.debug("configureShiroWeb invoked.");
        // Bind and expose an authorization cache manager
        bind(CacheManager.class).to(MemoryConstrainedCacheManager.class);
        expose(CacheManager.class);

        // Bind the Realms
        try {
            bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
        } catch (NoSuchMethodException e) {
            addError(e);
        }

        // Bind and expose the UserDAO
        //bind(UserDAO.class).to(NoAuthUserDAO.class);
        //expose(UserDAO.class);

        // Configure Shiro filter chains to support injection of security
        configureFilterChains();
    }

    protected abstract void configureFilterChains();

    @Provides
    Ini loadShiroIni() {
        return Ini.fromResourcePath("classpath:shiro.ini");
    }
}
