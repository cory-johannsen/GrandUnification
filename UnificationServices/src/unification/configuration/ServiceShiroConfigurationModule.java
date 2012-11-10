/**
 * VendScreenWebShiroConfigurationModule.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.configuration;

import javax.servlet.ServletContext;

/**
 * VendScreenWebShiroConfigurationModule
 * TODO: type description 
 *
 * @author cory.johannsen@vendscreen.com
 *
 */
public class ServiceShiroConfigurationModule extends
        ShiroConfigurationModule {

    /**
     * @param servletContext
     */
    public ServiceShiroConfigurationModule(ServletContext servletContext) {
        super(servletContext);
    }

    /* (non-Javadoc)
     * @see com.vendscreen.service.configuration.VendScreenShiroConfigurationModule#configureFilterChains()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void configureFilterChains() {
        addFilterChain("/login.html", ANON);
        addFilterChain("/service/login", ANON);
        addFilterChain("/service/session/validate", ANON);
        addFilterChain("/**", AUTHC_BASIC); // AUTHC_BASIC);
        addFilterChain("/service/**", AUTHC_BASIC); // AUTHC_BASIC);
    }

}
