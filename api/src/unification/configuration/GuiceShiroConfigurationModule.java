/**
 * VendScreenWebShiroConfigurationModule.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.configuration;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;

import javax.servlet.ServletContext;

/**
 * ShiroConfigurationModule
 * TODO: type description 
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
public class GuiceShiroConfigurationModule extends
        LDAPShiroConfigurationModule {

    @Inject
    @Named("API_VERSION") public String apiVersion;

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
        addFilterChain("/" + apiVersion + "/status", ANON);
        addFilterChain("/" + apiVersion + "*", AUTHC_BASIC);
    }

}
