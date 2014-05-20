/**
 * GuiceServletConfiguration.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.guice.aop.ShiroAopModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GuiceServletConfiguration TODO: type description
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public abstract class GuiceServletInjector extends GuiceServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(GuiceServletInjector.class);
    private ServletContext servletContext;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.inject.servlet.GuiceServletContextListener#getInjector()
     */
    @Override
    protected Injector getInjector() {
        log.trace("getInjector invoked.");
        return Guice.createInjector(createShiroConfigurationModule(servletContext), new ShiroAopModule(),
                createApplicationModule());
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.trace("contextInitialized invoked.");
        servletContext = servletContextEvent.getServletContext();
        super.contextInitialized(servletContextEvent);
    }

    protected abstract GrandUnificationModule createApplicationModule();

    protected abstract ShiroWebModule createShiroConfigurationModule(ServletContext context);
}
