/**
 * VendScreenAPIServletModule.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.configuration;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import unification.service.ObjectMapperProvider;


import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.jndi.JndiIntegration;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * GuiceJerseyServletModule 
 * Extension of JerseyServletModule that configures the system
 * with Guice dependency injection.  This includes binding
 * the JDBC datasource, connection manager and lifecycle interceptor, and
 * Jackson-JSON serialization engine. The system is then configured to
 * auto-publish and manage all service resources in the unification service
 * packages.
 * 
 * This module filters all request through a GuiceShiroFilter to automatically 
 * enforce Shiro security.
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public class GuiceJerseyServletModule extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();
        filter("/*").through(GuiceShiroFilter.class);
        
        // Bind the InitialContext implementation class to the Context interface using singleton semantics
        bind(Context.class).to(InitialContext.class).in(Singleton.class);
        // Bind the "jdbc/unification" JNDI resource to the DataSource interface
        bind(DataSource.class).toProvider(
                JndiIntegration.fromJndi(DataSource.class,
                        "java:comp/env/jdbc/unification"));

        // Bind an ObjectMapper provider to support seamless Jasper JSON serialization/deserialization
        bind(ObjectMapperProvider.class).asEagerSingleton();

        // hook Jersey into Guice Servlet
        bind(GuiceContainer.class);

        // hook Jackson into Jersey as the POJO <-> JSON mapper
        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        parameters.put(PackagesResourceConfig.PROPERTY_PACKAGES,
                "unification.service");

        parameters.put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
                "com.sun.jersey.api.container.filter.LoggingFilter");
        parameters.put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
                "com.sun.jersey.api.container.filter.LoggingFilter");

        serve("/*").with(GuiceContainer.class, parameters);
    }
}
