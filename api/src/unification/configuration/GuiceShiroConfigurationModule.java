/**
 * VendScreenWebShiroConfigurationModule.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.configuration;

import javax.servlet.ServletContext;

/**
 * ShiroConfigurationModule
 * TODO: type description 
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
public class GuiceShiroConfigurationModule extends
        ShiroConfigurationModule {

    /**
     * @param servletContext
     */
    public GuiceShiroConfigurationModule(ServletContext servletContext) {
        super(servletContext);
    }

    /* (non-Javadoc)
     * @see unification.configuration.GuiceShiroConfigurationModule#configureFilterChains()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void configureFilterChains() {
        addFilterChain("/1.0/status", ANON);
        addFilterChain("/1.0/**", AUTHC_BASIC);
    }

}
